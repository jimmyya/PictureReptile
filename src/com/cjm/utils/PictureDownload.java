package com.cjm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.cjm.model.MyHttpClient;
import com.cjm.model.Picture;


/**
 * @time 2016年4月14日
 * @author CHEN
 * @param 
 * picture：要下载对象的关键词、文件路径、页数
 * @about 一个图片下载的系统
 */
public class PictureDownload {
	//下载图片的功能
	public static void downloadPicture(Picture picture) throws ClientProtocolException, IOException {
		//获得下载的页数
		int pageCount=(int)Double.parseDouble(picture.getPageNum());
		//URLEncoder.encode(picture.getKeyWord())：将关键字转化成url格式
		for(int i=0;i<=pageCount;i++) {
			//爬取的图片来自百度
			String uri="http://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj"
            + "&ct=201326592&is=&fp=result&queryWord=" + URLEncoder.encode(picture.getKeyWord())
            + "&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0"
            + "&word=" + URLEncoder.encode(picture.getKeyWord())
            + "&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&pn=" + 30 * i 
            + "&rn=30&gsm=7000096&1460517144785=";
			//获得返回的json字符串
			String jsonStr= MyHttpClient.getJsonDate(uri);
		
			//将jsonStr字符串格式化之后写入文件
			String rootPathStr=Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			//File file=new File(rootPathStr+"json.txt");
			//if(!file.exists()) {
			//	file.createNewFile();//新建文件
			//}
			//System.out.println("记录文件的地址"+file.getAbsolutePath());//最好是记在日志中
			//开始记录入文件
			/*
			 *装饰者
			 *FileWriter 被装饰者
			 *BufferWriter 装饰者 
			 *就是加了一个缓存，这样等到缓存满了再写到硬盘，提高了性能
			 */
			//FileWriter outFw=new FileWriter(file);
			//BufferedWriter outPw=new BufferedWriter(outFw);
			//outPw.write(jsonStr,0,jsonStr.length());//要写入的内容、起始的位置、结束的位置
			//outPw.close();//这是很重要的，关闭流
			
			JSONObject objRoot=JSONObject.fromObject(jsonStr);//将jsonStr字符串转成JSONObject对象
			JSONArray imgsJson=(JSONArray) objRoot.get("data");//获得data节点内容
			for(int i1=0;i1<imgsJson.size()-1;i1++) {
				JSONObject jsonObject=imgsJson.getJSONObject(i1);//获得数组的JSONObject对象
				String objUri=(String)jsonObject.get("hoverURL");
				//输出下载的图片地址
				System.out.println(objUri);
				//下载图片
				PictureDownloadMachine.downloadImage(objUri,picture);
			}
		}
		
	}
}
