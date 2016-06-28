package com.watermark.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Writable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.watermark.util.Tools;






public class WaterMarkMap extends TableMapper<ImmutableBytesWritable, Writable> {
	
	private ImmutableBytesWritable immutableBytesWritable = new ImmutableBytesWritable();
	enum Counters { ROWS, COLS, ERROR, VALID }
	private JSONParser parser = new JSONParser();
	private List<Delete> delete = new ArrayList<Delete>();
	private HTable resultTable = null;
	@Override
	protected void setup(Context context) throws IOException ,InterruptedException {
		resultTable = new HTable(context.getConfiguration(), "photo");
	};
	@Override
	protected void map(ImmutableBytesWritable row, Result columns, Context context)
			throws IOException, InterruptedException {
		context.getCounter(Counters.ROWS).increment(1);
		try {
			String result = null;
			for (KeyValue kv : columns.list()) {
				result = Bytes.toString(kv.getValue());
				JSONObject json = (JSONObject)parser.parse(result);
				if(StringUtils.isNotBlank(json.get("binaryImage")+"")&&StringUtils.isNotBlank(json.get("studentName")+"")){
					StringBuilder key = new StringBuilder();
					key.append("jp1");
					key.append(StringUtils.substring(Bytes.toString(kv.getRow()), 3));
					Put put = new Put(Bytes.toBytes(key.toString()));
					put.add(kv.getFamily(), kv.getQualifier(), kv.getTimestamp(),Tools.changeWaterMark(Bytes.toBytesBinary(json.get("binaryImage").toString()), json.get("studentName").toString()+json.get("imageDate").toString()) );
					immutableBytesWritable.set(Bytes.toBytes(key.toString()));
					context.write(immutableBytesWritable, put);
					Delete del = new Delete(kv.getRow());
					delete.add(del);
					context.getCounter(Counters.VALID).increment(1);
				}
			}
		} catch (Exception e) {
			context.getCounter(Counters.ERROR).increment(1);
		}
		
		
	}
	
	@Override
	protected void cleanup(Context context)throws IOException, InterruptedException {
		resultTable.delete(delete);
	}

}
