/*
package com.nice.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

*/
/**
 * ftp工具类
 *
 * @author nice
 *//*

public class FtpUtil {

    */
/**
     * 日志记录
     *//*

    private static Logger logger = Logger.getLogger(FtpUtil.class);
    */
/**
     * ftp服务器地址
     *//*

    private final String ftpHost = "111.203.205.28";
    */
/**
     * ftp服务器用户名
     *//*

    private final String ftpName = "M20000000402";
    */
/**
     * ftp服务器密码
     *//*

    private final String ftpPass = "P4r76m402";
    */
/**
     * ftp根目录
     *//*

    private String ftpDir;
    */
/**
     * ftp端口
     *//*

    private final String ftpPort = "21";
    */
/**
     * 商户号
     *//*

    private final String merchentNo = "M20000000402";

    */
/**
     * 得到ftp对账文件订单信息集合
     *
     * @param checkDate 对账日期
     * @param busType 业务类型 充值RECHARGE 提现WITHDRAW 交易TRANSACTION 余额BALANCECHANGE
     * @param downloadPath 下载地址
     * @return
     *//*

    public String getFileLt(String checkDate, String busType, String downloadPath) throws Exception {

        int fileSel = 0;//查询到的文件的数量
        int fileCount = 0;//文件下载的数量
        String fName = null;
        ftpDir = "/" + checkDate.substring(0, 4) + "/" + checkDate.substring(4, 6);

        logger.info("选择的对账时间：" + checkDate);
        logger.info("FTP根目录：" + ftpDir);
        logger.info("下载目录：" + downloadPath);

        //得到文件名
        List<String> files = getFileNameByDate(checkDate, busType);
        fileSel = files.size();
        for (String fileName : files) {
            //【下载文件】如果文件存在，执行下面的操作
            if (this.downFile(fileName.trim(), downloadPath) == false) {
                //如果文件列表的文件未找到，继续查找下一个文件是否存在
                continue;
            } else {
                logger.info("文件【" + fileName + "】下载成功！开始解析文件...");
                fileCount++;
                //加载解析文件
                fName = fileName;
            }
        }
        logger.info("应查询到的文件个数：" + fileSel);
        logger.info("实际下载的文件个数：" + fileCount);
        if (fileCount == 0) {
            throw new Exception("对账文件不存在");
        }
        return fName;
    }

    */
/**
     * Description: 从FTP服务器下载文件
     *//*

    private boolean downFile(String fileName, String downloadPath) {
        // 初始表示下载失败
        boolean success = false;
        // 创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            int count = 0;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.connect(ftpHost, Integer.parseInt(ftpPort));
            // 登录ftp
            ftp.login(ftpName, ftpPass);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            // 转到指定下载目录
            ftp.changeWorkingDirectory(ftpDir);
            logger.info("要查找的文件：" + fileName);
            // 列出该目录下所有文件
            FTPFile[] fs = ftp.listFiles();
            // 遍历所有文件，找到指定的文件
            for (FTPFile ff : fs) {
                //logger.info("ftp文件列表：" + ff.getName());
                if (ff.getName().trim().equals(fileName)) {
                    count++;
                    // 根据绝对路径初始化文件
                    File localFile = new File(downloadPath + "/" + ff.getName());
                    // 输出流
                    OutputStream is = new FileOutputStream(localFile);
                    // 下载文件
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            if (count == 0) {
                logger.info("文件未找到，查找下一个...");
                return success;
            }
            // 退出ftp
            ftp.logout();
            // 下载成功
            success = true;
        } catch (FileNotFoundException e) {
            logger.error("文件未找到");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    */
/**
     * 得到文件名
     *//*

    public List<String> getFileNameByDate(String checkDate, String busType) {

        List<String> files = new ArrayList<String>();
        if ("RECHARGE".equals(busType)) {
            files.add(checkDate + "_" + merchentNo + "_RECHARGE.txt");
        } else if ("WITHDRAW".equals(busType)) {
            files.add(checkDate + "_" + merchentNo + "_WITHDRAW.txt");
        } else if ("TRANSACTION".equals(busType)) {
            files.add(checkDate + "_" + merchentNo + "_TRANSACTION.txt");
        } else if ("BALANCECHANGE".equals(busType)) {
            files.add(checkDate + "_" + merchentNo + "_BALANCECHANGE.txt");
        }

        //logger.info("应下载的文件名为：" + this.getFileName());
        return files;
    }

    */
/**
     * 计算两个日期之间的日期
     *
     * @param beginTime 开始时间
     * @param endTime 结束时间
     *//*

    private List<String> getBetweenDate(String beginTime, String endTime) throws ParseException {
        List<GregorianCalendar> v = new ArrayList<GregorianCalendar>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc1 = new GregorianCalendar(), gc2 = new GregorianCalendar();
        gc1.setTime(sdf.parse(beginTime));
        gc2.setTime(sdf.parse(endTime));
        do {
            GregorianCalendar gc3 = (GregorianCalendar) gc1.clone();
            v.add(gc3);
            gc1.add(Calendar.DAY_OF_MONTH, 1);
        } while (!gc1.after(gc2));
        GregorianCalendar[] ga = v.toArray(new GregorianCalendar[v.size()]);
        List<String> list = new ArrayList<String>();
        for (GregorianCalendar e : ga) {
            String year = String.valueOf(e.get(Calendar.YEAR));
            String month = String.valueOf(e.get(Calendar.MONTH) + 1);
            if (month.length() == 1) {
                month = "0" + month;
            }
            String date = String.valueOf(e.get(Calendar.DAY_OF_MONTH));
            if (date.length() == 1) {
                date = "0" + date;
            }
            list.add(year + month + date);
        }
        return list;
    }

}
*/
