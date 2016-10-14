package com.locus.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LocusReduce extends Reducer<Text, FloatWritable, NullWritable,Text> {

	private Text outputvalue = new Text();
	

	//private  JSONObject obj = new JSONObject();

	private StringBuilder builder = new StringBuilder(); 

	
	private Map<String,List<Float>> speedMap = new HashMap<String,List<Float>>();
	
	
	
	/**
	 * 车辆平均最大速度
	 */
	@Override
	protected void reduce(Text key, Iterable<FloatWritable> values,Context context) throws IOException,InterruptedException {
		String rowkey =key.toString(); //结果表rowkey 
		String[] strs = StringUtils.split(rowkey, "_");
		float maxValue = 0;
		List<Float> vehicleMaxSpeed = new ArrayList<Float>();
		//单个车辆的最大速度
		for (FloatWritable val : values) {
			maxValue = Math.max(maxValue, val.get());
		}
		//全局变量speedMap存放单个车型的所有最大速度list集合
		if(speedMap.containsKey(strs[1]) ){
			speedMap.get(strs[1]).add(maxValue);
		}else{
			vehicleMaxSpeed.add(maxValue);
			speedMap.put(strs[1], vehicleMaxSpeed);
		}
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {		
		Iterator<Entry<String, List<Float>>> it = speedMap.entrySet().iterator();           
		Text keyValue = new Text();
		String key;           
		List<Float> speedValue; 
		//迭代speedMap，获取车型的速度最大平均值
		while(it.hasNext()){   
			float sum = 0;
			int count = 0;
	        Map.Entry<String, List<Float>> entry = (Map.Entry<String, List<Float>>)it.next();           
	        key=entry.getKey().toString();  
	        keyValue.set(key);
	        speedValue=(List<Float>)entry.getValue();  
	        for(Float i:speedValue){
	        	sum += i;// 计算总值
                count++;// 统计总的车辆数
	        }
	        float average = 0;
	        if(count!=0){
	        	average = sum / count;// 计算平均值
	        }
	        
	        NullWritable nullWritable=NullWritable.get();
			builder.append(key+",");
			builder.append(average);
			outputvalue.set(builder.toString());
			builder.delete(0, builder.length());
			context.write(nullWritable, outputvalue);

		}  
	}
		
}
