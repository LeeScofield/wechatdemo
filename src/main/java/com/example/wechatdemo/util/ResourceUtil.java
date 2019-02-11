package com.example.wechatdemo.util;

import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Lee on 2019-01-29.
 * 获取资源工具类
 * @author Lee
 */
public class ResourceUtil implements AutoCloseable{
    FileInputStream fis = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Properties properties = null;
    InputStream is = null;


    @Override
    public void close() throws Exception {
        if(fis != null) fis.close();
        if(is != null) is.close();
        bos.close();
    }

    public ResourceUtil(String localUrl) throws Exception{
        this.fis = new FileInputStream(new File(localUrl));
    }

    public ResourceUtil(File file) throws Exception{
        fis = new FileInputStream(file);
    }

    public ResourceUtil(Resource resource) throws Exception{
        properties = new Properties();
        properties.load(resource.getInputStream());
        fis = new FileInputStream(resource.getFile());
    }

    public ResourceUtil(InputStream inputStream) throws Exception{
        is = inputStream;
        properties = new Properties();
        properties.load(inputStream);
    }


    /**
     * 返回资源文本
     * @return
     * @throws Exception
     */
    public String getResource() throws Exception{
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = fis.read(buffer)) > 0) {
            bos.write(buffer,0,len);
        }

        if(bos != null){
            return bos.toString();
        }else{
            return "";
        }
    }

    /**
     * 返回properties
     * @return
     */
    public Properties getProperties(){
        return this.properties;
    }
}