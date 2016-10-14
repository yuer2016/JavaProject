package com.swagger.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.entity.UserInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "user-api", description = "有关于用户的CURD操作", position = 5)
@RestController
@RequestMapping(value = "/api")
public class SwaggerController {
	
	@ApiOperation(value = "注册", notes = "注册用户", position = 3)
    @ResponseBody
    @RequestMapping(value = { "/regist" }, method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	public UserInfo userApi(@ApiParam(value = "填写Pk",required = true)@RequestBody String pwd){
		UserInfo userInfo = new UserInfo();
		userInfo.setName("元华");
		userInfo.setPwd(pwd);
		return userInfo;
	}
}
