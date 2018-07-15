package com.zhaolong.lesson11.controller;

import com.zhaolong.lesson11.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.zhaolong.lesson11.util.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/api")
@Api(value = "token")
public class ApiController {

    @GetMapping("/index")
    @ApiOperation(value = "测试token", notes = "")
    public @ResponseBody Object hellWorld(@RequestHeader(value = USER_NAME) String userId) {
        return "Hello World! This is a protected api, your use id is "+userId;
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
        @ApiModelProperty(value = "用户名",required = true)
        public String username;
        @ApiModelProperty(value = "密码",required = true)
        public String password;
    }

    private boolean isValidPassword(Account ac) {
        return "admin".equals(ac.username)
                && "admin".equals(ac.password);
    }
}
