package com.yicheng.test;

import java.util.ArrayList;
import java.util.List;

import com.yicheng.entity.StudentInfo;

public class ToolsTest {
	
	public static void main(String[] args){
		List<StudentInfo> stuinfos = new ArrayList<StudentInfo>();
		for (int i = 0; i < 10; i++) {
			StudentInfo studentInfo = new StudentInfo();
//			studentInfo.setAppId("34838");
			studentInfo.setDeviceNo("ceshi"+i);
			studentInfo.setStudentCardID("guojunwei"+i);
			stuinfos.add(studentInfo);
		}
//		for (StudentInfo studentInfo : stuinfos) {
//			System.out.println(studentInfo.tokeys());
//		}
		
	}
	/**
	 * 
	 * /**
	 * 通过图片路径转换为二进制字符串
	 * @param filePath 待转换的图片路径
	 * @return
	 *//*
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
	 * */

}