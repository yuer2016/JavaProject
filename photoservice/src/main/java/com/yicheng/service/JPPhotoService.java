package com.yicheng.service;

import java.util.List;

import com.yicheng.entity.JPImageEntity;
import com.yicheng.entity.StudentInfo;

public interface JPPhotoService {
	
	/**
     * 将驾培图片集合存入Hbase
     * @param 
     * @return
     */
	void putPhotoList(List<JPImageEntity> images,String appId);
	
	/**
	 * 获取驾培学习培训照片
	 * @param studentInfos
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> scanImages(List<StudentInfo> studentInfos,String appId)throws Exception;

}
