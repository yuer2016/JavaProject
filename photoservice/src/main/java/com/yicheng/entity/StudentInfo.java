package com.yicheng.entity;

import java.util.List;

/**
 * 
 * 学时信息
 *
 */
public class StudentInfo {
	
	private String keys;
	
	//开始时间
	private String begindate;
	
	//结束时间
	private String enddate;
	
	//车机号
	private String deviceNo;
	
	//学员卡号
	private String studentCardID;
	
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

	private List<Image> images;

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Image> getImages() {
		return images;
	}
	
}
