package org.flumePlugin.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

public class Tools {

	private static final String ID_FORMAT = "0000000"; 
	
	/**
	 * 7位定长ID
	 * @param liuShuiHao
	 * @return
	 */
	public static String getIdFormat(String liuShuiHao){
		Integer intHao = Integer.parseInt(liuShuiHao);
		DecimalFormat df = new DecimalFormat(ID_FORMAT);
		return df.format(intHao);
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}
	
	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转为yyMMddHHmmss格式
	 * @param date
	 * @return
	 */
	public static String formatDateTime(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMddHHmmss");
		Date newdate = null;
		try {
			newdate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf2.format(newdate);
	}
	
	/**
	 * 将Date转为yyMMddHHmmss格式
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		return sdf.format(date);
	}

}
