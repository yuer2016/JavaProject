package com.oue.collect.utils;

import java.util.Properties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 * mongodb 连接池
 * @author yuer
 * */
public enum MongoManager {
	
	INSTANCE;  
	
	private MongoClient mongoClient = null;  
	
	/**
	 *构造函数初始化客户端
	 *@author yuer
	 * */
	
	private MongoManager() {
		initDBPrompties();
	}  

	/**
	 * 获取mongoDB数据库
	 * @param dbName 数据库名称
	 * @return mongoClient
	 * */
	public  MongoDatabase getDB(String dbName) {  
		return mongoClient.getDatabase(dbName);
	}  

	/** 
	 * 初始化连接池 
	 * @author yuer
	 */  
	private void initDBPrompties() {
		Properties properties = ConfigUtils.INSTANCE.initProperties();
		ServerAddress  serverAddress = new ServerAddress(properties.getProperty("MongoDB.host"),Integer.parseInt(properties.getProperty("MongoDB.port"))); 
		MongoClientOptions  options = new MongoClientOptions.Builder().connectionsPerHost(Integer.parseInt(properties.getProperty("MongoDB.poolsize"))).
				threadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(properties.getProperty("MongoDB.blocksize"))).build();
		mongoClient = new  MongoClient(serverAddress,options);
	}
}
