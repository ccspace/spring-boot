/**
 * Copyright &copy; 2012-2013 瑞钱通 All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.nice.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 魔数类
 * 
 * @author nice
 * @version 2018-07-02
 */
public enum FileType {
	/**
	 * JEPG
	 */
	JPEG("FFD8FF"),
	/**
	 * PNG
	 */
	PNG("89504E47"),
	/**
	 * GIF
	 */
	GIF("47494638"),
	/**
	 * TIFF
	 */
	TIFF("49492A00"),
	/**
	 * WindowsBitmap
	 */
	BMP("424D"),
	/**
	 * CAD
	 */
	DWG("41433130"),
	/**
	 * Adobe Photoshop
	 */
	PSD("38425053"),
	/**
	 * Adobe XML
	 */
	XML("3C3F786D6C"),
	/**
	 * Adobe HTML
	 */
	HTML("68746D6C3E"),
	/**
	 * Adobe Acrobat
	 */
	PDF("255044462D312E"),
	/**
	 * ZIP Archive
	 */
	ZIP("504B0304"),
	/**
	 * RAR Archive
	 */
	RAR("52617221"),
	/**
	 * Wave
	 */
	WAV("57415645"),
	/**
	 * AVI
	 */
	AVI("41564920");
	private String value = "";

	private FileType(String value) {
		this.value = value;
	}

	private String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}

	/**
	 * 读取文件头文件
	 * 
	 * @param filePath
	 * @return
	 */
	private static String getFileHeader(String filePath) throws IOException {
		// 这里需要注意，每个文件的魔数长度不相同，因此需要使用startwith
		byte[] b = new byte[28];
		InputStream inputStream = null;
		inputStream = new FileInputStream(filePath);
		inputStream.read(b, 0, 28);
		inputStream.close();
		return bytes2Hex(b);
	}

	/**
	 * 读取流文件
	 * 
	 * @param inputStream
	 * @return
	 */
	private static String getFileHeader(InputStream inputStream) throws IOException {
		// 这里需要注意，每个文件的魔数长度不相同，因此需要使用startwith
		byte[] b = new byte[28];
		inputStream.read(b, 0, 28);
		// inputStream.close();
		return bytes2Hex(b);
	}

	private static String bytes2Hex(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 判断文件类型
	 * 
	 * @param filePath
	 * @return
	 */
	public static FileType getType(String filePath) throws IOException {
		String fileHead = getFileHeader(filePath);
		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}
		fileHead = fileHead.toUpperCase();
		FileType[] fileTypes = FileType.values();
		for (FileType type : fileTypes) {
			if (fileHead.startsWith(type.getValue())) {
				return type;
			}
		}
		return null;
	}

	/**
	 * 判断文件类型
	 * 
	 * @param is
	 * @return
	 */
	public static FileType getType(InputStream is) throws IOException {
		String fileHead = getFileHeader(is);
		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}
		fileHead = fileHead.toUpperCase();
		FileType[] fileTypes = FileType.values();
		for (FileType type : fileTypes) {
			if (fileHead.startsWith(type.getValue())) {
				return type;
			}
		}
		return null;
	}
}
