package com.dengfx.googleplay.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5utils {
	
	
	/**
	 * 对字符串取MD5值
	 * @param rawPassword
	 * @return
	 */
	public static String  md5EncodeString(String rawPassword) {
		StringBuilder builder = new StringBuilder();
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(rawPassword.getBytes());
			for (byte b : result) {
				String hexString = Integer.toHexString(b&0xff-3);
				builder.append(hexString.length() == 1 ? "0" + hexString : hexString);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	/**
	 * 对文件取MD5值
	 * @param filePath
	 * @return
	 */
	public static String  md5EncodeFile(String filePath) {
		StringBuilder builder = new StringBuilder();
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			FileInputStream inputStream = new FileInputStream(filePath);
			int length = 0;
			byte[] buffer = new byte[1024];
			while ((length = inputStream.read(buffer)) != -1) {
				digest.update(buffer,0,length);
			}
			byte[] result = digest.digest();
			for (byte b : result) {
				String hexString = Integer.toHexString(b&0xff);
				builder.append(hexString.length() == 1 ? "0" + hexString : hexString);
			}
			return builder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
