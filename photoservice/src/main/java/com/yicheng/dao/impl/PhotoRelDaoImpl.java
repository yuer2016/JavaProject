package com.yicheng.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.yicheng.dao.PhotoRelDao;
import com.yicheng.entity.Image;
import com.yicheng.entity.Photo;
import com.yicheng.util.Tools;

public class PhotoRelDaoImpl implements PhotoRelDao {

	private Logger logger = Logger.getLogger(PhotoRelDaoImpl.class);
	private Configuration conf = HBaseConfiguration.create();
	private HTable tableRel = null;

	public PhotoRelDaoImpl(){
		try {
			tableRel = new HTable(this.conf,"photorel");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public List<Image> getImageRelation(String[] keys) {
		List<Image> images = new  ArrayList<Image>();
		List<byte[]> temp = new ArrayList<byte[]>();
		List<Get> gets = new ArrayList<Get>();
		if(Tools.checkNotEmpty(keys) && keys.length > 0){
			for (String key : keys) {
				Get get = new Get(Bytes.toBytes(key));
				get.addColumn(Photo.RELFAMILY, Photo.IMAGE);
				gets.add(get);
			}
			try {
				Result[] results = tableRel.get(gets);
				for (Result result : results) {
					if(!result.isEmpty()){
						byte[] val = null;
						if(result.containsColumn(Photo.RELFAMILY, Photo.IMAGE)){
							val = result.getValue(Photo.RELFAMILY, Photo.IMAGE);
							temp.add(val);
						}
					}
				}
				if(null != temp && temp.size() > 0){
					PhotoDaoImpl pd = new PhotoDaoImpl();
					images = pd.getByteImage(temp);
					
				}
			} catch (IOException e) {
				logger.error(e);
			}

		}
		return images;
	}

}
