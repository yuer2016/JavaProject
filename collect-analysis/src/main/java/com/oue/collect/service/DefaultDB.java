package com.oue.collect.service;

import java.util.HashMap;
import java.util.Map;

import com.oue.collect.dao.CollectMongoDB;
import com.oue.collect.dao.CollectRedis;
import com.oue.collect.dao.impl.CollectMongoDBImpl;
import com.oue.collect.dao.impl.CollectRedisImpl;
import com.oue.collect.model.CollectModel;
import com.oue.collect.utils.Algorithms;
import com.oue.collect.utils.ValidatorUtils;

public class DefaultDB extends DBService {

	private final CollectMongoDB  mongodb = new  CollectMongoDBImpl();

	private final CollectRedis  redis = new CollectRedisImpl();

	private static final String  DATABASE = "qxt"; //MongoDB数据库名称

	private static final String  TABLENAME = "collect"; //MongoDB数据表名称

	private static final String  ERRORTB = "collectError";//MongoDB数据库错误数据表名称

	private static final String  RK_CT = "ct"; //采集产量

	private static final String  RK_TM = "tm"; //采集时间

	private static final String  RK_CYCLE = "cycle"; //生产周期

	/**
	 * 分析并存储采集结果
	 * @param CollectModel model 采集器上传原始正确数据
	 * @author yuer
	 * */
	@Override
	public void saveAnalysisModel(CollectModel model) {
		int status = ValidatorUtils.INSTANCE.ValidCollectModel(model); //根据采集model 获得采集中的状态
		switch (status) {
		case 0:
			saveModel(model);
			break;
		default:
			break;
		}

	}
	
	/**
	 * 存储正常的采集器json数据到mongodb
	 * @param String json 采集器上传原始正确数据
	 * @author yuer  
	 * */
	@Override
	public void saveModel(String json) {
		mongodb.saveCollect(DATABASE, TABLENAME, json);
	}
	/**
	 * 存储异常的采集器json数据到mongodb
	 * @param String json 采集器上传原始错误数据
	 * @author yuer 
	 * */
	@Override
	public void saveErrorModel(String json) {
		mongodb.saveCollect(DATABASE, ERRORTB, json);
	}
	
	/**
	 * 保存结果数据
	 * @param CollectModel model 采集数据model
	 * @author yuer
	 * */
	private void  saveModel(CollectModel model){
		Map<String, String> map = redis.hgetAll(model.getEid()); //获得上次的分析结果
		if(map.size() == 0){ //如果没有上次分析结果(第一次)，直接保存！
			saveResultToRedis(model);
		}else{
			int lastCt = Integer.parseInt(map.get(RK_CT)); //获取上次保存的产品增量
			model.setCt(Algorithms.INSTANCE.getCt(lastCt, model.getCt())); //计算得出本次的产量
			saveResultToRedis(model);
		}
	}
	
	/**
	 * 将数据正确的分析结果保存到Redis
	 * */
	private void saveResultToRedis(CollectModel model){
		String cycle = Algorithms.INSTANCE.getCycle(model.getR0(), model.getR1()); //计算开合周期
		Map<String, String> redisValues = new HashMap<>();
		redisValues.put(RK_TM, model.getTm());
		redisValues.put(RK_CT, String.valueOf(model.getCt()));
		redisValues.put(RK_CYCLE, cycle);
		redis.hmset(model.getEntid(), redisValues);	
	}



}
