package com.example.wechatdemo.controller;

import com.example.wechatdemo.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lee on 2019-01-29.
 *
 * @author Lee
 */
@RestController
public class MenuController {
    @Autowired
    private HttpUtil httpUtil;

    /**
     * 创建菜单
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/menuCreate",method = RequestMethod.GET)
    public String menuCreate(HttpServletRequest request) throws Exception{
        return httpUtil.menuCreate().get("errmsg").toString();
    }

    /**
     * 删除菜单
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/menuDelete",method = RequestMethod.GET)
    public String menuDelete(HttpServletRequest request) throws Exception{
        return httpUtil.menuDelete().get("errmsg").toString();
    }


}
