package com.oil.running;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oil.mapreduce.OilMap;
import com.oil.mapreduce.OilReduce;

public class OilRunning extends Configured implements Tool  {
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new OilRunning(), args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		Scan scan = new Scan();
		scan.addFamily("v".getBytes());
		Job job = new Job(conf, "Oil Consume");
		job.setJarByClass(OilRunning.class);
		TableMapReduceUtil.initTableMapperJob("vehicleanalysis",scan, OilMap.class, Text.class, FloatWritable.class, job);
		job.setReducerClass(OilReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[0]));
	
		return job.waitForCompletion(true)?0:1;
	}

}
