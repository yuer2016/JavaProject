package com.watermark.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



public class WaterMarkRunning extends Configured implements Tool {

	public static void main(String[] args)throws Exception {
		int exitCode = ToolRunner.run(new WaterMarkRunning(), args);
		System.exit(exitCode);

	}

	@Override
	public int run(String[] arg0) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		
		Scan scan = new Scan();
		
		scan.setStartRow(Bytes.toBytes("jp0")).setStopRow(Bytes.toBytes("jp1")).addFamily("p".getBytes());
		
		Job job = new Job(conf, "Water Mark Running");
		
		job.setJarByClass(WaterMarkRunning.class);
		
		TableMapReduceUtil.initTableMapperJob("photo",scan, WaterMarkMap.class, ImmutableBytesWritable.class, Writable.class, job);
		
		job.setOutputFormatClass(TableOutputFormat.class);
		
		job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE,"photo");
		
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		
		job.setOutputValueClass(Writable.class);
		
		job.setNumReduceTasks(0);
		
		return job.waitForCompletion(true)?0:1;
	}

}
