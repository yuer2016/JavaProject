package com.oil.mapreduce;

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


public class OilMap extends TableMapper<Text, FloatWritable> {
	private Logger logger = Logger.getLogger(OilMap.class);
	private JSONParser parser = new JSONParser();
	public enum Counters { ROWS, COLS, ERROR, VALID }
	private Text outputkey = new Text();
	private FloatWritable outputvalue = new FloatWritable();
	/**
	 * 提取每辆车的油耗
	 * */
	@Override
	protected void map(ImmutableBytesWritable row,Result columns,Context context)throws IOException, InterruptedException {
		context.getCounter(Counters.ROWS).increment(1);
		String result = null;
		String key = null;
		float value = 0;
		boolean isempty = false;
		String oilConsume = null;
		try {
			for (KeyValue kv : columns.list()) {

				result = Bytes.toStringBinary(kv.getValue());
				JSONObject json = (JSONObject)parser.parse(result);
				String flag = json.get("flag").toString();

				if("2".equals(flag)){
					context.getCounter(Counters.COLS).increment(1);
					oilConsume = json.get("oilConsume").toString();
					isempty = StringUtils.isNotBlank(oilConsume);
					key = json.get("vehicleNo").toString()+"_"+json.get("vehicleType").toString()+"_"+json.get("oilBaseConsume").toString();
					if(isempty){
						value = Float.parseFloat(oilConsume);
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
