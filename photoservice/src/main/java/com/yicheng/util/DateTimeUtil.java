/**
 * 
 * @FileName: DateTimeUtil
 * @Description: TODO(鐢ㄤ竴鍙ヨ瘽鎻忚堪杩欎釜绫荤殑浣滅敤)
 *
 * Copyright(c) 2012, DHC Software Co.,Ltd.
 * Version:1.0
 *
 * Author: Sylar
 * Date: 2012-11-18 涓婂崍11:16:51
 */
package com.yicheng.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 *时间工具类
 */
public class DateTimeUtil {

	//yyyy-MM-dd HH:mm:ss格式
	private final static String DEFAULTFORMAT = "yyyy-MM-dd HH:mm:ss";
	
	//yy-MM-dd HH:mm:ss格式
	private final static String DEFECTFORMAT = "yy-MM-dd HH:mm:ss";
	
	/**
	 * Data类型时间转化为String类型的基本方法
	 * @param format
	 * @param date
	 * @return
	 */
	public static String dateToString(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * String类型时间转化为Data类型的基本方法
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String format, String date) {
		Date datetime = null;
		try {
			datetime = new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datetime;
	}
	
	public static Date stringToDate(String date) {
		return stringToDate(DEFAULTFORMAT,date);
	}

	/**
	 * Data类型时间转化为String类型,获取当前时间(String)
	 */
	public static String dateToString() {
		return dateToString(DEFAULTFORMAT,new Date());
	}
	
	/**
	 * Data类型时间转化为String类型
	 */
	public static String dateToString(Date date) {
		return dateToString(DEFAULTFORMAT,date);
	}

	/**
	 * 获取当前时间(long)
	 */
	public static long getTime(){
		return System.currentTimeMillis();
	}
	
	/**
	 * 字符串类型日期转为long型基类
	 * @param date
	 * @return
	 */
	public static long getLongDateTime(String format,String date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date datetime = sdf.parse(date);
			return datetime.getTime();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 将字符串类型日期(yyyy-MM-dd HH:mm:ss)转为long型
	 * @param date
	 * @return
	 */
	public static long getLongTime(String date){
		return getLongDateTime(DEFAULTFORMAT,date);
	}
	
	/**
	 * 将字符串类型日期(yy-MM-dd HH:mm:ss)转为long型
	 * @param date
	 * @return
	 */
	public static long getDefectLongTime(String date){
		return getLongDateTime(DEFECTFORMAT,date);
	}
	
}

