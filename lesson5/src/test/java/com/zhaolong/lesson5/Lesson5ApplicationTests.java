package com.zhaolong.lesson5;

import com.zhaolong.lesson5.core.MailSender;
import com.zhaolong.lesson5.enums.MailContentTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson5ApplicationTests {
public static void main(String[] args) throws Exception {
    contextLoads();
}

    @Test
    public static void contextLoads() throws Exception {
        new MailSender()
                .title("测试SpringBoot发送邮件")
                .content("简单文本内容发送")
                .contentType(MailContentTypeEnum.TEXT)
                .targets(new ArrayList<String>(){{
                    add("281355589@qq.com");
                }}) .send();
    }

}
