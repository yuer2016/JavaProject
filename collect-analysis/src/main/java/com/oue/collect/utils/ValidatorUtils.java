package com.oue.collect.utils;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.oue.collect.model.CollectModel;

/**
 * 数据校验基础类
 * @author yuer
 * */
public enum ValidatorUtils {
	
	INSTANCE; 
	
	private ValidatorUtils(){}
	/**
	 * 验证数据采集对象是否合法
	 * @param Object object 采集数据model类
	 * @return 0 - 合法   大于0 - 不合法
	 * @author yuer
	 * */
	public int Valid(Object object){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> constraintViolations =  validator.validate(object);
		return constraintViolations.size();
	}

	/**
	 * 验证采集数据状态
	 * @param CollectModel model 采集数据model类
	 * @return int 状态码
	 * @author yuer
	 * */
	public int ValidCollectModel(CollectModel model){

		return 0;
	}

	/**
	 * 返回两个时间的差值
	 * @param lastTime 上次时间
	 * @param newTime 本次时间
	 * @return 返回相差的分钟数 -1 为时间转换异常
	 * @author yuer
	 * */
	public int  diffMinutes(String lastTime,String newTime){
		int diffTime = -1;
		try {
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");    
			//时间解析    
			DateTime oldDateTime = DateTime.parse(lastTime, format); 

			DateTime newDateTime = DateTime.parse(newTime, format); 

			diffTime= Minutes.minutesBetween(oldDateTime, newDateTime).getMinutes();
		} catch (Exception e) {
			
		}
		
		
		return diffTime;
	}
}
