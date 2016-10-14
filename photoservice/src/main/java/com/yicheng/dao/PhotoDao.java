package com.yicheng.dao;

import java.util.List;
import java.util.Map;

import com.yicheng.entity.Image;

public interface PhotoDao {
	/**
	 * 存储图片
	 * @param Map<String, Byte[]> images
	 * @param string 传过来的图片id需唯一
	 * @param  Byte[] 图片二进制流
	 * */
	public boolean putImage( List<Image> images);
	/**
	 * 根据传来的图片id 返回图片缓存目录地址
	 * @param String[] keys 图片id
	 * */
	public List<Image> getImage(String[] keys);
	
	/**
	 * 根据传来的图片id 返回图片二进制流
	 * @param String[] keys 图片id
	 * */
	public List<Image> getByteImage(String[] keys);
	
	/**
	 * 根据传来的图片id 返回图片二进制流
	 * @param List<byte[]> keys 图片id的二进制
	 * @return
	 */
	public List<Image> getByteImage(List<byte[]> keys);
	
	/**
	 * 根据传来的图片id 扫描图片缓存目录地址
	 * @param keys
	 * @return
	 */
	public Map<String,List<Image>> scanByteImage(String[] keys);
	
//	public List<StudentInfo> scanImages(List<StudentInfo> studentInfos)throws Exception;

}
