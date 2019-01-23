package com.example.wechatdemo.model;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lee on 2019-01-23.
 *
 * @author Lee
 */
@XmlRootElement(name="demoUser")  //xml根元素demoUser
public class DemoUser {
    private String name;
    private String password;
    public DemoUser() {
    }
    public String getName() {
        return name;
    }
    @XmlElement   //xml子元素 name
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    @XmlElement            //xml子元素 password
    public void setPassword(String password) {
        this.password = password;
    }
}