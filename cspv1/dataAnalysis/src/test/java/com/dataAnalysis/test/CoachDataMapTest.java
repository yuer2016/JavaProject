package com.dataAnalysis.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CoachDataMapTest {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("name");
		list.clear();
		list.add("sex");
		for (String string : list) {
			System.out.println(string);
		}

	}
	
	void map() throws ParseException{
		JSONParser parser = new JSONParser();
		StringBuilder builder = new StringBuilder();
		JSONObject json = (JSONObject)parser.parse("");
		//有效学时
		if(StringUtils.isNotBlank(json.get("validActualTime")+"") ){
			builder.append(json.get("validActualTime")+",");
		}else{
			builder.append(0+",");
		}
		//无效学时
		if(StringUtils.isNotBlank(json.get("invalidActualTime")+"") ){
			builder.append(json.get("invalidActualTime")+",");
		}else{
			builder.append(0+",");
		}
		builder.append(json.get("name")+",");//所属区域编号
		builder.append(json.get("name")+","); //驾校名称
		System.out.println(builder.toString());
	}
	

}
