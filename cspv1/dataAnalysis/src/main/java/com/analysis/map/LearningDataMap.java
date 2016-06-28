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
 * 学员学习数据分析Map
 * @author yuer
 * */
public class LearningDataMap extends TableMapper<Text, Text> {

	private static final Logger logger= LoggerFactory.getLogger(LearningDataMap.class);
	private JSONParser parser = new JSONParser();
	public enum Counters { ROWS, COLS, ERROR, VALID }
	private Text outputkey = new Text();
	private Text outputvalue = new Text();
	private StringBuilder builder = new StringBuilder(); 

	@Override
	protected void map(ImmutableBytesWritable row, Result columns,Context context)throws IOException, InterruptedException {
		context.getCounter(Counters.ROWS).increment(1);
		String result = null;
		int flage = 0;
		try {
			for (KeyValue kv : columns.list()) {
				result = Bytes.toString(kv.getValue());
				JSONObject json = (JSONObject)parser.parse(result);
				flage = getCourseAndTypeFlage(json.get("studyCourse").toString(),json.get("studyType").toString());
				builder.append(flage+",");
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
				builder.append(json.get("name")+","); //驾校名称
				builder.append(json.get("sname")+"");//学员名称
				outputkey.set(Bytes.toString(columns.getRow()));
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

	/**
	 * 获得对应驾校所学科目类型的标志位 
	 * @param studyCourse 所学科目
	 * @param studyType 学习类型
	 * @return  int 标志位（11-科目一 21-科目二理论 22-科目二模拟 23-科目二实操 31-科目三理论 32-科目三模拟 33-科目三实操）
	 * @author yuer 
	 * */
	private int getCourseAndTypeFlage(String studyCourse,String studyType){
		int result = 0;
		if(studyCourse.equals("科目一")){
			result = 11;
		}else if(studyCourse.equals("科目二")){
			result = 20+getstudyType(studyType);
		}else if(studyCourse.equals("科目三")){
			result = 30+getstudyType(studyType);
		}

		return result;
	}
	/**
	 * 获得学习类型标志
	 * @param stuType 学习类型
	 * @return int 标志位(1-理论 2-模拟 3-实操)
	 * @author yuer
	 * */
	private int getstudyType(String stuType){
		int result = 0;
		if(stuType.equals("理论")){
			result = 1;
		}else if(stuType.equals("模拟")){
			result = 2;
		}else if(stuType.equals("实操")){
			result = 3;
		}
		return result;
	}

}
