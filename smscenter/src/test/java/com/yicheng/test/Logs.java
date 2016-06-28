package com.yicheng.test;


public class Logs {

	public static void main(String[] args) {
		/*SmsController smsController = new SmsController();
		smsController.testsend("{'smsId':'1','phone':'111','message':'1111'}");*/
		String str = "{0}姐姐觉得{1}";
		
		/*str = str.replace("{0}", "111");
		str = str.replace("{1}", "222");*/
		System.out.println(str.replace("{0}", "111"));
	}

}
