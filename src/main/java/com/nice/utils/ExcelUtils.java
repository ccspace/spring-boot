package com.nice.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils {

	public static Cell[] getExcleRow(File excelFile, int rowIndex) throws Exception {
		Workbook workbook = null;

		Cell[] row = null;
		try {
			workbook = Workbook.getWorkbook(excelFile);
			row = workbook.getSheet(0).getRow(rowIndex);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return row;

	}

	public static List<Cell[]> getExcle(File excelFile) throws Exception {
		Workbook workbook = null;
		int count = 0;
		List<Cell[]> list = new ArrayList<Cell[]>();
		try {
			workbook = Workbook.getWorkbook(excelFile);
			Sheet sheet = workbook.getSheet(0);
			count = sheet.getRows();
			for (int i = 0; i < count; i++) {
				Cell[] row = sheet.getRow(i);
				list.add(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return list;

	}
	
	public static List<Map<String,Object>> readXls(String[] head, InputStream is, int srart) throws IOException{
		jxl.Workbook readwb = null;   
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try{   
            //构建Workbook对象, 只读Workbook对象   
            readwb = Workbook.getWorkbook(is);   
  
            //Sheet的下标是从0开始   
            //获取第一张Sheet表   
            Sheet readsheet = readwb.getSheet(0);   
  
            //获取Sheet表中所包含的总列数   
            int rsColumns = readsheet.getColumns();
            if(rsColumns != head.length){
            	throw new Exception("文件列数不正确！");
            }
            
            //获取Sheet表中所包含的总行数   
            int rsRows = readsheet.getRows();   
  
            //获取指定单元格的对象引用   
            for (int i = srart; i < rsRows; i++){
            	Map<String,Object> map = new HashMap<String, Object>();
                for (int j = 0; j < rsColumns; j++){
                    Cell cell = readsheet.getCell(j, i);
                    map.put(head[j], cell.getContents());
                }
                list.add(map);
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            readwb.close();   
        }
        return list;
	  }  

	/**
	 * 导出Excel
	 * 
	 * @param list ：结果集合
	 * @param filePath ：指定的路径名
	 * @param out ：输出流对象 通过response.getOutputStream()传入
	 * @param mapFields ：导出字段 key:对应实体类字段 value：对应导出表中显示的中文名
	 * @param sheetName ：工作表名称
	 */
	public static void createExcel(List<?> list, String filePath, OutputStream out, Map<String, String> mapFields, String sheetName) {
		sheetName = sheetName != null && !sheetName.equals("") ? sheetName : "sheet1";
		WritableWorkbook wook = null;// 可写的工作薄对象
		Object objClass = null;
		try {
			if (filePath != null && !filePath.equals("")) {
				wook = Workbook.createWorkbook(new File(filePath));// 指定导出的目录和文件名
																	// 如：D:\\test.xls
			} else {
				wook = Workbook.createWorkbook(out);// jsp页面导出用
			}

			// 设置头部字体格式
			WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			// 应用字体
			WritableCellFormat wcfh = new WritableCellFormat(font);
			// 设置其他样式
			wcfh.setAlignment(Alignment.CENTRE);// 水平对齐
			wcfh.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcfh.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcfh.setBackground(Colour.SEA_GREEN);// 背景色
			wcfh.setWrap(false);// 不自动换行

			// 设置内容日期格式
			DateFormat df = new DateFormat("yyyy-MM-dd HH:mm:ss");
			// 应用日期格式
			WritableCellFormat wcfc = new WritableCellFormat(df);

			wcfc.setAlignment(Alignment.CENTRE);
			wcfc.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcfc.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcfc.setWrap(false);// 不自动换行

			// 创建工作表
			WritableSheet sheet = wook.createSheet(sheetName, 0);
			SheetSettings setting = sheet.getSettings();
			setting.setVerticalFreeze(1);// 冻结窗口头部

			int columnIndex = 0; // 列索引
			List<String> methodNameList = new ArrayList<String>();
			if (mapFields != null) {
				String key = "";
				Map<String, Method> getMap = null;
				Method method = null;
				// 开始导出表格头部
				for (Iterator<String> i = mapFields.keySet().iterator(); i.hasNext();) {
					key = i.next();
					// 应用wcfh样式创建单元格
					sheet.addCell(new Label(columnIndex, 0, mapFields.get(key), wcfh));
					// 记录字段的顺序，以便于导出的内容与字段不出现偏移
					methodNameList.add(key);
					columnIndex++;
				}
				if (list != null && list.size() > 0) {
					// 导出表格内容
					for (int i = 0, len = list.size(); i < len; i++) {
						objClass = list.get(i);
						if(objClass instanceof Map){
							Map<String,Object> map = (Map<String, Object>) objClass;
							for (int j = 0; j < methodNameList.size(); j++) {
								Object o = map.get(methodNameList.get(j));
								if(o != null){
									String value = o.toString();
									sheet.addCell(new Label(j, i + 1, value, wcfc));
								}else{
									sheet.addCell(new Label(j, i + 1, "", wcfc));
								}
							}
						}else{
							getMap = getAllMethod(objClass);// 获得对象所有的get方法
							// 按保存的字段顺序导出内容
							for (int j = 0; j < methodNameList.size(); j++) {
								// 根据key获取对应方法
								method = getMap.get("GET" + methodNameList.get(j).toString().toUpperCase());
								if (method != null) {
									Class methodClass = method.getReturnType();
									String value = "";
									if("java.math.BigDecimal".equals(methodClass.getName())){
										value = String.valueOf(method.invoke(objClass, null) == null ? "0.00" : method.invoke(objClass, null));
									}else{
										value = String.valueOf(method.invoke(objClass, null) == null ? "" : method.invoke(objClass, null));
									}
									// 从对应的get方法得到返回值
									
									//String value = String.valueOf(method.invoke(objClass, null) == null ? "" : method.invoke(objClass, null));
									// 应用wcfc样式创建单元格
									sheet.addCell(new Label(j, i + 1, value, wcfc));
								} else {
									// 如果没有对应的get方法，则默认将内容设为""
									sheet.addCell(new Label(j, i + 1, "", wcfc));
								}

							}
						}
					}
				}
				wook.write();
				System.out.println("导出Excel成功！");
			} else {
				throw new Exception("传入参数不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wook != null) {
					wook.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 获取类的所有get方法
	 * 
	 * @param cls
	 * @return
	 */
	public static HashMap<String, Method> getAllMethod(Object obj) throws Exception {
		HashMap<String, Method> map = new HashMap<String, Method>();
		Method[] methods = obj.getClass().getMethods();// 得到所有方法
		String methodName = "";
		for (int i = 0; i < methods.length; i++) {
			methodName = methods[i].getName().toUpperCase();// 方法名
			if (methodName.startsWith("GET")) {
				map.put(methodName, methods[i]);// 添加get方法至map中
			}
		}
		return map;
	}

	/**
	 * 根据指定路径导出Excel
	 * 
	 * @param list
	 * @param filePath
	 * @param mapFields
	 * @param sheetName
	 */
	public static void exportExcel(List<?> list, String filePath, Map<String, String> mapFields, String sheetName) {
		createExcel(list, filePath, null, mapFields, sheetName);
	}

	/**
	 * 从页面导出Excel
	 * 
	 * @param list 需要导出的数据集合
	 * @param out 输出流
	 * @param mapFields 属性的中文名称的Map
	 * @param sheetName sheet页的名称
	 */
	public static void exportExcel(List<?> list, OutputStream out, Map<String, String> mapFields, String sheetName) {
		createExcel(list, null, out, mapFields, sheetName);
	}

	/**
	 * 导出Excel
	 * 
	 * @param list ：结果集合
	 * @param filePath ：指定的路径名
	 * @param out ：输出流对象 通过response.getOutputStream()传入
	 * @param mapFields ：导出字段 key:对应实体类字段 value：对应导出表中显示的中文名
	 * @param sheetName ：工作表名称
	 */
	public static void createText(List<?> list, String filePath, OutputStream out, String[] fields) {
		Object objClass = null;
		try {
			int columnIndex = 0; // 列索引
			List<String> methodNameList = new ArrayList<String>();
			if (fields != null) {
				String key = "";
				Map<String, Method> getMap = null;
				Method method = null;
				StringBuffer sb = new StringBuffer();
				if (list != null && list.size() > 0) {
					// 导出表格内容
					for (int i = 0, len = list.size(); i < len; i++) {
						StringBuffer buffer = new StringBuffer();
						objClass = list.get(i);
						if (objClass instanceof Map) {
							for (String k : fields) {
								buffer.append(((Map) objClass).get(k) + ",");
							}
						} else {
							getMap = getAllMethod(objClass);// 获得对象所有的get方法
							for (String k : fields) {
								// 根据key获取对应方法
								method = getMap.get("GET" + k.toUpperCase());
								if (method != null) {
									// 从对应的get方法得到返回值
									String value = String.valueOf(method.invoke(objClass, null) == null ? "" : method.invoke(objClass, null));
									buffer.append(value + ",");
								} else {
									// 如果没有对应的get方法，则默认将内容设为""
									buffer.append("" + ",");
								}
							}
						}
						sb.append(buffer.toString() + "\r\n");
					}
				}
				out.write(sb.toString().getBytes());
				System.out.println("导出txt成功！");
			} else {
				throw new Exception("传入参数不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 从页面导出Excel
	 * 
	 * @param list 需要导出的数据集合
	 * @param out 输出流
	 * @param mapFields 属性的中文名称的Map
	 * @param sheetName sheet页的名称
	 */
	public static void exportText(List<?> list, OutputStream out, String[] fields, String sheetName) {
		createText(list, null, out, fields);
	}
	
	/**
	 * 从页面导出Excel
	 * 
	 * @param list 需要导出的数据集合
	 * @param out 输出流
	 * @param mapFields 属性的中文名称的Map
	 * @param sheetName sheet页的名称
	 */
	public static void exportText(List<String[]> headList, List<?> list, OutputStream out, String[] fields, String sheetName) {
		createText(headList, list, null, out, fields);
	}
	
	/**
	 * 导出Excel
	 * 
	 * @param list ：结果集合
	 * @param filePath ：指定的路径名
	 * @param out ：输出流对象 通过response.getOutputStream()传入
	 * @param mapFields ：导出字段 key:对应实体类字段 value：对应导出表中显示的中文名
	 * @param sheetName ：工作表名称
	 */
	public static void createText(List<String[]> headList, List<?> list, String filePath, OutputStream out, String[] fields) {
		Object objClass = null;
		try {
			if (fields != null) {
				Map<String, Method> getMap = null;
				Method method = null;
				StringBuffer sb = new StringBuffer();
				if(headList!=null && headList.size()>0){
					for (String[] arr : headList) {
						for (String s : arr) {
							sb.append(s+",");
						}
						//去除最后一位逗号
						sb.deleteCharAt(sb.length()-1); 
						sb.append("\r\n");
					}
				}
				if (list != null && list.size() > 0) {
					// 导出表格内容
					for (int i = 0, len = list.size(); i < len; i++) {
						StringBuffer buffer = new StringBuffer();
						objClass = list.get(i);
						if (objClass instanceof Map) {
							for (String k : fields) {
								buffer.append(((Map) objClass).get(k) + ",");
							}
						} else {
							getMap = getAllMethod(objClass);// 获得对象所有的get方法
							for (String k : fields) {
								// 根据key获取对应方法
								method = getMap.get("GET" + k.toUpperCase());
								if (method != null) {
									// 从对应的get方法得到返回值
									String value = String.valueOf(method.invoke(objClass, null) == null ? "" : method.invoke(objClass, null));
									buffer.append(value + ",");
								} else {
									// 如果没有对应的get方法，则默认将内容设为""
									buffer.append("" + ",");
								}
							}
						}
						sb.append(buffer.toString() + "\r\n");
					}
				}
				out.write(sb.toString().getBytes());
				System.out.println("导出txt成功！");
			} else {
				throw new Exception("传入参数不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * 导出Excel
	 * 
	 * @param list ：结果集合
	 * @param filePath ：指定的路径名
	 * @param out ：输出流对象 通过response.getOutputStream()传入
	 * @param mapFields ：导出字段 key:对应实体类字段 value：对应导出表中显示的中文名
	 * @param sheetName ：工作表名称
	 */
	public static void createTextJ01(String[] headList, List<?> list,OutputStream out, String[] fields) {
		Object objClass = null;
		try {
			if (fields != null) {
				Map<String, Method> getMap = null;
				Method method = null;
				StringBuffer sb = new StringBuffer();
				if (list != null && list.size() > 0) {
					// 导出表格内容
					for (int i = 0, len = list.size(); i < len; i++) {
						StringBuffer buffer = new StringBuffer();
						objClass = list.get(i);
						if (objClass instanceof Map) {
							for (String k : fields) {
								buffer.append(((Map) objClass).get(k) + ",");
							}
						} else {
							getMap = getAllMethod(objClass);// 获得对象所有的get方法
							for (int j=0;j<fields.length;j++) {
								// 根据key获取对应方法
								method = getMap.get("GET" + fields[j].toUpperCase());
								if (method != null) {
									// 从对应的get方法得到返回值
									String value = String.valueOf(method.invoke(objClass, null) == null ? "" : method.invoke(objClass, null));
									buffer.append(headList[j]+" ");
									if(null ==value){
										buffer.append("0.00" + ",");
									}
									buffer.append(value + ",");
								} else {
									// 如果没有对应的get方法，则默认将内容设为""
									buffer.append("" + ",");
								}
							}
						}
						sb.append(buffer.toString() + "\r\n");
					}
				}
				out.write(sb.toString().getBytes());
				System.out.println("导出txt成功！");
			} else {
				throw new Exception("传入参数不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} 
	}
	
	public static void createTextJ02(String[] headList, List<?> list,OutputStream out, String[] fields) {
		Object objClass = null;
		try {
			if (fields != null) {
				Map<String, Method> getMap = null;
				Method method = null;
				StringBuffer sb = new StringBuffer();
				if (list != null && list.size() > 0) {
					// 导出表格内容
					for (int i = 0, len = list.size(); i < len; i++) {
						StringBuffer buffer = new StringBuffer();
						objClass = list.get(i);
						if (objClass instanceof Map) {
							for (String k : fields) {
								buffer.append(((Map) objClass).get(k) + ",");
							}
						} else {
							getMap = getAllMethod(objClass);// 获得对象所有的get方法
							for (int j=0;j<fields.length;j++) {
								// 根据key获取对应方法
								method = getMap.get("GET" + fields[j].toUpperCase());
								if (method != null) {
									// 从对应的get方法得到返回值
									String value = String.valueOf(method.invoke(objClass, null) == null ? "" : method.invoke(objClass, null));
									buffer.append(headList[j]+" ");
									if(null ==value){
										buffer.append("0.00" + ",");
									}
									buffer.append(value + ",");
								} else {
									// 如果没有对应的get方法，则默认将内容设为""
									buffer.append("" + ",");
								}
							}
						}
						sb.append(buffer.toString() + "\r\n");
					}
				}
				out.write(sb.toString().getBytes());
				System.out.println("导出txt成功！");
			} else {
				throw new Exception("传入参数不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	
	
}
