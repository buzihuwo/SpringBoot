package com.zhaolong.lesson6.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhaolong.lesson6.util.LoggerUtils;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/index")
public class IndexController {
    @GetMapping("/login")
    public JSONObject login(HttpServletRequest request, String name) throws Exception {
        HttpSession session=request.getSession();
        session.setAttribute("myName",name);
        JSONObject obj = new JSONObject();
        obj.put("msg", "用户:" + name + ",登录成功.");
        request.setAttribute(LoggerUtils.LOGGER_RETURN, obj);
        return obj;
    }
}
