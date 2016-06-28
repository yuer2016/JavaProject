package com.yicheng.dao.impl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.yicheng.dao.SmsDao;
import com.yicheng.entity.SmsModel;

public class SmsDaoImpl implements SmsDao {
	
	private Logger logger = Logger.getLogger(SmsDaoImpl.class);
	private Configuration conf = HBaseConfiguration.create();
	private HTable table = null;
	
	public SmsDaoImpl(){
		try {
			table = new HTable(this.conf,"sms");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public boolean putsms(String userid, String sms) {
		boolean result = true;
		StringBuilder uuid = new StringBuilder();
		uuid.append(userid+"_"+System.currentTimeMillis());
		try {
			Put put = new  Put(Bytes.toBytes(uuid.toString()));
			put.add(SmsModel.FAMILY, SmsModel.SMSCONTENT, Bytes.toBytes(sms));
			table.put(put);
		} catch (Exception e) {
			result = false;
			logger.error(e);
		}
		return result;
	}

}
