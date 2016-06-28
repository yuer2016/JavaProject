package com.analysis.reduce;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 学员学时数据分析reduce
 * @author yuer
 * */
public class LearningDataReduce extends Reducer<Text, Text, NullWritable,Text> {

	private Text outputvalue = new Text();////输出hdfs值实例方便重用
	
	//private HTable resultTable = null;//中间结果表HBASE实例
	
	//private List<Put> puts = new ArrayList<Put>();//保存分析结果put列表
	
	private NullWritable nullWritable=NullWritable.get();//输出hdfs键实例方便重用
	
	private StringBuilder builder = new StringBuilder();//拼装最终结果
	
	private String rowkey = null;//中间结果表rowkey
	
	private String area = null; //所属区域
	
	private String idcard = null;//学员身份证号
	@Override
	protected void setup(Context context)throws IOException, InterruptedException {
		//resultTable = new HTable(context.getConfiguration(), TableInfo.STN_RESULT);
	}
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)throws IOException, InterruptedException {
		double[] stu = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};//存储分析各个科目的有效和无效学时
		String results = null;
		String[] resultArray =null;
		String resultvalue = null;
		rowkey = key.toString();
		/*
		 * 如果结果表有数据则重新赋值
		 * 
		if(StringUtils.isNotBlank(getusecount(rowkey))){
			resultArray = StringUtils.split(getusecount(rowkey), ",");
			for (int i = 0; i < 14; i++) {
				stu[i] = Double.parseDouble(resultArray[i+2]);
			}
		}*/
		area = StringUtils.substring(rowkey,0, 6);
		idcard = StringUtils.substring(rowkey, 6); 
		/*
		 *对每个学员各个科目的有效和无效学时进行累加
		 * */
		for (Text test : values) {
			results = test.toString();
			resultArray = StringUtils.split(results, ",");
			switch (Integer.parseInt(resultArray[0])) {
			case 11:
				stu[0] += Double.parseDouble(resultArray[1]);
				stu[1] += Double.parseDouble(resultArray[2]);
				break;
			case 21:
				stu[2] += Double.parseDouble(resultArray[1]);
				stu[3] += Double.parseDouble(resultArray[2]);
				break;
			case 22:
				stu[4] += Double.parseDouble(resultArray[1]);
				stu[5] += Double.parseDouble(resultArray[2]);
				break;
			case 23:
				stu[6] += Double.parseDouble(resultArray[1]);
				stu[7] += Double.parseDouble(resultArray[2]);
				break;
			case 31:
				stu[8] += Double.parseDouble(resultArray[1]);
				stu[9] += Double.parseDouble(resultArray[2]);
				break;
			case 32:
				stu[10] += Double.parseDouble(resultArray[1]);
				stu[11] += Double.parseDouble(resultArray[2]);
				break;
			case 33:
				stu[12] += Double.parseDouble(resultArray[1]);
				stu[13] += Double.parseDouble(resultArray[2]);
				break;

			default:
				break;
			}

		}
		/*
		 * 拼装分析结果
		 * */
		builder.append(area+",");//所属区域
		builder.append(idcard+",");//学员身份证号
		for (double number : stu) {
			builder.append(number+",");//各个科目的有效无效学时
		}
		builder.append(resultArray[3]+",");//驾校名称
		builder.append(resultArray[4]);//学员名称
		resultvalue = builder.toString();
		builder.delete(0, builder.length());
		outputvalue.set(resultvalue);
		/*putusecount(rowkey,resultvalue);
		if(puts.size() >100){
			resultTable.put(puts);
			puts.clear();
		}*/
		context.write(nullWritable, outputvalue);
	}
	
	@Override
	protected void cleanup(Context context) throws IOException ,InterruptedException {
		//resultTable.put(puts);
	};


	/**
	 * 根据rowkey获取中间结果表中上次的分析记录
	 * @author yuer
	 * */
	/*
	private String getusecount(String rowkey) throws IOException{
		String res = null;
		Get get = new Get(Bytes.toBytes(rowkey));
		get.addColumn(TableInfo.STNR_LEARN_FAMILY, TableInfo.STNR_LEARN_QUALIFIER);
		Result result = resultTable.get(get);
		if(!result.isEmpty()){
			byte[] countbyte = result.getValue(TableInfo.STNR_LEARN_FAMILY, TableInfo.STNR_LEARN_QUALIFIER);
			res = Bytes.toString(countbyte);
		}
		return res;
	}**/
	/**
	 * 保存这次的分析结果LIST到中间结果表
	 * @author yuer
	 * */
	/*
	private boolean putusecount(String rowkey,String usecount ){
		boolean results = true;
		try {
			Put put = new Put(Bytes.toBytes(rowkey));
			put.add(TableInfo.STNR_LEARN_FAMILY, TableInfo.STNR_LEARN_QUALIFIER, Bytes.toBytes(usecount));
			puts.add(put);
		} catch (Exception e) {
			e.printStackTrace();
			results = false;
		}
		
		return results;
	}**/


}
