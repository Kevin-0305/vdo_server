package com.daily.english.app.service;

import com.daily.english.app.domain.CmsUser;
import com.daily.english.app.dto.CmsUserDto;
import com.daily.english.app.mapper.CmsUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CmsUserService {

    @Autowired
    private CmsUserMapper cmsUserMapper;

    public PageInfo<CmsUser> selectCmsUserPage(CmsUserDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<CmsUser> list = cmsUserMapper.selectCmsUserPage(pageSearchDto);
        PageInfo<CmsUser> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public List<CmsUser> selectCmsUserList(CmsUser juryUser) {
        return cmsUserMapper.selectList(juryUser);
    }

    public CmsUser selectCmsUserById(String id) {
        return cmsUserMapper.selectById(id);
    }


    public void saveCmsUser(CmsUser juryUser) {
        if (null != juryUser.getId() && juryUser.getId() > 0) {
            juryUser.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            cmsUserMapper.updateCmsUser(juryUser);
        } else {
            juryUser.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            cmsUserMapper.insertCmsUser(juryUser);
        }
    }

    public void modifyCmsUserInformation(CmsUser juryUser) {
        juryUser.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        cmsUserMapper.updateCmsUser(juryUser);
    }

    public void deleteCmsUser(String id) {
        CmsUser juryUser = new CmsUser();
        juryUser.setId(Integer.parseInt(id));
        juryUser.setStatus(-1);
        cmsUserMapper.updateCmsUser(juryUser);
    }

    public CmsUser findCmsUserByUserName(String userName) {
        CmsUser cmsUser = cmsUserMapper.selectByUserName(userName);
        return cmsUser;
    }

}
