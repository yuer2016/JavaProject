package com.yicheng.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * properties文件工具类
 */
public class PropertiesUtil {
	private static Properties prop = null;
	static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    /**
     * load config
     **/
	static {
		loadProp();
	}
    /**
     * load the config into cache
     **/
	public static void loadProp(){
		try {
			prop = new Properties();
			prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("filecatalog.properties"));
		} catch (IOException e) {
			logger.info("load config failed:"+e.getMessage());
		}
	}
    /**
     * 获取写入图片地址
     **/
    public static String getInputImageAddress(){
    	return prop.getProperty("filedir", "");
    }
    /**
     * 获取输出图片地址
     **/
    public static String getOutputImageAddress(){
    	return prop.getProperty("ipPort", "") + "/" + prop.getProperty("imgPath", "");
    }
    /**
     * 获取域名输出图片地址
     **/
    public static String getDomainImageAddress(){
    	return prop.getProperty("domain", "") + "/" + prop.getProperty("imgPath", "");
    }

}