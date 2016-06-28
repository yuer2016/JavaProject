package com.dataAnalysis.test;

public class Stu {

	public static void main(String[] args) {
		double[] stu = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};//存储分析各个科目的有效和无效学时
		for (int i = 0; i < stu.length; i++) {
			stu[i]+=1;
		}
		for (double d : stu) {
			System.out.println(d);
		}
	}

}
