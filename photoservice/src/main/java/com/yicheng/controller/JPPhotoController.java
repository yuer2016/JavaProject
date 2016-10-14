package com.yicheng.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yicheng.entity.JPImageEntity;
import com.yicheng.entity.Response;
import com.yicheng.entity.StudentInfo;
import com.yicheng.service.JPPhotoService;
import com.yicheng.util.Tools;

@RestController
@RequestMapping(value = "/jp")
public class JPPhotoController extends CommonController{

	@Autowired
	private JPPhotoService jPPhotoService;
	
	private Logger logger = Logger.getLogger(JPPhotoController.class);
	
	/**
	 * 驾培图片服务
     * 将图片集合(二进制)存入Hbase
     * @param imgList 格式[{"binaryImage":"","imageDate":"","studentName":"","deviceNo":"","studentCardID":""},...]
     * binaryImage(byte[]):图片二进制,imageDate:时间,studentName:学员名字,deviceNo:车机号,studentCardID:学员卡号
     * 时间格式:yyyy-MM-dd HH:mm:ss
     * @return true false
     */
	@RequestMapping(value = "/putImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String putPhotoList(HttpServletRequest request,@RequestBody String imgList) {
		Response respon = new Response();
		parseRequest(request, respon);
		boolean flag = true;
		try {
			List<JPImageEntity> images = Tools.getJPParams(imgList);
			jPPhotoService.putPhotoList(images,respon.getAppId());
		} catch (Exception e) {
			flag = false;
			errorHandler(e,respon,logger);
		}
		respon.setResult(flag);
		return respon.toJSON();
	}
	
	/**
	 * 将单个图片(二进制)存入Hbase
	 * @param imgList 格式{"binaryImage":"","imageDate":"","studentName":"","deviceNo":"","studentCardID":""}
     * binaryImage(byte[]):图片二进制,imageDate:时间,studentName:学员名字,deviceNo:车机号,studentCardID:学员卡号
     * 时间格式:yyyy-MM-dd HH:mm:ss
     * @return true false
	 */
	@RequestMapping(value = "/putOneImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String putPhotoOne(HttpServletRequest request,@RequestBody String img) {
		Response respon = new Response();
		parseRequest(request, respon);
		boolean flag = true;
		try {
			JPImageEntity image = Tools.getJPParam(img);
			jPPhotoService.putPhotoList(Arrays.asList(image),respon.getAppId());
		} catch (Exception e) {
			flag = false;
			errorHandler(e,respon,logger);
		}
		respon.setResult(flag);
		return respon.toJSON();
	}
	
	/**
	 * 获取驾培学习培训照片
	 * @param request
	 * @param studentInfos 格式[{"begindate":"","enddate":"","deviceNo":"","studentCardID":""},...]
	 * begindate:开始时间,enddate:结束时间,deviceNo:车机号,studentCardID:学识号
	 * @return
	 */
	@RequestMapping(value = "/getstuImgs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String getstuphotos(HttpServletRequest request,@RequestBody String studentInfos){
		Response respon = new Response();
		parseRequest(request, respon);
		try {
			List<StudentInfo> studentList = Tools.getJPList(studentInfos);
			List<StudentInfo> sList = new ArrayList<StudentInfo>();
			sList = jPPhotoService.scanImages(studentList,respon.getAppId());
			if(sList!=null && sList.size()>0){
				respon.setResult(sList);
			}else{
				respon.setMessage("没有可查询的值！");
			}
		} catch (Exception e) {
			errorHandler(e,respon,logger);
		}
		return respon.toJSON();
	}

}
