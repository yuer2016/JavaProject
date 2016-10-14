package com.oue.collect.service;

import com.oue.collect.model.CollectModel;

public abstract class DBService {
	
 	public abstract void saveAnalysisModel(CollectModel model);
 	
 	public abstract void saveModel(String json);
 	
 	public abstract void saveErrorModel(String json);
 
}
