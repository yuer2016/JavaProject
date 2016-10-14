package com.oue.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/creat")
public class EChartController {
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET,  produces = "application/json; charset=utf-8")
	public @ResponseBody  String  getEChart(@PathVariable("id") long id){
		int[] values = {5, 25, 36, 10, 10, 20};
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
		return json.toJSONString();
	}
}
