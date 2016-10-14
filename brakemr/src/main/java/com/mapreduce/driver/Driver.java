package com.mapreduce.driver;

import org.apache.hadoop.util.ProgramDriver;

import com.brake.running.BrakeRunning;
import com.locus.running.LocusRunning;
import com.oil.running.OilRunning;

public class Driver {
	public static void main(String[] args)throws Throwable {
		ProgramDriver pgd = new ProgramDriver();
	    pgd.addClass("BrakeRunning", BrakeRunning.class, "Brake Running class");
	    pgd.addClass("OilRunning", OilRunning.class, "Oil Consume class");
	    pgd.addClass("LocusRunning", LocusRunning.class, "Locus Speed Running class");
	    pgd.driver(args);
	}
}
