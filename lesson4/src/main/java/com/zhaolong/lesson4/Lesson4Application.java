package com.zhaolong.lesson4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Lesson4Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Lesson4Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Lesson4Application.class);
    }
}
