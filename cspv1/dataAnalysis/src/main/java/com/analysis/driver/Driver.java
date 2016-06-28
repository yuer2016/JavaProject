package com.analysis.driver;

import org.apache.hadoop.util.ProgramDriver;

import com.analysis.run.CoachDataRuning;
import com.analysis.run.LearningDataRunning;

public class Driver {

	public static void main(String[] args) throws Throwable {
		ProgramDriver pgd = new ProgramDriver();
		pgd.addClass("LearningData", LearningDataRunning.class, "LearningData class");
		pgd.addClass("CoachData", CoachDataRuning.class, "CoachDataRuning class");
		pgd.driver(args);

	}

}
