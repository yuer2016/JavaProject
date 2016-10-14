package com.oue.collect.utils;

/**
 * 采集数据分析算法类
 * @author yuer
 * */
public enum Algorithms {
	
	INSTANCE; 
	
	private static  final  int  CTLIMIT = 65536; //增量周期的最大值

	private Algorithms(){
	}
	/**
	 * 计算生产周期
	 * @param  r0  开的周期
	 * @param  r1  闭的周期
	 * @return 生产周期
	 * @author yuer
	 * */
	public String  getCycle(int r0 ,int r1){

		return String.valueOf(r0 + r1);
	}

	/**
	 * 计算机器产量
	 * @param int lastCt上次保存的产量
	 * @param int ct本次采集的增量产量值
	 * @return pdcount本次产量
	 * @author yuer
	 * */
	public int getCt(int lastCt,int ct){
		int pdcount =  0;
		if (ct >= lastCt){
			pdcount = ct - lastCt;
		}else if((CTLIMIT - lastCt) > 2 * ct){
			pdcount = ct;
		}else{
			pdcount = ct - lastCt + CTLIMIT;
		}

		return pdcount;
	}
}
