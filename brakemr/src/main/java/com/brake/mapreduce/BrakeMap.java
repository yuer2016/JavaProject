package com.brake.mapreduce;

import java.io.IOException;




import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class BrakeMap extends TableMapper<Text, IntWritable> {
	private Logger logger = Logger.getLogger(BrakeMap.class);
	private JSONParser parser = new JSONParser();
	public enum Counters { ROWS, COLS, ERROR, VALID }
	private Text outputkey = new Text();
	private IntWritable outputvalue = new IntWritable();
	/**
	 * 提取每辆车的刹车次数
	 * */
	@Override
	protected void map(ImmutableBytesWritable row,Result columns,Context context)throws IOException, InterruptedException {
		context.getCounter(Counters.ROWS).increment(1);
		String result = null;
		String key = null;
		int value = 0;
		boolean isempty = false;
		String brakeTotalNumber = null;
		try {
			for (KeyValue kv : columns.list()) {
				result = Bytes.toStringBinary(kv.getValue());
				JSONObject json = (JSONObject)parser.parse(result);
				String flag = json.get("flag").toString();
				
				if("1".equals(flag)){
					context.getCounter(Counters.COLS).increment(1);
					brakeTotalNumber = json.get("brakeTotalNumber").toString();
					isempty = StringUtils.isNotBlank(brakeTotalNumber);
					key = json.get("vehicleNo").toString()+"_"+json.get("brakesId").toString()+"_"+json.get("brakeBaseNumber").toString();
					if(isempty){
						value =Integer.parseInt(brakeTotalNumber);
						outputkey.set(key);
						outputvalue.set(value);
						context.write(outputkey, outputvalue);
						context.getCounter(Counters.VALID).increment(1);
					}
				}
				
				
			}
		} catch (Exception e) {
			logger.debug(e);
			context.getCounter(Counters.ERROR).increment(1);
		}
		
	}
	
	

}
