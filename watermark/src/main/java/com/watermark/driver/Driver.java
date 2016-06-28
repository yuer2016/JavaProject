package com.watermark.driver;

import org.apache.hadoop.util.ProgramDriver;

import com.watermark.mapreduce.WaterMarkRunning;


public class Driver {
	
	public static void main(String[] args) throws Throwable{
		ProgramDriver pgd = new ProgramDriver();
	    pgd.addClass("water", WaterMarkRunning.class, "Water Mark Running class");
	    pgd.driver(args);
	}
}
