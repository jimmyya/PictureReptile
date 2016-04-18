package com.cjm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;

import com.cjm.model.Picture;
import com.cjm.utils.PictureDownload;

/**
 * @time 2016年4月14日
 * @author CHEN
 * @param 
 * @about 一个图片下载的系统
 */

@WebServlet("/WEB/jsp/CatchPicture")
public class CatchPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Picture picture;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatchPicture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//提示将要下载图片
/*		 获得当前线程的名字
 * 		 Thread current = Thread.currentThread();  
	     System.outPw.println(current.getName());  */
		//输出一些提示的信息，当然最好是写在日志中，我在这里就精简了这部分内容
		System.out.println("下载图片");
		//设置编码
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		//设置返回提示信息
		PrintWriter outPwPw=response.getWriter();
		//获得用户输入内容
		String keyWordStr=request.getParameter("keyWord");//获得关键字
		String pageNumStr=request.getParameter("pageNum");//获得页数
		String fileUriStr=request.getParameter("fileUri");//获得文件夹路径
		//构造Picture对象
		picture=new Picture(keyWordStr,pageNumStr,fileUriStr);
		
		if(keyWordStr==null||"".equals(keyWordStr)) {//返回失败的提示
			//当然你可以设置更多的检验，但是有更好的处理方式，之后我会使用异常处理去使系统具有恢复性
			request.setAttribute("message", "<script type='text/javascript'>alert('请输入关键字');</script>");
		} else {//万事俱备
			//调用下载过程函数
			//这里为什么要使用线程呢，关于线程的小秘密，我之后也会写
			//请注意我这里使用了内部匿名类
			Thread thread =new Thread(){
				public void run() {
					try {
						PictureDownload.downloadPicture(picture);//调用了整个系统最关键的部分
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			thread.setName("pictureCatchMachine");//给他起个名字
			thread.start();
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);//返回展示页面
	}

	
}
