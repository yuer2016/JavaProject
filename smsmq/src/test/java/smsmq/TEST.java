package smsmq;

import com.yicheng.util.SendSMSUtil;

public class TEST {
	
	public static void main(String[] args)throws Exception{
		
		int result = SendSMSUtil.SmsSends("15929554571", "测试单条发送");
		System.out.println(result);
	}

}
