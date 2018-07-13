package com.zhaolong.lesson10.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel(description = "user11")
public class User {
    @ApiModelProperty("id")
    private  Long id;
    @ApiModelProperty(value = "name",required = true)
    private String name;
    @ApiModelProperty("age")
    private int age;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
