package com.krystal.blog.common.util;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

import java.util.ArrayList;

/**
 * 邮件
 */
public class EmailUtil {
    private static final String HOST = "smtp.qq.com";//邮件服务器的SMTP地址，可选，默认为smtp.<发件人邮箱后缀>
    private static final int PORT = 587;// 邮件服务器的SMTP端口，可选，默认25
    private static final String FROM = "2545718105@qq.com";//发件人（必须正确，否则发送失败）
    private static final String USER = "2545718105";//用户名，默认为发件人邮箱前缀
    private static final String PASS = "wrxxijsetnwldihb";//密码（注意，某些邮箱需要为SMTP服务单独设置授权码)

    public static MailAccount getMailCount() {
        MailAccount account = new MailAccount();
        account.setHost(HOST);
        account.setPort(PORT);
        account.setAuth(true);
        account.setFrom(FROM);
        account.setUser(USER);
        account.setPass(PASS);
        return account;
    }

    /**
     * 发送普通邮件
     */
    public static void sendNormalEmail(String to, String title, String code) {
        MailAccount account = getMailCount();
        ArrayList<String> mailList = CollUtil.newArrayList(to);
        String html = String.format("<h1>您的验证码是：%s</h1>", code);
        MailUtil.send(account, mailList, title, html, true);
    }

    public static void main(String[] args) {
        String code = RandomUtil.randomStringUpper(10);
        sendNormalEmail("idiotalex@163.com","验证码", code);
    }
}


