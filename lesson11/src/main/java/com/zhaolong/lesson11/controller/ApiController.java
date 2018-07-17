package com.zhaolong.lesson11.controller;

import com.zhaolong.lesson11.annotation.LoginRequired;
import com.zhaolong.lesson11.entity.User;
import com.zhaolong.lesson11.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(value = "token")
public class ApiController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;

    @LoginRequired
    @GetMapping("/userid")
    public Object getUserId(){
        String auth = request.getHeader(jwtUtil.HEADER_STRING);
        return jwtUtil.getIdByJWT(auth);
    }


    @GetMapping("/test1")
    public Object testLogin1() {
        return "success";
    }

    @PostMapping("/token")
    @ApiOperation(value = "登陆获取token", notes = "")
    public Object login(@RequestBody Account account) {
        if (isValidPassword(account)) {
            User user=getUser();
            String jwt = jwtUtil.generateToken(user.getUuid());
            return new HashMap<String, String>() {{
                put(jwtUtil.HEADER_STRING, jwt);
            }};
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    private static class Account {
        @ApiModelProperty(value = "用户名", required = true)
        public String username;
        @ApiModelProperty(value = "密码", required = true)
        public String password;
    }

    private boolean isValidPassword(Account ac) {
        User user=getUser();
        return user.getName().equals(ac.username)&&  user.getPassword().equals(ac.password);
    }

    private  User getUser(){
        User user=new User();
        user.setUuid(UUID.randomUUID().toString().replace("-", ""));
        user.setName("admin");
        user.setPassword("admin");
        return user;
    }
}
