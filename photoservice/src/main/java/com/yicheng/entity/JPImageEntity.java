package com.yicheng.entity;

import org.apache.hadoop.hbase.util.Bytes;
//import org.json.simple.JSONObject;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 *驾培图片entity
 */
public class JPImageEntity {

	//图片二进制流64位
	private byte[] binaryImage; 
	//图片日期
	private String imageDate;
	//学员姓名
	private String studentName;
	//车机号
	private String deviceNo;
	//学员卡号
	private String studentCardID;

	public byte[] getBinaryImage() {
		return binaryImage;
	}
	
	public void setBinaryImage(byte[] binaryImage) {
		this.binaryImage = binaryImage;
	}
	
	public String getImageDate() {
		return imageDate;
	}
	
	public void setImageDate(String imageDate) {
		this.imageDate = imageDate;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getDeviceNo() {
		return deviceNo;
	}
	
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	public String getStudentCardID() {
		return studentCardID;
	}
	
	public void setStudentCardID(String studentCardID) {
		this.studentCardID = studentCardID;
	}
	
	/*
	 * 类转化JSON字符串
	 */
	public String toJSON(){
		JSONObject json = new JSONObject();
		json.put("binaryImage", Bytes.toStringBinary(this.getBinaryImage()));
		json.put("imageDate", this.getImageDate());
		json.put("studentName", this.getStudentName());
		return json.toJSONString();
	}
	
}
