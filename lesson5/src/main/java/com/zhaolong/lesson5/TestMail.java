package com.zhaolong.lesson5;

import com.zhaolong.lesson5.core.MailSender;
import com.zhaolong.lesson5.enums.MailContentTypeEnum;

import java.util.ArrayList;

public class TestMail {
    public static  void main(String[] args)throws Exception{
        new MailSender()
                .title("测试SpringBoot发送邮件")
                .content("简单文本内容发送")
                .contentType(MailContentTypeEnum.TEXT)
                .targets(new ArrayList<String>(){{
                    add("281355589@qq.com");
                }}) .send();
    }
}
