package com.zhaolong.lesson5.core;

import com.zhaolong.lesson5.entity.MailEntity;
import com.zhaolong.lesson5.enums.MailContentTypeEnum;

import java.util.List;

public class MailSender {
    //    邮件实体
    private static MailEntity mail = new MailEntity();

    //    设置邮件标题
    public MailSender title(String title) {
        mail.setTitle(title);
        return this;
    }

    //    设置邮件内容
    public MailSender content(String content) {
        mail.setContent(content);
        return this;
    }

    //    设置邮件格式
    public MailSender contentType(MailContentTypeEnum typeEnum) {
        mail.setContentType(typeEnum.getValue());
        return this;
    }

    //    设置请求目标邮件地址
    public MailSender targets(List<String> targets) {
        mail.setList(targets);
        return this;
    }

    //    执行发送邮件
    public void send() throws Exception {
        if (mail.getContentType() == null) mail.setContentType(MailContentTypeEnum.HTML.getValue());
        if (mail.getTitle() == null || mail.getTitle().trim().length() == 0)
            throw new Exception("邮件标题没有设置.调用title方法设置");

    }
}
