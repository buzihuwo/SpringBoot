package com.zhaolong.lesson11.controller;

import com.zhaolong.lesson11.annotation.LoginRequired;
import com.zhaolong.lesson11.entity.User;
import com.zhaolong.lesson11.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@Api(value = "token")
public class ApiController {

    @Autowired
    private HttpServletRequest request;

    @LoginRequired
    @GetMapping("/test")
    public Object testLogin(@RequestBody User user) {
        String sss = request.getHeader(HttpHeaders.CONTENT_TYPE);
        return sss;
    }


    @GetMapping("/test1")
    public Object testLogin1() {
        return "success";
    }

    @PostMapping("/token")
    @ApiOperation(value = "登陆获取token", notes = "")
    public Object login(@RequestBody final Account account) {
        if (isValidPassword(account)) {
            String jwt = JwtUtil.generateToken(account.username);
            return new HashMap<String, String>() {{
                put("token", jwt);
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
        return "admin".equals(ac.username)
                && "admin".equals(ac.password);
    }
}
