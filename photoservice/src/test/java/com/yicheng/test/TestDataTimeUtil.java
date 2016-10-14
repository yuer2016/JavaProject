package com.yicheng.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yicheng.entity.JPImageEntity;

public class TestDataTimeUtil {
	
	/**
	 * 将字符串类型日期转为long型
	 * @param date
	 * @return
	 */
	public static long parseTimeMillis(String date,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date datetime = sdf.parse(date);
			return datetime.getTime();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
//		System.out.println(parseTimeMillis("15-08-24 09:47:35","yy-MM-dd HH:mm:ss"));
		String datatime = "2015-08-24 09:47:35";
		System.out.println(datatime.substring(2, datatime.length()));
		
		
		
		
		
		
		
		
		
		
		
		JPImageEntity je = new JPImageEntity();
		
//		byte[] by = new byte[3];
		
		je.setBinaryImage(new byte[]{0,1,1,1,2,1});
		je.setStudentName("zhangsan");
		je.setImageDate(datatime);
		
		System.out.println(je.toJSON());
		
		
		
		
		
		
		
		
		
	}

}