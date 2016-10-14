package com.yicheng.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.yicheng.dao.PhotoDao;
import com.yicheng.entity.Image;
import com.yicheng.entity.Photo;
import com.yicheng.util.DateTimeUtil;
import com.yicheng.util.Tools;

public class PhotoDaoImpl implements PhotoDao {

	private Logger logger = Logger.getLogger(PhotoDaoImpl.class);
	private Configuration conf = HBaseConfiguration.create();
	private HTable table = null;
	public PhotoDaoImpl(){
		try {
			table = new HTable(this.conf,"photo");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public boolean putImage(List<Image> images) {
		boolean result = true;
		List<Put> puts = new ArrayList<Put>();
		if(Tools.checkNotEmpty(images)){
			if(images.size()>0){
				for (Image image : images) {
					Put put = new  Put(Bytes.toBytes(image.getKey()));
					if(Tools.checkNotEmpty(image.getImageDate())){
						put.add(Photo.FAMILY, Photo.IMAGE, DateTimeUtil.getLongTime(image.getImageDate()), image.getBinaryImage());	
					}else{
						put.add(Photo.FAMILY, Photo.IMAGE, image.getBinaryImage());
					}
					puts.add(put);
				}
				try {   
					table.put(puts);
				} catch (IOException e) {
					result = false;
					logger.error(e);
				}
			}else{
				result = false;
				logger.error("传入的图片值为Empty");
			}

		}else{
			result = false;
			logger.error("传入的图片值为null");
		}
		return result;
	}
	public List<Image> getImage(String[] keys) {
		if(Tools.checkNotEmpty(keys)){

		}
		return null;
	}
	public List<Image> getByteImage(String[] keys) {
		List<Image> images = new  ArrayList<Image>();
		List<Get> gets = new ArrayList<Get>();
		if(Tools.checkNotEmpty(keys)){
			if(keys.length>0){
				for (String key : keys) {
					if(!"0".equals(key)){
						Get get = new Get(Bytes.toBytes(key));
						get.addColumn(Photo.FAMILY, Photo.IMAGE);
						gets.add(get);
					}
				}
				try {
					Result[] results = table.get(gets);
					for (Result result : results) {
						if(!result.isEmpty()){
							Image image = new Image();
							image.setKey(Bytes.toString(result.getRow()));
							byte[] val = null;
							if(result.containsColumn(Photo.FAMILY, Photo.IMAGE)){
								val = result.getValue(Photo.FAMILY, Photo.IMAGE);
								image.setBinaryImage(val);
							}
							images.add(image);
						}
					}
				} catch (IOException e) {
					logger.error(e);
				}
			}

		}
		return images;
	}

	public List<Image> getByteImage(List<byte[]> keys) {
		List<Image> images = new  ArrayList<Image>();
		List<Get> gets = new ArrayList<Get>();
		if(Tools.checkNotEmpty(keys) && keys.size() > 0){
			for (byte[] key : keys) {
				Get get = new Get(key);
				get.addColumn(Photo.FAMILY, Photo.IMAGE);
				gets.add(get);
			}
			try {
				Result[] results = table.get(gets);
				for (Result result : results) {
					if(!result.isEmpty()){
						Image image = new Image();
						image.setKey(Bytes.toString(result.getRow()));
						byte[] val = null;
						if(result.containsColumn(Photo.FAMILY, Photo.IMAGE)){
							val = result.getValue(Photo.FAMILY, Photo.IMAGE);
							image.setBinaryImage(val);
						}
						images.add(image);
					}
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}

		return images;
	}

	public Map<String,List<Image>> scanByteImage(String[] keys)  {
		Map<String,List<Image>> images = new  HashMap<String, List<Image>>();
		List<Image> imagelist = new ArrayList<Image>();
		String mapkey = "";
		Scan scan = new Scan();
		scan.addFamily(Photo.FAMILY);
		if(Tools.checkNotEmpty(keys)){
			if(keys.length>0){
				for (String key : keys) {
					scan.setStartRow(Bytes.toBytes(key));
					scan.setStopRow(Bytes.toBytes(key+1));
					try {
						ResultScanner results = table.getScanner(scan);
						for (Result result : results) {
							if(!result.isEmpty()){
								Image image = new Image();
								mapkey = Bytes.toString(result.getRow());
								String[] temp = mapkey.split("_");
								if(temp.length > 1){
									image.setKey(temp[1]);
								}else{
									image.setKey(temp[0]);
								}
//								image.setKey(mapkey.split("-")[1]);
								byte[] val = null;
								if(result.containsColumn(Photo.FAMILY, Photo.IMAGE)){
									val = result.getValue(Photo.FAMILY, Photo.IMAGE);
									image.setBinaryImage(val);
								}
								imagelist.add(image);	
							}
						}
						images.put(mapkey.split("-")[0], imagelist);
					} catch (IOException e) {
						logger.debug(e);
					}
				}

			}

		}
		return images;
	}

//	public List<StudentInfo> scanImages(List<StudentInfo> studentInfos)throws Exception {
//		List<StudentInfo> stuinfos = new ArrayList<StudentInfo>();
//		Scan scan = new Scan();
//		for (StudentInfo studentInfo : studentInfos) {
//			scan.addFamily(Photo.FAMILY).setStartRow(Bytes.toBytes(generateScanKey(studentInfo,appId))).setStopRow(Bytes.toBytes(generateScanKey(studentInfo,appId) + "_a")).setTimeRange(DateTimeUtil.getLongTime(studentInfo.getBegindate()), DateTimeUtil.getLongTime(studentInfo.getEnddate()) + 1);
//			ResultScanner results = table.getScanner(scan);
//			int i=0;
//			StringBuilder stringBuilder = new StringBuilder();
//			for (Result result : results) {
//				if(i==0){
//					stringBuilder.append(Bytes.toString(result.getRow()));
//				}else{
//					stringBuilder.append(","+Bytes.toString(result.getRow()));
//				}
//				i++;
//			}
//			logger.info("查询到学时照片key值："+stringBuilder.toString());
//			studentInfo.setKeys(stringBuilder.toString());
//			stuinfos.add(studentInfo);
//		}
//
//
//		return stuinfos;
//	}




}
