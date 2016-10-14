package com.yicheng.dao;

import java.util.List;

import com.yicheng.entity.JPImageEntity;
import com.yicheng.entity.StudentInfo;

public interface JPPhotoDao {
	/**
	 * 存储图片
	 * @param List<JPImageEntity> images
	 * @param string appId
	 * */
	public boolean putImage(List<JPImageEntity> images,String appId);
	
	/**
	 * 获取驾培学习培训照片
	 * @param studentInfos
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<StudentInfo> scanImages(List<StudentInfo> studentInfos,String appId)throws Exception;

}
