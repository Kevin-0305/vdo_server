package com.daily.english.app.mapper;

import com.daily.english.app.domain.Column;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ColumnMapper {

    @Insert("insert into ed_column(`Index`, ShowFont, status) " +
            "values(#{index}, #{showFont}, #{status}) ")
    @Options(useGeneratedKeys = true, keyProperty="columnID", keyColumn = "ColumnID")
    int insert(Column column);

    @Select("select ColumnID, `Index`, ShowFont, status " +
            "from ed_column ")
    List<Column> findColumnList();
}
