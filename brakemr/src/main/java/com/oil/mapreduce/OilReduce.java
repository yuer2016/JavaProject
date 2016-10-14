package com.oil.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.json.simple.JSONObject;

public class OilReduce extends Reducer<Text, FloatWritable, Text,Text> {

	private Text outputvalue = new Text();
	
	private JSONObject obj = new JSONObject();
	
	private Map<String,List<Float>> oilMap = new HashMap<String,List<Float>>();
	
	/**
	 * 累加油耗
	 * */
	@SuppressWarnings("unchecked")
	@Override
	protected void reduce(Text key, Iterable<FloatWritable> values,Context context) throws IOException,InterruptedException {
		float sum = 0;
		int count = 0;
		String rowkey =key.toString(); //结果表rowkey 
		String[] strs = StringUtils.split(rowkey, "_");
		List<Float> oilConsume = new ArrayList<Float>();
		//单个车辆的油耗总值
		for (FloatWritable val : values) {
			sum += val.get();
			count ++;
		}
		//每辆车的平均油耗
		float avg = sum / count;

		JSONObject vehicleObj = new JSONObject();
		
		//全局变量oilMap存放单个车型的所有油耗总量的list集合
		if(oilMap.containsKey(strs[1]) ){
			oilMap.get(strs[1]).add(avg);
		}else{
			oilConsume.add(avg);
			oilMap.put(strs[1], oilConsume);
		}
		
		vehicleObj.put("vehicleNo", strs[0]);
		vehicleObj.put("avgOil", avg);
		vehicleObj.put("count", count);
		outputvalue.set(vehicleObj.toJSONString());
		context.write(key, outputvalue);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		Text key = new Text();
		List<Float> oilConsume;
		for (Entry<String, List<Float>> oil : oilMap.entrySet()) {
			float sum = 0;
			int count = 0;
			String mapKey = oil.getKey();
			key.set(mapKey);
			oilConsume = oil.getValue();
			for (Float i : oilConsume) {
				
				sum += i;// 计算车辆型号油耗总值
				count ++;// 统计车辆型号下总的车辆数
			}
			
			float average = sum / count;
			obj.put("vehicleType", key);
			obj.put("vehicleTypeAvg", average);
			outputvalue.set(obj.toJSONString());
			context.write(key, outputvalue);
			
		}
		
	}

}
