package com.yicheng.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yicheng.dao.SettingDao;

@Service
public class SettingServiceImpl implements SettingService {
	
	@Autowired
	private SettingDao SettingDao;

	@Override
	public String getSttingTemplate(Map<String, Object> parms) {
		String result = null;
		result = SettingDao.getSettingTemplate(parms);
		return result;
	}

}
