package com.yicheng.service;

import java.util.List;
import java.util.Map;

import com.yicheng.entity.Image;

public interface PhotoService {
    /**
     * 通过keys获取图片的物理路径
     * @param keys
     * @return
     */
	List<Image> getPhotoList(String[] keys);
	
	/**
     * 通过keys获取图片的物理路径(测试先读图片关系表，获取key再去读取图片表)
     * @param keys
     * @return
     */
	List<Image> getPhotoListByRel(String[] keys);
	
	/**
	 * 通过keys获取图片的二进制
	 * @param keys
	 * @return
	 */
	List<Image> getBytePhotoList(String[] keys);

	 /**
     * 将集合存入
     * @param keys
     * @return
     */
	void putPhotoList(List<Image> images);
		
	
	/**
	 * 根据传来的图片id 扫描图片二进制
	 * @param keys
	 * @return
	 */
	Map<String,List<Image>> scanBytePhoto(String[] keys);
	
	/**
	 * 
	 * */
//	public List<Image> scanImages(List<StudentInfo> studentInfos)throws Exception;
	
}
