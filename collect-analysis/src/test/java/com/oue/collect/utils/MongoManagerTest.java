package com.oue.collect.utils;


import org.bson.Document;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class MongoManagerTest {
	
	void testMongo(){
		MongoDatabase DB = MongoManager.INSTANCE.getDB("qxt");
		MongoCollection<Document> user = DB.getCollection("student");
		Document document = Document.parse("{'eid':'ent:123'}");

		user.insertOne(document);

		user.deleteOne(eq("eid", "ent:123"));
	}

}
