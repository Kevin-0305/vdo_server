package com.daily.english.app.job;

import com.daily.english.app.domain.Email;
import com.daily.english.app.service.EmailService;
import com.daily.english.app.service.MailService;
import com.daily.english.app.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class MailTask {
    @Autowired
    private EmailService emailService;
    @Autowired
    private MailService mailService;

    //3.添加定时任务
    @Scheduled(cron = "0/10 * * * * ?")
    private void configureTasks() {
        String nowTime = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        List<Email> emails = emailService.queryMailToSend();
        for (Email email : emails) {
            try {
                mailService.sendAttachmentMail(email.getAddressees(), email.getSubject(), email.getContent(), email.getAttachmentJson());
                email.setStatus(1);
                email.setSendTime(nowTime);
                emailService.updateEmail(email);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("发送邮件失败邮箱({}) 邮件任务ID：{}", email.getAddressees(), email.getId());
            }
        }
    }
}