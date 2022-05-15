package com.krystal.blog.common.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.krystal.blog.common.beans.ApplicationTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class EmailService {

    @Resource
    private ApplicationTemplate applicationTemplate;

    public MailAccount initMailAccount() {
        MailAccount account = new MailAccount();
        account.setHost(applicationTemplate.getEmailHost());
        account.setPort(applicationTemplate.getEmailPort());
        account.setAuth(true);
        account.setFrom(applicationTemplate.getEmailFrom());
        account.setUser(applicationTemplate.getEmailUser());
        account.setPass(applicationTemplate.getEmailPass());
        return account;
    }

    /**
     * 发送验证码邮件
     */
    public void sendCodeEmail(String to, String title, String code) {
        MailAccount account = initMailAccount();
        ArrayList<String> mailList = CollUtil.newArrayList(to);
        String html = String.format("<h1>您的验证码是：%s</h1>", code);
        MailUtil.send(account, mailList, title, html, true);
    }

    /**
     * 发送邮件
     */
    public void sendActionEmail(String from,String to, String action, String email) {
        MailAccount account = initMailAccount();
        ArrayList<String> mailList = CollUtil.newArrayList(email);
        String html = String.format("<h1>%s %s %s</h1>", from, action, to );

        MailUtil.send(account, mailList, "通知", html, true);
    }
}
