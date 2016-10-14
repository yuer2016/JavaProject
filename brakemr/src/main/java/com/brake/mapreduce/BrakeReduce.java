package com.brake.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class BrakeReduce extends Reducer<Text, IntWritable, NullWritable,Text> {

	private Text outputvalue = new Text();
	
	private HTable resultTable = null;
	
	private byte[] columnFamily = "v".getBytes();
	
	private byte[] qualifier ="usecount".getBytes(); 
	
	private List<Put> puts = new ArrayList<Put>();
	
	//private  JSONObject obj = new JSONObject();
	
	private StringBuilder builder = new StringBuilder(); 
		
	@Override
	protected void setup(Context context)throws IOException, InterruptedException {
		resultTable = new HTable(context.getConfiguration(), "vehicleanalysis");
	}
	/**
	 * 累加刹车次数
	 * */
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException,InterruptedException {
		int sum = 0;
		int count = 0; //上次累计值
		String rowkey =key.toString(); //结果表rowkey 
		for (IntWritable val : values) {
			sum+=val.get();
		}
		String[] strs = StringUtils.split(rowkey, "_");
		int brakeBaseNumber = Integer.parseInt(strs[2]);
		count = getusecount(rowkey);//获取上次使用刹车总记录
		int usecount = count+sum;
		putusecount(rowkey,usecount);
		/*obj.put("vehicleNo", strs[0]);
		obj.put("brakeTotalNumber", usecount);
		obj.put("brakeSurplusNumber", brakeBaseNumber-usecount);
		outputvalue.set(obj.toJSONString());*/
		NullWritable nullWritable=NullWritable.get();
		builder.append(strs[1]+",");
		builder.append(usecount+",");
		builder.append(brakeBaseNumber-usecount);
		outputvalue.set(builder.toString());
		builder.delete(0, builder.length());
		context.write(nullWritable, outputvalue);
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		resultTable.put(puts);
	}
	
	
	private int getusecount(String rowkey ) throws IOException{
		int usecount = 0;
		Get get = new Get(Bytes.toBytes(rowkey));
		get.addColumn(columnFamily, qualifier);
		Result result = resultTable.get(get);
		if(!result.isEmpty()){
			byte[] countbyte = result.getValue(columnFamily, qualifier);
			usecount = Bytes.toInt(countbyte);
		}
		
		return usecount;
	}
	
	private boolean putusecount(String rowkey,int usecount ){
		boolean results = true;
		try {
			Put put = new Put(Bytes.toBytes(rowkey));
			put.add(columnFamily, qualifier, Bytes.toBytes(usecount));
			puts.add(put);
		} catch (Exception e) {
			e.printStackTrace();
			results = false;
		}
		
		return results;
	}

}
