package com.cjm.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

import com.cjm.model.Picture;

/**
 * @time 2016年4月14日
 * @author CHEN
 * @param 
 * @about 一个图片下载的具体实现
 */

public class PictureDownloadMachine {
	/**
	 * @param objUri:图片的地址 
	 * 		  picture：提供图片存放的地址
	 */
	public static void downloadImage(String objUri,Picture picture) throws IOException {
		//获得图片流
		byte[] btImg=getImageFromNetByUrl(objUri);
		if(null!=btImg&&btImg.length>0) {
			//图片流存在写入硬盘
			String pathStr=Paths.get(picture.getFileUri(), objUri.substring(objUri.lastIndexOf("/")+1)).toString();//拼接路径
			writeImageToDisk(btImg,pathStr);//写入硬盘
		}
	}
	
	private static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url=new URL(strUrl);//将链接转成URL
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			//伪装成浏览器
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2700.0 Safari/537.36");
			//伪装主机
			conn.setRequestProperty("Host" ,"image.baidu.com");
			//设置接收方式
			conn.setRequestProperty("Accept" ,"text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
			//设置字符
			conn.setRequestProperty("Accept-Encoding" ,"gzip, deflate, sdch");
			//设置连接状态
			conn.setRequestProperty("Connection" ,"keep-alive");
			//伪装请求方
			conn.setRequestProperty("Referer" ,"http://image.baidu.com");
			conn.setRequestProperty("X-Requested-With" ,"XMLHttpRequest");
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5*1000);
			//获得返回流
			InputStream inStream =conn.getInputStream();
			byte[]btImg=readInputStream(inStream);
			return  btImg;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @about 捕获内容缓冲区的数据，转换成字节数组
	 * @param inputStream ：输入流
	 * 		  byteArrayOutputStream :输出流（存储容器）
	 */
	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		byte[]buffer=new byte[1024];
		int len=0;
		while ((len=inputStream.read(buffer))!=-1) {
			byteArrayOutputStream.write(buffer,0,len);
		}
		inputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
	private static void writeImageToDisk(byte[] btImg, String fileUri) throws IOException {
		File file=new File(fileUri);//新建一个文件空壳
		FileOutputStream fileOutputStream=new FileOutputStream(file);//文件输出流
		fileOutputStream.write(btImg);//把图片写到了文件空壳中
		fileOutputStream.flush();//把缓存的内容都写完
		fileOutputStream.close();
	}

	
	
	
}
