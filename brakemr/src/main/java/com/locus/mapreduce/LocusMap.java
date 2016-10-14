package com.locus.mapreduce;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.brake.mapreduce.BrakeMap;

public class LocusMap extends TableMapper<Text, FloatWritable> {

	private Logger logger = Logger.getLogger(BrakeMap.class);
	private JSONParser parser = new JSONParser();
	public enum Counters { ROWS, COLS, ERROR, VALID }
	private Text outputkey = new Text();
	private FloatWritable outputvalue = new FloatWritable();
	
	/**
	 * 获取车辆的平均最大速度
	 */
	@Override
	protected void map(ImmutableBytesWritable row,Result columns,Context context)throws IOException, InterruptedException {
		//每取一条记录，计数器累加
		context.getCounter(Counters.ROWS).increment(1);
		String result = null;
		String key = null;
		int value = 0;
		boolean isempty = false;
		String locusSpeed = null;
		try {
			for (KeyValue kv : columns.list()) {
				result = Bytes.toStringBinary(kv.getValue());
				JSONObject json = (JSONObject)parser.parse(result);
				String flag = json.get("flag").toString();
				
				if("3".equals(flag)){
					context.getCounter(Counters.COLS).increment(1);
					locusSpeed = json.get("speed").toString();
					isempty = StringUtils.isNotBlank(locusSpeed);
					key = json.get("vehicleNo").toString()+"_"+json.get("vehicleType").toString();
					if(isempty){
						value =Integer.parseInt(locusSpeed);
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
