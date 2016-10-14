package com.locus.running;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.locus.mapreduce.LocusMap;
import com.locus.mapreduce.LocusReduce;

public class LocusRunning extends Configured implements Tool  {
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new LocusRunning(), args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		Scan scan = new Scan();
		scan.addFamily("v".getBytes());
		Job job = new Job(conf, "Locus MaxAvgSpeed");
		job.setJarByClass(LocusRunning.class);
		TableMapReduceUtil.initTableMapperJob("vehicleanalysis",scan, LocusMap.class, Text.class, FloatWritable.class, job);
		job.setReducerClass(LocusReduce.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[0]));
	
		return job.waitForCompletion(true)?0:1;
	}

}
