package com.yicheng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yicheng.dao.JPPhotoDao;
import com.yicheng.entity.JPImageEntity;
import com.yicheng.entity.StudentInfo;
import com.yicheng.service.JPPhotoService;

@Service
public class JPPhotoServiceImpl implements JPPhotoService{

	@Autowired
	private JPPhotoDao jPPhotoDao;
	
	/**
	 * 将驾培图片集合存入Hbase
	 * @param keys
	 * @return
	 */
	public void putPhotoList(List<JPImageEntity> images,String appId) {
		jPPhotoDao.putImage(images,appId);
	}
	
	/**
	 * 获取驾培学习培训照片
	 */
	public List<StudentInfo> scanImages(List<StudentInfo> studentInfos,String appId)
			throws Exception {
		List<StudentInfo> sList = jPPhotoDao.scanImages(studentInfos,appId);
//		for (StudentInfo studentInfo : sList) {
//			for (byte[] imageBytes : studentInfo.getImages()) {
//				
//				Tools.byteArrayToImage(imageBytes,"F:\\2.jpg");
//			}
//		}
		return sList;
	}

}