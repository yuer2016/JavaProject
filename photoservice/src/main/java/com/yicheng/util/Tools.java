package com.yicheng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yicheng.entity.Image;
import com.yicheng.entity.JPImageEntity;
import com.yicheng.entity.StudentInfo;

public class Tools {
	
	/**
	 * 检查对象是否为空
	 * @param o
	 * @return
	 */
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
	
	/**
	 * 字节数组转换成图片并输出
	 * @param bytes 待转换的字节数组
	 * @param filePath 图片输出路径
	 * */
	public static void byteArrayToImage(byte[] bytes, String filePath){
		File f = new File(filePath);
		byte[] buf = bytes;
		FileOutputStream out;
		try {
			out = new FileOutputStream(f);
			out.write(buf);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过图片路径转换为二进制字符串
	 * @param filePath 待转换的图片路径
	 * @return
	 */
	public static String ImageTobyteString(String filePath){
		File imgFile = new File(filePath);  
	     FileInputStream fis;
	     byte[]  bytes =  null;
	     StringBuilder sb = new StringBuilder();
		try {
			fis = new FileInputStream(imgFile);
			bytes = new byte[fis.available()];  
		    fis.read(bytes);
		    fis.close(); 
	        for (byte b : bytes) {
	        	sb.append(b);
			}   
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
        return sb.toString();
	}
	
	/**
	 * 驾培图片服务,解析json数组
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 */
	public static List<JPImageEntity> getJPParams(String body) throws Exception{
		return JSON.parseArray(body, JPImageEntity.class);
	}
	
	/**
	 * 驾培图片服务,解析json(单个)
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 */
	public static JPImageEntity getJPParam(String body) throws Exception{
		return JSON.parseObject(body, JPImageEntity.class);
	}
	
	/**
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParam(String body) throws Exception{
		return JSON.parseObject(body, Map.class);
	}
	
	/**
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 */
	public static List<Image> getListParams(String imgList) throws Exception{
		return JSON.parseArray(imgList, Image.class);
	}
	/**
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 */
	public static Image getListParamOne(String img) throws Exception{
		return JSON.parseObject(img, Image.class);
	}
	/**
	 * application/json
	 * @param body 
	 * @return
	 * @throws Exception
	 */
	public static String[] getStringsParam(String body) throws Exception{
		return JSON.parseObject(body,String[].class);
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static List<StudentInfo> getJPList(String json)throws Exception{
		return JSON.parseArray(json, StudentInfo.class);
	}
	/**
	 * 检查文件夹是否存在，不存在则创建
	 * @param path
	 */
	public static void createDir(String path){
		if(!"".equals(path)){
			File dirfile = new File(path);
			if(!dirfile.exists() && !dirfile.isDirectory()){
				dirfile.mkdir();    
			}
		}	
	}
}
