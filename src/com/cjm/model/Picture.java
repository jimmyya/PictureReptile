package com.cjm.model;

/**
 * @time 2016年4月14日
 * @author CHEN
 * @param Picture：对象的关键词、文件路径、页数
 */

public class Picture {
	private String keyWord;//关键词
	private String pageNum;//下载的页数
	private String fileUri;//文件夹路径
	
	
	public Picture() {
		super();
	}
	public Picture(String keyWord,String pageNum,String fileUri) {
		super();
		this.keyWord=keyWord;
		this.pageNum=pageNum;
		this.fileUri=fileUri;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getFileUri() {
		return fileUri;
	}
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}
	
	
}
