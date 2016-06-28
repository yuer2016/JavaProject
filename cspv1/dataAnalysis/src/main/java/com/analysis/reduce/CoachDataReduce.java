package com.analysis.reduce;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 教练学时数据分析Reducer
 * @author yuer
 * */
public class CoachDataReduce extends Reducer<Text, Text, NullWritable,Text> {

	private Text outputvalue = new Text(); //输出hdfs值实例方便重用
	
	private NullWritable nullWritable=NullWritable.get();//输出hdfs键实例方便重用

	//private HTable resultTable = null;//中间结果表HBASE实例
 
	//private List<Put> puts = new ArrayList<Put>();//保存分析结果put列表

	private StringBuilder builder = new StringBuilder();//拼装最终结果
	
	private String rowkey = null; //中间结果表rowkey
	/*
	 * 初始化中间结果表
	 * */
	@Override
	protected void setup(Context context)throws IOException, InterruptedException {
		//resultTable = new HTable(context.getConfiguration(), TableInfo.STN_RESULT);
	}

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)throws IOException, InterruptedException {
		double[] stu = {0,0}; //存储分析的有效和无效学时
		String results = null;
		String[] resultArray =null;
		String resultvalue = null;
		rowkey = key.toString();
		/*
		 * 如果结果表有数据则重新赋值
		 * 
		if(StringUtils.isNotBlank(getusecount(rowkey))){
			resultArray = StringUtils.split(getusecount(rowkey), ",");
		    stu[0] = Double.parseDouble(resultArray[2]);
		    stu[1] = Double.parseDouble(resultArray[3]);	
		}*/
		/*
		 *对每个教练所教有效和无效学时进行累加
		 * */
		for (Text test : values) {
			results = test.toString();
			resultArray = StringUtils.split(results, ",");
			stu[0]+=Double.parseDouble(resultArray[0]);
			stu[1]+=Double.parseDouble(resultArray[1]);
		}
		/*
		 * 拼装分析结果
		 * */
		builder.append(rowkey+",");//教练身份证号码
		builder.append(resultArray[4]+",");//教练名称
		for (double number : stu) {
			builder.append(number+","); //(有/无)效学时
		}
		builder.append(resultArray[2]+",");//驾校所属区域
		builder.append(resultArray[3]);//驾校名称
		
		resultvalue = builder.toString();
		builder.delete(0, builder.length());
		outputvalue.set(resultvalue);
		
		/**
		putusecount(rowkey,resultvalue); //将结果保存到hbase中间结果表
		if(puts.size() >100){
			resultTable.put(puts);
			puts.clear();
		}**/
		context.write(nullWritable, outputvalue);

	}

	@Override
	protected void cleanup(Context context)throws IOException, InterruptedException {
		//resultTable.put(puts);
	}
	/**
	 * 根据rowkey获取中间结果表中上次的分析记录
	 * @author yuer
	 * 
	private String getusecount(String rowkey) throws IOException{
		String res = null;
		Get get = new Get(Bytes.toBytes(rowkey));
		get.addColumn(TableInfo.STNR_COACH_FAMILY, TableInfo.STNR_COACH_QUALIFIER);
		Result result = resultTable.get(get);
		if(!result.isEmpty()){
			byte[] countbyte = result.getValue(TableInfo.STNR_COACH_FAMILY, TableInfo.STNR_COACH_QUALIFIER);
			res = Bytes.toString(countbyte);
		}
		return res;
	}*/
	/**
	 * 保存这次的分析结果LIST到中间结果表
	 * @author yuer
	 * 
	private boolean putusecount(String rowkey,String resultvalue ){
		boolean results = true;
		try {
			Put put = new Put(Bytes.toBytes(rowkey));
			put.add(TableInfo.STNR_COACH_FAMILY, TableInfo.STNR_COACH_QUALIFIER, Bytes.toBytes(resultvalue));
			puts.add(put);
		} catch (Exception e) {
			e.printStackTrace();
			results = false;
		}
		
		return results;
	}*/

}
