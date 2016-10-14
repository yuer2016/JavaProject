package com.oue.collect.utils;

import java.util.Properties;

/**
 * 配置通用类
 * @author yuer
 * */
public enum ConfigUtils {
	
	INSTANCE; 
	
	private ConfigUtils(){}

	/**
	 * 初始化Properties配置文件信息
	 * @author yuer
	 * */
	public Properties initProperties(){
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties"));	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
}
