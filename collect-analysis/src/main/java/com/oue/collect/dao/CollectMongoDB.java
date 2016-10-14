package com.oue.collect.dao;

/**
 * mongoDB 操作封装类
 * @author yuer
 * */
public interface CollectMongoDB {
	
	void saveCollect(String database,String tablename,String json);
}
