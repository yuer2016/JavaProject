package com.oue.collect.dao;

import java.util.Map;

/**
 * redis 操作封装类
 * @author yuer
 * */
public interface CollectRedis {
	
	String  hmset(String key,  Map<String, String> hash);
	
	Map<String, String> hgetAll(String key);
	
	String hget(String key,String field);
	
	
}
