package com.oue.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Test {

	public static void main(String[] args) {
		int[] values = {5, 20, 36, 10, 10, 20};
		String[] name = {"衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"};
		JSONArray xAxis = new  JSONArray();
		for (int v : values) {
			xAxis.add(v);
		}
		JSONArray series = new  JSONArray();
		
		for (String n : name) {
			series.add(n);
		}
		
		JSONObject  json =  new  JSONObject();
		json.put("title", "不良品分析");
		json.put("legend", "销量");
		
		
		json.put("xAxis", xAxis);
		json.put("series", series);
		
		System.out.println(json.toJSONString());

	}

}
