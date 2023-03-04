package com.mashibing.controller.testcontroller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mashibing.bean.TblUserRecord;

import com.mashibing.returnjson.Permission;
import com.mashibing.returnjson.Permissions;
import com.mashibing.returnjson.ReturnObject;
import com.mashibing.returnjson.UserInfo;
import com.mashibing.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@RestController

@CrossOrigin(originPatterns =  "*", allowedHeaders = "*", methods = {}, allowCredentials = "true")
/**默认情况下
 * origins allowedHeaders methods allowCredentials 接受全部请求可以不写，allowCredentials等于TRUE时报错
 * */
//@CrossOrigin(origins = "*",allowedHeaders = "*",methods = {},allowCredentials = "true")

public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 3.发起请求/
     */
    @RequestMapping("/auth/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        System.out.println("login");
        TblUserRecord tblUserRecord = loginService.login(username, password);
        tblUserRecord.setToken(tblUserRecord.getUserName());
        tblUserRecord.setId(1);
        tblUserRecord.setMovePhone("18138209273");
        //将用户数据写到session中
        session.setAttribute("userRecord", tblUserRecord);
        System.out.println(session.getId());
        ReturnObject returnObject = new ReturnObject(tblUserRecord);
        return JSONObject.toJSONString(returnObject);
    }



    @RequestMapping("/user/info")
    public String getInfo(HttpSession session) {
        /**
         * @权限管理模块
         * 返回权限管理信息
         * */
        System.out.println(session.getId());
        System.out.println("---------------");
        System.out.println("userRecord: "+session.getAttribute("userRecord"));
        TblUserRecord tblUserRecord = (TblUserRecord) session.getAttribute("userRecord");

        //获取权限管理模块信息
        //获取到的模块数据信息（分隔符從零開始遇到“-”分割返回）
        String[] split = tblUserRecord.getTblRole().getRolePrivileges().split("-");
        Permissions permissions = new Permissions();
        //向权限集合对象中添加具体的权限
        List<Permission> permissionList = new ArrayList<>();
        for (String s : split) {
            System.out.println("s的值为："+s);
            permissionList.add(new Permission(s));
            System.out.println("permissionList:"+permissionList);

        }
        permissions.setPermissions(permissionList);
        System.out.println("permissions:"+permissions);
        //设置返回值的result
        UserInfo userInfo = new UserInfo(tblUserRecord.getUserName(), permissions);
        System.out.println("userInfo："+userInfo);
        String s = JSONObject.toJSONString(new ReturnObject(userInfo));
        System.out.println("json:"+s);
        return s;
    }


}
