package com.watermark.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class Tools {
	
	private static final Font font = new Font("黑体", Font.BOLD, 12);
	private static Logger logger = Logger.getLogger(Tools.class);
	
	/**
	 * 给图片加水印
	 * @param image 二进制图片原文件
	 * @param text 加水印文字
	 * @return byte 加完水印图片二进制数组
	 * @author hadoop
	 * */
	public static byte[] changeWaterMark(byte[] image,String text){
		try(InputStream input = new ByteArrayInputStream(image);
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();){
			BufferedImage src = ImageIO.read(input);
			int width = src.getWidth(null);  
	        int height = src.getHeight(null);
	        BufferedImage bufferedImage = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);
	        Graphics graphics = bufferedImage.createGraphics();
	        graphics.drawImage(src, 0, 0, width, height, null);
	        graphics.setColor(Color.RED);
	        graphics.setFont(font);
	        graphics.drawString(text, width/2, height-font.getSize()-9);
	        graphics.dispose();
	        ImageIO.write(bufferedImage, "JPG", outputStream);
	        return outputStream.toByteArray();	
		}catch(IOException e){
			logger.error(e);
		}
		return null;
	}

}
