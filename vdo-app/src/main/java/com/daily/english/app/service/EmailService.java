package com.daily.english.app.service;

import com.daily.english.app.domain.Email;
import com.daily.english.app.dto.EmailDto;
import com.daily.english.app.mapper.EmailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private MailService mailService;

    public List<Email> selectEmailList(Email email) {
        return emailMapper.selectList(email);
    }

    public List<Email> queryMailToSend() {
        return emailMapper.queryMailToSend();
    }

    public Email selectEmailById(String id) {
        return emailMapper.selectById(id);
    }

    public void updateEmail(Email email){
        email.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        emailMapper.updateEmail(email);
    }

    /**
     * 保存修改邮件信息
     * @param email
     */
    public void saveEmail(Email email) {
        if (null != email.getId() && email.getId() > 0) {
            email.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            emailMapper.updateEmail(email);
        } else {
            email.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            emailMapper.insertEmail(email);
        }
    }

    /**
     * 及时发送
     * @param email
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void sendEmailSave(Email email){
        String nowTime = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        email.setCreateTime(nowTime);
        email.setSendTime(nowTime);
        email.setStatus(1);
        emailMapper.insertEmail(email);
        mailService.sendAttachmentMail(email.getAddressees(),email.getSubject(),email.getContent(),email.getAttachmentJson());
    }

    /**
     * 定时发送
     * @param email
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void sendTimeEmailSave(Email email){
        email.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        email.setStatus(2);//设置成等待发送
        emailMapper.insertEmail(email);
    }

    /**
     * 邮件分页查询
     * @param emailDto
     * @return
     */
    public PageInfo<Email> selectEmailPage(EmailDto emailDto) {
        PageHelper.startPage(emailDto.getPageNo(), emailDto.getPageSize());
        List<Email> list = emailMapper.selectListPage(emailDto);
        PageInfo<Email> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteEmailById(String id) {
        Email email = new Email();
        email.setId(Integer.parseInt(id));
        email.setStatus(-1);
        emailMapper.updateEmail(email);
    }


}
