package com.oue.collect.main;

import com.alibaba.fastjson.JSON;
import com.oue.collect.model.CollectModel;
import com.oue.collect.service.DBService;
import com.oue.collect.service.DefaultDB;
import com.oue.collect.utils.ValidatorUtils;

public class CollectAnalysis {
	
	private final DBService  dbService =  new  DefaultDB();
	
	public  void CollectData(String json){
		CollectModel model = JSON.parseObject(json, CollectModel.class);
		int isvalidator = ValidatorUtils.INSTANCE.Valid(model);  //model数据是否合法 
		switch (isvalidator) {
		case 0:
			dbService.saveModel(json);
			dbService.saveAnalysisModel(model);
			break;
		default:
			dbService.saveErrorModel(json);
			break;
		}
	}

}
