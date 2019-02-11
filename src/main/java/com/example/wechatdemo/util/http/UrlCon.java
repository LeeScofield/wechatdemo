package com.example.wechatdemo.util.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Lee
 * @date 2016-5-4
 * @describe 传入url信息
 */
public class UrlCon implements Serializable{
	
	private static final long serialVersionUID = -5362681690857070239L;
	
	private String url;       //爬取URL
	private String charSet;   //爬取网站字符集
	private Map<String,String> paras = new HashMap<String, String>(); //请求参数

	private URL urlRequest;           //在初始化UrlCon时初始化该对象
	private Map<String,String> headers = new HashMap<String, String>(); //设置请求头信息
	private String postData = "";     //POST请求时的流文件
	private String contentType;		  //POST请求时的contentType，初始化默认为：multipart/form-data，可在set方法中修改


	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParas() {
		return paras;
	}

	public void setParas(Map<String, String> paras) {
		this.paras = paras;
	}

	public void setUrl(String url) {
		if(this.paras != null && !this.paras.isEmpty()){
			this.paras.clear();
		}
		if(this.headers != null && this.headers.isEmpty()){
			this.headers.clear();
		}
		if(this.postData != null && !this.postData.trim().equals("")){
			this.postData = "";
		}
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setCharSet(String charSet){
		this.charSet = charSet;
	}
	
	public String getCharSet(){
		return charSet;
	}

	public UrlCon(String url) throws Exception{
		if(url == null || url.equals("")){
			throw new Exception("url值为null");
		}
		this.url = url;
		this.charSet = "UTF-8"; //默认UTF-8字符集
		this.contentType = "multipart/form-data";
		this.urlRequest = new URL(url);
	}
	
	public UrlCon(String url,String charsetName) throws Exception{
		this(url);
		this.charSet = charsetName;
	}
	
	public int getPort(){
		if(this.urlRequest.getPort() == -1){
			return 80;
		}else{
			return this.urlRequest.getPort();
		}
	}
	
	public String getHost(){
		return this.urlRequest.getHost();
	}
	
	public String getProtocol(){
		return this.urlRequest.getProtocol();
	}
	
	public String getPath(){
		return this.urlRequest.getPath();
	}
	
	/**
	 * 深克隆
	 * @return 返回该对象的一个克隆对象
	 */
	public Object deepClon(){
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			
			bais = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(oos == null) oos.close();
				if(baos == null) baos.close();
				
				if(ois == null) ois.close();
				if(ois == null) ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	public static void main(String[] args) throws Exception {
		UrlCon u = new UrlCon("http://www.buhuome.com");
		System.out.println(u.getPath());
	}
	
}
