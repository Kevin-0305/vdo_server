package com.daily.english.app.mapper;

import com.daily.english.app.domain.Answer;
import com.daily.english.app.domain.AppVersion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AppVersionMapper {

    @Insert("insert into ed_app_version(newVersion, type,apkUrl,updateDescription," +
            "forceUpdate,forceVersion ) " +
            "values(#{newVersion}, #{type},#{apkUrl}, #{updateDescription}, #{forceUpdate} , #{forceVersion})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insertAppVersion(AppVersion appVersion);

    @Select("select newVersion, apkUrl, updateDescription,forceUpdate,forceVersion  " +
            "from ed_app_version where type = #{type} order by newVersion desc limit 1")
    AppVersion findAppVersionByType(@Param("type") int type);

    @Select("select id,type,newVersion, apkUrl, updateDescription,forceUpdate,forceVersion   " +
            "from ed_app_version")
    List<AppVersion> findAppVersionList();
}
