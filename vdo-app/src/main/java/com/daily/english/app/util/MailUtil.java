package com.daily.english.app.util;

import com.daily.english.app.domain.YjUserBmInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱工具类
 *
 * @author Staff
 */
public class MailUtil {

    /**
     * 发送验证码
     *
     * @param account
     * @param code
     */
    public static boolean sendCode(String account, String code) {
        // 设置邮件服务器信息
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("mail.chinadailyhk.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);

        // 邮箱用户名
        mailInfo.setUserName("ed");
        // 邮箱密码
        mailInfo.setPassword("English20160118");
        // 发件人邮箱
        mailInfo.setFromAddress("ed@chinadailyhk.com");
        // 收件人邮箱
        mailInfo.setToAddress(account);
        // 邮件标题
        mailInfo.setSubject("Verification Code from VDO English 來自VDO English的註冊驗證碼");
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

        mailInfo.setContent(buffer.toString());

        // 发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        // 发送文体格式
        //sms.sendTextMail(mailInfo);
        // 发送html格式
        return sms.sendHtmlMail(mailInfo);

    }

    /**
     * 发送报名信息邮件
     *
     * @param account     接收邮箱
     * @param userBmInfos 报名信息：老师报名邮件传递报名信息集合，如果是个人不需要传
     * @return
     */
    public static boolean sendBmUser(String account, List<YjUserBmInfo> userBmInfos) {
        // 设置邮件服务器信息
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("mail.chinadailyhk.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);

        // 邮箱用户名
        mailInfo.setUserName("ed");
        // 邮箱密码
        mailInfo.setPassword("English20160118");
        // 发件人邮箱
        mailInfo.setFromAddress("ed@chinadailyhk.com");
        // 收件人邮箱
        mailInfo.setToAddress(account);
        // 邮件标题
        mailInfo.setSubject("來自VDO English21世界杯报名提醒");
        // 邮件内容
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html><body>" +
                "Dear contestant,<br>" +
                "Congratulations!  Your online application has been successfully received.  Next, please pay the registration fee ($100, non-refundable, no cancellations), by posting a crossed cheque payable to “The English-Speaking Union (Hong Kong) Ltd” within seven working days. <br>" +
                "Post the cheque to the following address:   <br>" +
                "Room 713A Champion Building, 301 Nathan Road, Kowloon <br>" +
                "Please mark the envelope with “NESC Hong Kong Region” and indicate your category (Primary/Secondary School Category). <br>" +
                "An official receipt will be sent to each applicant by email. <br>" +
                "*To avoid any unnecessary administration fees, please ensure you have sufficient balance in the bank account. <br>");
        if (userBmInfos != null) {
            buffer.append("Your login access are as follows:<br>");
            for (YjUserBmInfo userBmInfo : userBmInfos) {
                buffer.append("name：" + userBmInfo.getNameEn() + "<br>" +
                        "Login I.D.: " + userBmInfo.getEmail() + "<br>" +
                        "Password: " + userBmInfo.getRegisterCode() + "<br>" +
                        "<br>");
            }
        }
        buffer.append("Please upload you video and script on our website <a target='_blank' href='https://www.vdoenglish.com'>www.vdoenglish.com</a> <br>" +
                "After we receive your registration fee, you will be eligible for the preliminary round.  Semi-Finalists will be selected by a panel of expert judges based on their evaluation of contestants’ video materials.  Qualified Semi-Finalists will receive notification by email and/or a phone call in November.  For updated information and the latest news on the competition, please refer to our website <a href='https://www.vdoenglish.com'>www.vdoenglish.com</a>. <br>" +
                "We wish you every success! <br>");
        buffer.append("<br>");
        buffer.append("親愛的參賽者： <br>" +
                "您已成功在網上完成報名。請於七個工作天內，以郵寄方式支付報名費（港幣一百元正的劃線支票，不可退款，不可取消），支票抬頭請填上 \"The English-Speaking Union (Hong Kong) Ltd\"。 <br>" +
                "請將支票寄到以下地址：  <br>" +
                "九龍彌敦道301號嘉賓商業大廈713A室 <br>" +
                "請在信封註明 “NESC Hong Kong Region” 以及您的比賽組別（小學/中學組別）。 <br>" +
                "我們並會透過電郵向每位申請人發出正式收據。 <br>" +
                "*為避免任何收取任何不必要的行政費，請確保您的銀行帳戶有足夠的餘額。 <br>");
        if (userBmInfos != null) {
            buffer.append("以下是參賽者的登入資料：<br>");
            for (YjUserBmInfo userBmInfo : userBmInfos) {
                buffer.append("姓名：" + userBmInfo.getNameCh() + "<br>" +
                        "帳號：" + userBmInfo.getEmail() + "<br>" +
                        "密碼：" + userBmInfo.getRegisterCode() + "<br>" +
                        "<br>");
            }
        }
        buffer.append("請將比賽視頻及講稿上載到<a href='https://www.vdoenglish.com'>www.vdoenglish.com</a>。 <br>" +
                "我們收到您的報名費後，您將獲得參加預賽的資格。專業評判團會根據所有預賽選手所提交的片段進行評分並選出初賽選手。合格的初賽選手將於11月以電郵和/或電話收到有關通知。有關比賽的最新資訊和最新消息，請瀏覽網站<a href='https://www.vdoenglish.com'>www.vdoenglish.com</a>。 <br>" +
                " <br>" +
                "   祝 <br>" +
                "一切順利！ <br>" +
                " <br>" +
                "此致 <br>" +
                "Mr. Joshua HO <br>" +
                "「21世界杯」全國英語演講比賽 <br>" +
                "香港賽區（中學組）組委會 <br>");
        buffer.append("</body></html>");
        mailInfo.setContent(buffer.toString());

        // 发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        // 发送文体格式
        //sms.sendTextMail(mailInfo);
        // 发送html格式
        return sms.sendHtmlMail(mailInfo);

    }


    public static void main(String[] args) {
        List<YjUserBmInfo> userBmInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            YjUserBmInfo user1 = new YjUserBmInfo();
            user1.setNameCh("第" + (i + 1) + "个");
            user1.setNameEn("NO." + (i + 1) + " name");
            user1.setEmail("no" + (i + 1) + "@vdoenglish.com");
            user1.setRegisterCode(RandomStringUtil.getRandomString(8));
            userBmInfos.add(user1);
        }

        sendBmUser("1017847132@qq.com", userBmInfos);
    }
}
