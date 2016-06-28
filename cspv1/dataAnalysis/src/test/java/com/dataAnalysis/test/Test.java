package com.dataAnalysis.test;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;

public class Test {
	@org.junit.Test
	public void spilt() {
		System.out.println(StringUtils.substring("000001610115199303133818",0, 6));
		System.out.println(StringUtils.substring("000001610115199303133818",6));
	}
	@org.junit.Test
	public void cmd(){
		String[] args = {"-p","/root"};
		Options options = new Options();
		Option  option = new Option("h", false, " Print help");
		option.setRequired(false);
		options.addOption(option);
		option = new Option("p","path" ,true, " reduce output hdfs path");
		option.setRequired(false);
		options.addOption(option);
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.setWidth(110);
		CommandLine commandLine = null;
		CommandLineParser commandLineParser = new PosixParser();
		try {
			commandLine = commandLineParser.parse(options, args);
			if(commandLine.hasOption("h")){
				helpFormatter.printHelp("Test", options);
			}else{
				System.out.println(commandLine.getOptionValue("p"));
			}
		} catch (Exception e) {

		}


	}

}
//val name = pairs.reduceByKey((x,y) => x+y)
//val pairs = lines.map(x => (x.split(",")(7),y))
//val lines = sc.textFile("/root/text")
