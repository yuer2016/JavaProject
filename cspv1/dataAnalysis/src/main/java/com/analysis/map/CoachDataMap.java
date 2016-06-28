package com.analysis.map;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 教练学时数据分析MAP
 * @author yuer
 * */
public class CoachDataMap extends TableMapper<Text, Text> {
	private static final Logger logger=LoggerFactory.getLogger(CoachDataMap.class);
	private JSONParser parser = new JSONParser();
	public enum Counters { ROWS, COLS, ERROR, VALID }
	private Text outputkey = new Text();
	private Text outputvalue = new Text();
	private StringBuilder builder = new StringBuilder(); 
	@Override
	protected void map(ImmutableBytesWritable row, Result columns,Context context) throws IOException, InterruptedException {
		context.getCounter(Counters.ROWS).increment(1);
		String result = null;
		try {
			for (KeyValue kv : columns.list()) {
				result = Bytes.toString(kv.getValue());
				JSONObject json = (JSONObject)parser.parse(result);
				//有效学时
				if(StringUtils.isNotBlank(json.get("validActualTime")+"") ){
					builder.append(json.get("validActualTime")+",");
				}else{
					builder.append(0+",");
				}
				//无效学时
				if(StringUtils.isNotBlank(json.get("invalidActualTime")+"") ){
					builder.append(json.get("invalidActualTime")+",");
				}else{
					builder.append(0+",");
				}
				builder.append(StringUtils.substring(Bytes.toString(columns.getRow()),0, 6)+",");//所属区域编号
				builder.append(json.get("name")+","); //驾校名称
				
				if(StringUtils.isNotBlank(json.get("teacher")+"") ){
					builder.append(json.get("teacher")+",");
				}else{
					builder.append("无名"+",");
				}
				outputkey.set(json.get("tidCardNo")+"");//教练身份证号
				logger.debug(builder.toString());
				outputvalue.set(builder.toString());
				builder.delete(0, builder.length());
				context.write(outputkey, outputvalue);
				context.getCounter(Counters.VALID).increment(1);

			}
		} catch (Exception e) {
			logger.error(e.toString());
			context.getCounter(Counters.ERROR).increment(1);
		}
	}

}
