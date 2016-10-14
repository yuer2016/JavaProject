package com.brake.running;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.brake.mapreduce.BrakeMap;
import com.brake.mapreduce.BrakeReduce;

public class BrakeRunning extends Configured implements Tool  {
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new BrakeRunning(), args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		Scan scan = new Scan();
		scan.addFamily("v".getBytes());
		Job job = new Job(conf, "Brake count");
		job.setJarByClass(BrakeRunning.class);
		TableMapReduceUtil.initTableMapperJob("vehicleanalysis",scan, BrakeMap.class, Text.class, IntWritable.class, job);
		job.setReducerClass(BrakeReduce.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[0]));
	
		return job.waitForCompletion(true)?0:1;
	}

}
