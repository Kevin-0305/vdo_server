package com.daily.english.app.mapper;

import com.daily.english.app.domain.Praise;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PraiseMapper {

    @Insert("insert into ed_praise(uid, vid) " +
            "values(#{uid}, #{vid})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insert(Praise praise);

    @Select("select id, uid, vid " +
            "from ed_praise")
    List<Praise> findPraiseList();
}
