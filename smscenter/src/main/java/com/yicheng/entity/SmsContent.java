package com.yicheng.entity;

public class SmsContent {
	
	private String userId; //用户短信标识
	
	private String phone; //发送手机号
	
	private String message; //发送短信内容
	
	private String username; //用户名
	
	private String verificationCode;//验证码
	

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getPhone() {
		return phone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
