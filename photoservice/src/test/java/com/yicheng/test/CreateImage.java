package com.yicheng.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CreateImage {
	  
	
	public static void main(String[]  args) throws Exception{
		
		
		
		 File imgFile = new File("d:\\a.png");  
	     FileInputStream fis = new FileInputStream( imgFile );  
	     byte[] bytes = new byte[fis.available()];  //返回实际可读字节大小
	    
	     fis.read(bytes);    
	    
         fis.close(); 
         StringBuilder sb = new StringBuilder();
         for (byte b : bytes) {
        	 System.out.print(b);
			sb.append(b);
		}
         
         System.out.println();
        // System.out.println(sb.toString());
         //String image = sb.toString();
         //System.out.println(image);
	     //System.out.println(bytes.toString());
         byte[] imgByte = new byte[1024];
	     //char[] s =  sb.toString()
	     
	    for (byte b : imgByte) {
			System.out.print(b);
		}
	     
	     InputStream in = new ByteArrayInputStream(imgByte);
         File file=new File("d://b.png");//可以是任何图片格式.jpg,.png等
         FileOutputStream fos=new FileOutputStream(file);
            
         byte[] b = new byte[1024];
         int nRead = 0;
         while ((nRead = in.read(b)) != -1) {
             fos.write(b, 0, nRead);
         }
         fos.flush();
         fos.close();
         in.close();
		
		
		
		
	/*	 // 指定要读取文件的缓冲输入字节流  
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("D:/a.jpg"));  
        File file = new File("E:\\IMG0460A.jpg");  
        if (!file.exists()) {  
            //file.createNewFile();  
        	   // 指定要写入文件的缓冲输出字节流  
        	  BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));  
              byte[] bytesize = new byte[1024];// 用来存储每次读取到的字节数组  
              int n;// 每次读取到的字节数组的长度  
              while ((n = in.read(bytesize)) != -1) {  
                  out.write(bytesize, 0, n);// 写入到输出流  
              }  
              out.close();// 关闭流  
              in.close();  
        }  */
      
	/*	public static int saveToImgByStr(String imgStr,String imgPath,String imgName){
			try {
			    System.out.println("===imgStr.length()====>" + imgStr.length()
			            + "=====imgStr=====>" + imgStr);
			} catch (Exception e) {
			    e.printStackTrace();
			}
			        int stateInt = 1;
			        if(imgStr != null && imgStr.length() > 0){
			            try {
			                 
			                // 将字符串转换成二进制，用于显示图片 
			                // 将上面生成的图片格式字符串 imgStr，还原成图片显示 
			                byte[] imgByte = hex2byte( imgStr ); 
			     
			                InputStream in = new ByteArrayInputStream(imgByte);
			     
			                File file=new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
			                FileOutputStream fos=new FileOutputStream(file);
			                   
			                byte[] b = new byte[1024];
			                int nRead = 0;
			                while ((nRead = in.read(b)) != -1) {
			                    fos.write(b, 0, nRead);
			                }
			                fos.flush();
			                fos.close();
			                in.close();
			     
			            } catch (Exception e) {
			                stateInt = 0;
			                e.printStackTrace();
			            } finally {
			            }
			        }
			        return stateInt;
			    }
				
				 *//**
			     * 将二进制转换成图片保存
			     * @param imgStr 二进制流转换的字符串
			     * @param imgPath 图片的保存路径
			     * @param imgName 图片的名称
			     * @return 
			     *      1：保存正常
			     *      0：保存失败
			     *//*
			    public static int saveToImgByBytes(File imgFile,String imgPath,String imgName){
			 
			        int stateInt = 1;
			        if(imgFile.length() > 0){
			            try {
			                File file=new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
			                FileOutputStream fos=new FileOutputStream(file);
			                 
			                FileInputStream fis = new FileInputStream(imgFile);
			                   
			                byte[] b = new byte[1024];
			                int nRead = 0;
			                while ((nRead = fis.read(b)) != -1) {
			                    fos.write(b, 0, nRead);
			                }
			                fos.flush();
			                fos.close();
			                fis.close();
			     
			            } catch (Exception e) {
			                stateInt = 0;
			                e.printStackTrace();
			            } finally {
			            }
			        }
			        return stateInt;
			    }
*/
		
		
		

		/*String str = "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";

		BASE64Decoder decoder=new BASE64Decoder();

		  byte[] bytes=decoder.decodeBuffer(str);

		  File file=new File("D:/a.jpeg");

		  FileOutputStream fs=new FileOutputStream(file);

		  fs.write(bytes);

		  fs.flush();

		  fs.close();
*/
		 }
	
	
	} 


