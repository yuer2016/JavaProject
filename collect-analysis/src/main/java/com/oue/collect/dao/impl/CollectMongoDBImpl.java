package com.oue.collect.dao.impl;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.oue.collect.dao.CollectMongoDB;
import com.oue.collect.utils.MongoManager;

public class CollectMongoDBImpl implements CollectMongoDB {
	private static final Logger logger = LoggerFactory.getLogger(CollectMongoDBImpl.class);
	
	/**
	 * 保存采集数据
	 * @author yuer
	 * @param json 采集数据
	 * @param databases 数据库名称
	 * @param tablename 数据表名称
	 * */
	@Override
	public void saveCollect(String databases, String tablename, String json) {
		logger.info("mongo install data :",json);
		MongoDatabase database = MongoManager.INSTANCE.getDB(databases);
		MongoCollection<Document> collect = database.getCollection(tablename);
		collect.insertOne(Document.parse(json));
		
	}

}
