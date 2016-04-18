package com.cjm.model;

/**
 * @time 2016年4月14日
 * @author CHEN
 * @param 
 * jsonStr:需要格式化的json字符串
 * jonFormat:格式化的json字符串，建议就是当需要进行很多字符串拼接的时候，使用
 * 			StringBuffer，至于为什么可以看看我写的String、StringBuffer、StringBuild的区别
 * @about 对jsonStr进行格式化
 * 	对jsonStr的格式化其实就是
 * 			1、使用适量的换行
 * 			2、使用适当的缩进
 * 		  例子：
 * 		{
		    "data": {
		        "id": 1,
		        "name": "junming",
		        "wife": [
		            {
		                "id": 1,
		                "name": "yingli"
		            },
		            {
		                "id": 2,
		                "name": "yingli"
		            }
		        ]
		    }
		}
 */
public class JsonFormat {
	public static String format(String jsonStr) {
	    int level = 0;
	    StringBuffer jsonFormatStr = new StringBuffer();
	    for(int i=0;i<jsonStr.length();i++){
	      char c = jsonStr.charAt(i);//取出jsonStr中的所有字符
	      if(level>0&&'\n'==jsonFormatStr.charAt(jsonFormatStr.length()-1)){
	        jsonFormatStr.append(getLevelStr(level));
	      }
	      switch (c) {//换行//缩进
	      case '{': 
	      case '[':
	        jsonFormatStr.append(c+"\n");
	        level++;
	        break;
	      case ',': 
	        jsonFormatStr.append(c+"\n");
	        break;
	      case '}':
	      case ']':
	        jsonFormatStr.append("\n");
	        level--;
	        jsonFormatStr.append(getLevelStr(level));
	        jsonFormatStr.append(c);
	        break;
	      default:
	        jsonFormatStr.append(c);
	        break;
	      }
	    }
	    
	    return jsonFormatStr.toString();

	  }
	  
	  private static String getLevelStr(int level){
	    StringBuffer levelStr = new StringBuffer();
	    for(int levelI = 0;levelI<level ; levelI++){
	      levelStr.append("\t");//增加空格
	    }
	    return levelStr.toString();
	  }
}
