package com.ruyicai.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction  extends ActionSupport implements ServletRequestAware, ServletResponseAware, ServletContextAware{ 
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext application;
	protected PrintWriter out;
    
	/**
	 * 初始化设置Request,并在其中初始化session
	 * 
	 * @param request
	 */
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	/**
	 * 初始化设置response
	 * 
	 * @param response
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		this.response.setContentType("text/html; charset=utf-8");
		try {
			out = this.response.getWriter();
		} catch (IOException e) {

		}
	}

	/**
	 * 初始化设置application
	 * 
	 * @param application
	 */
	@Override
	public void setServletContext(ServletContext application) {
		this.application = application;
	}

	/**
	 * 获取本地IP
	 * 
	 * @return 本地IP
	 */
	public String getLocalIp() {
		return request.getRemoteAddr();
	}
	/**
	 * 向客户端输出JSON
	 * @param jsonObj JSON
	 */
	protected void outPrint(JSONObject jsonObj){
		outPrint(jsonObj.toString());
	}
	
	/**
	 * 向客户端输出内容
	 * @param str 内容
	 */
	protected void outPrint(String str){
		out.print(str);
		out.flush();
		out.close();
	}
	
}
