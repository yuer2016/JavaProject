package com.analysis.run;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.analysis.map.CoachDataMap;
import com.analysis.reduce.CoachDataReduce;
import com.analysis.utils.TableInfo;


public class CoachDataRuning extends Configured implements Tool{

	public static void main(String[] args) throws Throwable {
		int exitCode = ToolRunner.run(new CoachDataRuning(), args);
		System.exit(exitCode);

	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		Path outputPath = new Path("/CoachData");
		outputPath.getFileSystem(getConf()).delete(outputPath, true);
		Scan scan = new Scan();
		scan.addFamily(TableInfo.STN_FAMILY);
		Job job = new Job(conf, "CoachData count");
		job.setJarByClass(LearningDataRunning.class);
		TableMapReduceUtil.initTableMapperJob(TableInfo.STUDENT_TABLE_NAME,scan, CoachDataMap.class, Text.class, Text.class, job);
		job.setReducerClass(CoachDataReduce.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, outputPath);

		return job.waitForCompletion(true)?0:1;
	}

}
