package com.yicheng.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yicheng.dao.PhotoDao;
import com.yicheng.dao.PhotoRelDao;
import com.yicheng.entity.Image;
import com.yicheng.service.PhotoService;
import com.yicheng.util.PropertiesUtil;
import com.yicheng.util.Tools;

@Service
public class PhotoServiceImpl implements PhotoService{

	@Autowired
	private PhotoDao photoDao;
	
	@Autowired
	private PhotoRelDao photoRelDao;

	/**
	 * 通过keys获取图片的物理路径
	 * @param keys
	 * @return
	 */
	public List<Image> getPhotoList(String[] keys){
		// 获取图片存入文件夹地址
		String dir = PropertiesUtil.getInputImageAddress();
		// 检查文件夹是否存在，不存在则创建
		Tools.createDir(dir);
		List<Image> imgList = new ArrayList<Image>();
		if(keys!=null && !"".equals(keys)){
			// 如果缓存目录中已存在图片，将keys数组中相应位置的值置为0
			for(int i=0;i<keys.length;i++){
				String filePath =dir+"/"+keys[i]+".jpg";
				File file = new File(filePath);
				if(file.exists() && file.isFile()){
					file.setLastModified((new Date()).getTime());
					Image img = new Image();
					img.setKey(keys[i]);
					img.setValue(PropertiesUtil.getOutputImageAddress() + "/" + keys[i] + ".jpg");
					imgList.add(img);
					keys[i]="0";
				}
			}
			// 从habse里获取
			List<Image> list = photoDao.getByteImage(keys);		
			// 将二进制写入指定目录
			if(list!=null && list.size()>0){
				for(Image img :list){
					if(img!=null && img.getKey()!=null && !"null".equals(img.getKey())){
						String filePath ="";
						filePath =dir+"/"+img.getKey()+".jpg";
						Tools.byteArrayToImage(img.getBinaryImage(), filePath);						
						img.setValue(PropertiesUtil.getOutputImageAddress() + "/" + img.getKey() + ".jpg");
						img.setBinaryImage(null);
						imgList.add(img);
					}
				}
			}
		}
		return imgList;
	}

	/**
	 * 通过keys获取图片的物理路径(测试先读图片关系表，获取key再去读取图片表)
	 * @param keys
	 * @return
	 */
	public List<Image> getPhotoListByRel(String[] keys) {
		// 获取图片存入文件夹地址
		String dir = PropertiesUtil.getInputImageAddress();
		// 检查文件夹是否存在，不存在则创建
		Tools.createDir(dir);
		List<Image> imgList = new ArrayList<Image>();
		if(keys!=null && !"".equals(keys)){
			// 如果缓存目录中已存在图片，将keys数组中相应位置的值置为0
			for(int i=0;i<keys.length;i++){
				String filePath =dir+"/"+keys[i]+".jpg";
				File file = new File(filePath);
				if(file.exists() && file.isFile()){
					file.setLastModified((new Date()).getTime());
					Image img = new Image();
					img.setKey(keys[i]);
					img.setValue(PropertiesUtil.getOutputImageAddress() + "/" + keys[i] + ".jpg");
					imgList.add(img);
					keys[i]="0";
				}
			}
			// 从habse里获取
			List<Image> list = photoRelDao.getImageRelation(keys);		
			// 将二进制写入指定目录
			if(list!=null && list.size()>0){
				for(Image img :list){
					if(img!=null && img.getKey()!=null && !"null".equals(img.getKey())){
						String filePath ="";
						filePath =dir+"/"+img.getKey()+".jpg";
						Tools.byteArrayToImage(img.getBinaryImage(), filePath);						
						img.setValue(PropertiesUtil.getOutputImageAddress() + "/" + img.getKey() + ".jpg");
						img.setBinaryImage(null);
						imgList.add(img);
					}
				}
			}
		}
		return imgList;
	}

	/**
	 * 通过keys获取图片的二进制
	 * @param keys
	 * @return
	 */
	public List<Image> getBytePhotoList(String[] keys){		
		return photoDao.getByteImage(keys);	
	}


	/**
	 * 将集合存入
	 * @param keys
	 * @return
	 */
	public void putPhotoList(List<Image> images) {
		photoDao.putImage(images);
	}

	/**
	 * 根据传来的图片id 扫描图片缓存目录地址
	 * @param keys
	 * @return
	 */
	public Map<String,List<Image>> scanBytePhoto(String[] keys){
		return photoDao.scanByteImage(keys);
	}

//	public List<Image> scanImages(List<StudentInfo> studentInfos)
//			throws Exception {
//		List<Image> images = new ArrayList<Image>();
//		studentInfos = photoDao.scanImages(studentInfos);
////		for (StudentInfo studentInfo : studentInfos) {
////			images.addAll(getPhotoList(studentInfo.getKeys().split(",")));
////		}
//		return images;
//	}

}
