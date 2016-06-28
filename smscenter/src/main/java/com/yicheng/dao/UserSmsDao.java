package com.yicheng.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yicheng.entity.UserSmsBean;

public interface UserSmsDao {
	
	UserSmsBean  getAll(@Param("smsId") String smsId) throws DataAccessException;
	
	int updateSmsSurplus(Map<String, Object> parms);

}
