package com.example.wechatdemo.util.http;

import java.util.Map;


/**
 * 
 * @author Lee
 * @date 2016-5-4
 * @describe 爬取数据后的结果集对象
 */

public class CrawlResult {
	private boolean isSuccess;        //是否采集成功
	private String content;	          //采集文本内容
	private Integer httpStatusCode;    //返回状态码

	private boolean isLogin;      //登录成功或失败，只作登录用，其它连接可的返回结果可忽略此项
	
	/**
	 * 取得验证码流文件后，需要在此关闭http连接
	 */
	private Map<String,Object> inputStream;  //采集流文件
	
	
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}


	public Map<String, Object> getInputStream() {
		return inputStream;
	}

	public void setInputStream(Map<String, Object> inputStream) {
		this.inputStream = inputStream;
	}

	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
}
