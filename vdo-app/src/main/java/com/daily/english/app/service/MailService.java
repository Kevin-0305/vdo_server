package com.daily.english.app.service;

import com.daily.english.app.json.AttachmentJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class MailService {

    @Value("${spring.mail.username}")
    //使用@Value注入application.properties中指定的用户名
    private String from;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    //用于发送文件
    private JavaMailSender mailSender;

    /**
     * 发送普通文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(from);//发信人

        mailSender.send(message);
    }
    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    public void sendHtmlMail(String to, String subject, String content){

        log.info("发送HTML邮件开始：{},{},{}", to, subject, content);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from,"VDO English");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
            log.info("发送HTML邮件成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送HTML邮件失败：", e);
        }
    }

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param code 验证码
     */
    public void sendPasswordCodeMail(String to, String subject,String code){

        log.info("发送HTML邮件开始：{},{},{}", to, subject, code);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            // 邮件内容
            StringBuffer buffer = new StringBuffer();

            buffer.append("<img src=\"http://112.74.31.9/edback/videoImg/127398nc93912mxm1xlogo.png\" style=\"width:12%\">");
            buffer.append("<br>");
            buffer.append("<hr align=\"left\" style=\"width:678px\">");
            buffer.append("Thanks for your interest in VDO English. Please enter your six-digit code on your page:");
            buffer.append("<br>");
            buffer.append(code);
            buffer.append("<br>");
            buffer.append("<br>");
            buffer.append("感謝您使用VDO English。請在頁面輸入下列六位驗證碼：");
            buffer.append("<br>");
            buffer.append(code);
            buffer.append("<br>");
            buffer.append("<br>");
            buffer.append("Regards,");
            buffer.append("<br>");
            buffer.append("VDO English團隊");
            helper.setFrom(from,"VDO English");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(buffer.toString(), true);//true代表支持html
            mailSender.send(message);
            log.info("发送HTML邮件成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送HTML邮件失败：", e);
        }
    }

    /**
     * 发送注册验证码
     * @param to 收件人
     * @param subject 主题
     * @param code 验证码
     */
    public void sendRegisterMail(String to, String subject,String code){

        log.info("发送HTML邮件开始：{},{},{}", to, subject, code);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            // 邮件内容
            StringBuffer buffer = new StringBuffer();

            buffer.append("<img src=\"http://112.74.31.9/edback/videoImg/127398nc93912mxm1xlogo.png\" style=\"width:12%\">");
            buffer.append("<br>");
            buffer.append("<hr align=\"left\" style=\"width:678px\">");
            buffer.append("Thanks for your interest in VDO English. Please enter your six-digit code on your sign-up page:");
            buffer.append("<br>");
            buffer.append(code);
            buffer.append("<br>");
            buffer.append("<br>");
            buffer.append("感謝您使用VDO English。請在註冊頁面輸入下列六位驗證碼：");
            buffer.append("<br>");
            buffer.append(code);
            buffer.append("<br>");
            buffer.append("<br>");
            buffer.append("Regards,");
            buffer.append("<br>");
            buffer.append("VDO English團隊");
            helper.setFrom(from,"VDO English");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(buffer.toString(), true);//true代表支持html
            mailSender.send(message);
            log.info("发送HTML邮件成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送HTML邮件失败：", e);
        }
    }

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    public void sendAttachmentMail(String to, String subject, String content, String filePath){
        log.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, filePath);

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(from,"VDO English");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath.replaceAll("/static/image",uploadFolder)));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
            log.info("发送带附件邮件成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送带附件邮件失败", e);
        }
    }
    /**
     * 发送多附件邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param attachmentJson 附件json
     */
    public void sendAttachmentMail(String to, String subject, String content, AttachmentJson attachmentJson){
        log.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, attachmentJson);

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(from,"VDO English");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            if (attachmentJson != null && attachmentJson.getContent().size()>0){//多附件发送邮件
                for (AttachmentJson.AttachmentRecord attachmentRecord : attachmentJson.getContent()) {
                    FileSystemResource file = new FileSystemResource(new File(attachmentRecord.getFileUrl().replaceAll("/static/image",uploadFolder)));
                    helper.addAttachment(attachmentRecord.getFileName(), file);//添加附件，可多次调用该方法添加多个附件
                }
            }
            mailSender.send(message);
            log.info("发送带附件邮件成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送带附件邮件失败", e);
        }
    }

    /**
     * 发送带图片的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId 图片ID，用于在<img>标签中使用，从而显示图片
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
        log.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from,"VDO English");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            log.info("发送带图片邮件成功");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送带图片邮件失败", e);
        }
    }
    
}