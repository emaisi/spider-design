package org.spiderdesign.mailbox.controller;

import org.spiderdesign.common.CURDController;
import org.spiderdesign.executor.PluginConfig;
import org.spiderdesign.mailbox.mapper.MailMapper;
import org.spiderdesign.mailbox.model.Mail;
import org.spiderdesign.mailbox.service.MailService;
import org.spiderdesign.mailbox.utils.MailboxUtils;
import org.spiderdesign.model.JsonBean;
import org.spiderdesign.model.Plugin;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("mail")
public class MailController extends CURDController<MailService, MailMapper, Mail> implements PluginConfig {

    @RequestMapping("/test")
    public JsonBean<String> test(Mail mail, String email){
        try {
            JavaMailSenderImpl mailboxTemplate = MailboxUtils.createMailSender(mail);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mail.getUsername());
            mailMessage.setTo(email);
            mailMessage.setSubject("邮箱发送测试");
            mailMessage.setSentDate(new Date());//发送时间
            mailMessage.setText("邮箱发送测试");
            mailboxTemplate.send(mailMessage);
            return new JsonBean<String>(1, "测试成功");
        } catch (Exception e) {
            return new JsonBean<String>(-1, e.getMessage());
        }
    }

    @Override
    public Plugin plugin() {
        return new Plugin("邮箱配置","resources/mailList.html");
    }
}
