package com.yicheng.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;



public class SendSMSUtil {
	
	private static Logger  logger = Logger.getLogger(SendSMSUtil.class);

	private static final  String  sendurl = "http://www.96888sms.cn/api.php/open/sendm";

	public static int SmsSends(String phone,String content) throws IOException{
		int result = 0;
		StringBuilder smsurl = new StringBuilder();
		smsurl.append(sendurl);
		smsurl.append("/name/ycjt");
		smsurl.append("/pwd/B259022286CA0E790E99E7A06FB252F7");
		// 向StringBuffer追加手机号码
		smsurl.append("/phone/"+phone);
		// 向StringBuffer追加消息内容转URL标准码
		smsurl.append("/msg/"+URLEncoder.encode(content+"【亿程信息 定位服务专家】","UTF-8"));
		
		logger.info(smsurl.toString());
		
		URL url = new URL(smsurl.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		InputStream is =url.openStream();
		
		result = Integer.parseInt(convertStreamToString(is));
		
		
		return result;
	}
	
	
	private static String convertStreamToString(InputStream is) {    
        StringBuilder sb = new StringBuilder();    
        byte[] bytes = new byte[4096];  
        int size = 0;  
        
        try {    
        	while ((size = is.read(bytes)) > 0) {  
                String str = new String(bytes, 0, size, "UTF-8");  
                sb.append(str);  
            }  
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb.toString();    
    }

}
