package com.yicheng.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

public class Test {

	
	public static void main(String[] args) throws Exception {
		
		Logger logger = Logger.getLogger(Test.class);
		
		Configuration conf = HBaseConfiguration.create();

		@SuppressWarnings("resource")
		HTable table = new HTable(conf, "photo");
		
		 File imgFile = new File("d:\\a.jpg");  
	     FileInputStream fis = new FileInputStream( imgFile );  
	     byte[] bytes = new byte[fis.available()];  //返回实际可读字节大小
	     fis.read(bytes);    
         fis.close(); 

		Put put = new Put(Bytes.toBytes("20150511_388"));

		put.add(Bytes.toBytes("p"),Bytes.toBytes("m"),bytes);

		table.put(put); 
		
		Get get = new Get(Bytes.toBytes("20150511_388"));
		
		get.addColumn(Bytes.toBytes("p"),Bytes.toBytes("m"));
		
		Result result = table.get(get);
		
		byte[] val = result.getValue(Bytes.toBytes("p"),Bytes.toBytes("m"));
		
		logger.info(val);
		
//		for (byte b : val) {
//			System.out.print(b);
//		}
		
		 InputStream in = new ByteArrayInputStream(val);
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

		/*InputStream in = new ByteArrayInputStream(val);
        File file2=new File("d://b.jpg");//可以是任何图片格式.jpg,.png等
        FileOutputStream fos=new FileOutputStream(file);
           
        byte[] b = new byte[1024];
        int nRead = 0;
        while ((nRead = in.read(b)) != -1) {
            fos.write(b, 0, nRead);
        }
        fos.flush();
        fos.close();
        in.close();*/
		
	}

}