package com.yicheng.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.yicheng.entity.Response;

public abstract class CommonController  {
	
	public void parseRequest(HttpServletRequest request, Response respon){
        String appId = request.getHeader("appId");
        String token = request.getHeader("token");
        String serviceURL = request.getHeader("serviceURL");
        respon.setAppId(appId);
        respon.setToken(token);
        respon.setServiceURL(serviceURL);
	}
	
	/**
	 * 错误处理
	 * @param e
	 * @param response
	 * @param logger
	 */
	protected void errorHandler(Exception e, Response response, Logger logger){
	   	 if(e instanceof java.net.SocketTimeoutException){
	       	 response.setMessage(1, "接口访问超时！");
	   	 }else{
	   		response.setMessage(1, "系统处理异常");
	   	 }
	   	logger.error(response.getMessage(),e);
   }
}
