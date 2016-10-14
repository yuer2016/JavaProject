package com.yicheng.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.yicheng.dao.JPPhotoDao;
import com.yicheng.entity.Image;
import com.yicheng.entity.JPImageEntity;
import com.yicheng.entity.Photo;
import com.yicheng.entity.StudentInfo;
import com.yicheng.util.DateTimeUtil;
import com.yicheng.util.Tools;

/**
 * Hbase操作类
 *
 */
public class JPPhotoDaoImpl implements JPPhotoDao {

	private Logger logger = Logger.getLogger(JPPhotoDaoImpl.class);
	private Configuration conf = HBaseConfiguration.create();
	private HTable table = null;
	public JPPhotoDaoImpl(){
		try {
			table = new HTable(this.conf,"photo");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 将驾培图片集合存入Hbase
	 */
	public synchronized boolean putImage(List<JPImageEntity> images,String appId) {
		boolean result = true;
		List<Put> puts = new ArrayList<Put>();
		if(Tools.checkNotEmpty(images) && images.size()>0){
			for (JPImageEntity image : images) {
				Put put = new  Put(Bytes.toBytes(generateRowKey(image,appId)));
				put.add(Photo.FAMILY, Photo.IMAGE, DateTimeUtil.getLongTime(image.getImageDate()), Bytes.toBytes(image.toJSON()));
				puts.add(put);
			}
			try {
				table.put(puts);
				logger.info("size:" + puts.size());
				logger.info("Success!");
			} catch (IOException e) {
				result = false;
				logger.error(e);
			}

		}else{
			result = false;
			logger.error("null");
		}
		puts = null;
		return result;
	}
	
	/**
	 * 获取驾培学习培训照片
	 */
	public synchronized List<StudentInfo> scanImages(List<StudentInfo> studentInfos,String appId)throws Exception {
		List<StudentInfo> stuinfos = new ArrayList<StudentInfo>();
		Scan scan = new Scan();
		for (StudentInfo studentInfo : studentInfos) {
			String scanKey = generateScanKey(studentInfo,appId);
			scan.addFamily(Photo.FAMILY).setStartRow(Bytes.toBytes(scanKey)).setStopRow(Bytes.toBytes(scanKey + "_a")).setTimeRange(DateTimeUtil.getLongTime(studentInfo.getBegindate()), DateTimeUtil.getLongTime(studentInfo.getEnddate()) + 1);
			ResultScanner results = table.getScanner(scan);
			StringBuilder stringBuilder = new StringBuilder();
			List<Image> imageList = new ArrayList<Image>();
			for (Result result : results) {
				
				Image image = new Image();
//				JSONParser parser = new JSONParser();
				
//				JSONObject json = (JSONObject)parser.parse(Bytes.toString(result.getValue(Photo.FAMILY, Photo.IMAGE)));
				
				image.setBinaryImage(result.getValue(Photo.FAMILY, Photo.IMAGE));
				
				image.setKey(Bytes.toString(result.getRow()));
				
				imageList.add(image);
				
			}
			studentInfo.setImages(imageList);
			logger.info("key:"+stringBuilder.toString());
			stuinfos.add(studentInfo);
		}

		return stuinfos;
	}

	/*
	 * 生成rowkey
	 */
	private String generateRowKey(JPImageEntity image,String appId){
		//使用年份后两位+月日,时分秒,来唯一标识rowkey
		String datatime = image.getImageDate();
		datatime = parseDateTime(datatime);
		String rowKey = appId + "0" + image.getDeviceNo() + image.getStudentCardID() + datatime;
		logger.debug("rowKey:" + rowKey);
		return rowKey;
	}
	
	/*
	 * 生成用于扫描的key
	 */
	private String generateScanKey(StudentInfo student,String appId){
		return appId + "1" + student.getDeviceNo() + student.getStudentCardID();
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss类型的日期转化为yyMMdd HHmmss
	 */
	private String parseDateTime(String date){
		Date temp = DateTimeUtil.stringToDate(date);
		String datetime = DateTimeUtil.dateToString("yyMMddHHmmss",temp);
		return datetime;
	}
	
	public static void main(String[] args) {
		System.out.println(DateTimeUtil.getDefectLongTime("2013-06-16 15:50:44"));
	}

}
