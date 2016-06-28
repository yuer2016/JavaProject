package com.yicheng.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class Response implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO() 
	*/ 
	private static final long serialVersionUID = -1599411678301387889L;

	/**
	 * 获取token成功�?00
	 * 获取token失败�?01
	 * 验证成功�?00
	 * 验证失败�?01
	 */
	private int code = 0;
	
	private String token = "";
	
	private String appId = "";
	
	private String secretId = "";
	
	private String serviceURL = "";
	
	private String redirectURL = "";
	
	private Object result = "";
	
	private String message = "";
	
	public Response(){}
	
	public Response(int code, String token, String appId, String secretId, String serviceURL, String redirectURL, Object result, String message){
		this.code = code;
		this.token = token;
		this.appId = appId;
		this.secretId = secretId;
		this.serviceURL = serviceURL;
		this.redirectURL = redirectURL;
		this.result = result;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String toJSON(){
		return JSON.toJSONString(this);
	}
}
