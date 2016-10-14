package com.yicheng.dao;

import java.util.List;

import com.yicheng.entity.Image;

public interface PhotoRelDao {

	List<Image> getImageRelation(String[] keys);
	
}
