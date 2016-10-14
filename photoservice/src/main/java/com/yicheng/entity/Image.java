package com.yicheng.entity;

public class Image {

	//图片标识符
	private String key;
	
	//图片url 
	private String value;
	
	//图片二进制流64位
	private byte[]  binaryImage;
	
	//图片日期
	private String imageDate;

	public String getImageDate() {
		return imageDate;
	}

	public void setImageDate(String imageDate) {
		this.imageDate = imageDate;
	}

	public byte[] getBinaryImage() {
		return binaryImage;
	}

	public void setBinaryImage(byte[] binaryImage) {
		this.binaryImage = binaryImage;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
