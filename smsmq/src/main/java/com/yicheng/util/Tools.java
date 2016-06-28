package com.yicheng.util;


import java.text.SimpleDateFormat;
import java.util.Date;


public class Tools {
	
	
	public static boolean checkNotEmpty(Object o){
		boolean result = true;
		if(o == null){
			result = false;
		}else{
		   if(o instanceof String){
			 String s = (String)o;
			 result = !s.trim().isEmpty();
			}
		}
		return result;
	}
	
	public static long parseTimeMillis(String date){
		long  result = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datetime = format.parse(date);
			result = datetime.getTime();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
/*	*//**
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 *//*
	public static List<Image> getListParams(String imgList) throws Exception{
		return JSON.parseArray(imgList, Image.class);
	}
	*//**
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 *//*
	public static Image getListParamOne(String img) throws Exception{
		return JSON.parseObject(img, Image.class);
	}
	*//**
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 *//*
	public static String[] getStringsParam(String body) throws Exception{
		return JSON.parseObject(body,String[].class);
	}*/
	
	
}
