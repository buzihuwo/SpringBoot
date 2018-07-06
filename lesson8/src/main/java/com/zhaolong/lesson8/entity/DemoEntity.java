package com.zhaolong.lesson8.entity;

import com.zhaolong.lesson8.validator.FlagValidator;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DemoEntity implements Serializable {

    @NotBlank
    @Length(min = 2,max = 10)
    private String name;

    @Min(value = 1)
    public int age;

    @NotBlank(message="mailmailmailmail")
    @Email
    private String mail;

    @FlagValidator(value = "1,2,3",message="asdasdas")
    private String flag;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
