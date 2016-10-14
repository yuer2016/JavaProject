package com.yicheng.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yicheng.entity.Image;
import com.yicheng.entity.Response;
import com.yicheng.service.PhotoService;
import com.yicheng.util.Tools;

@RestController
@RequestMapping(value = "/photo")
public class PhotoController extends CommonController{

	@Autowired
	private PhotoService photoService;
	
	private Logger logger = Logger.getLogger(PhotoController.class);
	
	
	/**
     * 将图片集合(二进制)存入Hbase
     * @param imgList 格式[{"key":"","binaryImage":""},...]
     * @return
     */
//	@RequestMapping(value = "/putImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String putPhotoList(HttpServletRequest request,@RequestBody String imgList) {
//		Response respon = new Response();
//		parseRequest(request, respon);
//		boolean flag = true;
//		try {
//			List<Image> images = Tools.getListParams(imgList);
//			photoService.putPhotoList(images);
//		} catch (Exception e) {
//			flag = false;
//			errorHandler(e,respon,logger);
//		}
//		respon.setResult(flag);
//		return respon.toJSON();
//	}
	
	/**
	 * 将单个图片(二进制)存入Hbase
	 * @param imgList 格式{"key":"","binaryImage":""}
	 * @return
	 */
//	@RequestMapping(value = "/putOneImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String putPhotoOne(HttpServletRequest request,@RequestBody String imgList) {
//		Response respon = new Response();
//		List<Image> images = new ArrayList<Image>();
//		parseRequest(request, respon);
//		boolean flag = true;
//		try {
//			Image image = Tools.getListParamOne(imgList);
//			images.add(image);
//			photoService.putPhotoList(images);
//			images = null;
//		} catch (Exception e) {
//			flag = false;
//			errorHandler(e,respon,logger);
//		}
//		respon.setResult(flag);
//		return respon.toJSON();
//	}
	
	/**
     * 根据多个key，获取图片URL
     * @param imgList 格式["key1","key2",...]
     * @return
     */
//	@RequestMapping(value = "/getImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String getPhotoLists(HttpServletRequest request,@RequestBody String imgList) {
//		Response respon = new Response();
//		parseRequest(request, respon);
//		try {
//			String[] keys = Tools.getStringsParam(imgList);
//			List<Image> list = photoService.getPhotoList(keys);
//			respon.setResult(list);
//			
//		} catch (Exception e) {
//			errorHandler(e,respon,logger);
//		}
//		return respon.toJSON();
//	}
	
	/**
     * 根据多个key，获取图片URL(测试先读图片关系，获取key再去读取图片表)
     * @param imgList 格式["key1","key2",...]
     * @return
     */
//	@RequestMapping(value = "/getImgsRel", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String getPhotoRelLists(HttpServletRequest request,@RequestBody String imgList) {
//		Response respon = new Response();
//		parseRequest(request, respon);
//		try {
//			String[] keys = Tools.getStringsParam(imgList);
//			List<Image> list = photoService.getPhotoListByRel(keys);
//			respon.setResult(list);
//			
//		} catch (Exception e) {
//			errorHandler(e,respon,logger);
//		}
//		return respon.toJSON();
//	}
	
	/**
	 * 根据查询条件获取多个图片信息
	 * */
//	@RequestMapping(value = "/getstuImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String getstuphotos(HttpServletRequest request,@RequestBody String studentInfos){
//		Response respon = new Response();
//		parseRequest(request, respon);
//		try {
//			List<StudentInfo> studentList = Tools.getlist(studentInfos);
//			List<Image> imageList = new ArrayList<Image>();
//			imageList = photoService.scanImages(studentList);
//			if(imageList!=null && imageList.size()>0){
//				respon.setResult(imageList);
//			}else{
//				respon.setMessage("没有可查询的值！");
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		return respon.toJSON();
//		
//	}
	
	
	
	/**
	 * 根据多个key，获取图片二进制
	 * @param imgList 格式["key1","key2",...]
	 * @return
	 */
//	@RequestMapping(value = "/getByteImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String getBytePhotoLists(HttpServletRequest request,@RequestBody String imgList) {
//		Response respon = new Response();
//		parseRequest(request, respon);
//		try {
//			String[] keys = Tools.getStringsParam(imgList);
//			List<Image> list = photoService.getBytePhotoList(keys);
//			respon.setResult(list);
//			
//		} catch (Exception e) {
//			errorHandler(e,respon,logger);
//		}
//		return respon.toJSON();
//	}
	
	
	/**
	 * 根据多个key，获取图片二进制
	 * @param imgList 格式["key1","key2",...]
	 * @return
	 */
//	@RequestMapping(value = "/scanByteImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String scanBytePhoto(HttpServletRequest request,@RequestBody String imgList) {
//		Response respon = new Response();
//		parseRequest(request, respon);
//		try {
//			String[] keys = Tools.getStringsParam(imgList);
//			Map<String,List<Image>> list = photoService.scanBytePhoto(keys);
//			respon.setResult(list);
//		} catch (Exception e) {
//			errorHandler(e,respon,logger);
//		}
//		return respon.toJSON();
//	}
	
}
