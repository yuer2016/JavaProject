package com.yicheng.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yicheng.dao.PhotoDao;
import com.yicheng.dao.impl.PhotoDaoImpl;
import com.yicheng.entity.Image;

import junit.framework.TestCase;

public class TPhotoDaoImpl extends TestCase {
	
	private Logger logger = Logger.getLogger(TPhotoDaoImpl.class);

	public void testPhotoDaoImpl() {
		fail("Not yet implemented");
	}

	public void testPutImage() {
		PhotoDao photodao = new PhotoDaoImpl();
		List<Image> images = new ArrayList<Image>();
		Image image = new Image();
		image.setKey("cs001");
		image.setValue("01001010101010101010101010101000001010001");
		images.add(image);
		boolean b = photodao.putImage(images);
		assert(b);
	}

	public void testGetImage() {
		fail("Not yet implemented");
	}

	public void testGetByteImage() {
		PhotoDao photodao = new PhotoDaoImpl();
		List<Image> images = new ArrayList<Image>();
		String[] keys ={"cs001"};
		images = photodao.getByteImage(keys);
		if(images.size()>0){
			for (Image Image : images) {
				logger.info(Image.getKey() +":"+Image.getValue());
			}
		}
	}

}
