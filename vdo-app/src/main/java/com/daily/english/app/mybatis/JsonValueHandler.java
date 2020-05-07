package com.daily.english.app.mybatis;

import com.daily.english.app.json.*;
import com.daily.english.app.util.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author liuzd
 * @Title: JsonValueHandler
 * @Package com.jjshome.xf.core.mybatis
 * @Description: MyBatis 自定义枚举转换, 用于转换JSON数据
 * @date 2015/12/29
 */
/* 数据库中的数据类型 */
@MappedTypes(value = {PdfJson.class, GuestJson.class, PlayersJson.class,
        PicJson.class, VideoJson.class, AttachmentJson.class, DropJson.class,CompetePicJson.class,EventNewsAttaJson.class})
public class JsonValueHandler extends BaseTypeHandler<Object> {
    private Class<Object> type;

    public JsonValueHandler(Class<Object> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter,
                                    JdbcType jdbcType) throws SQLException {

        ps.setString(i, JsonUtil.stringify(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName)
            throws SQLException {

        return JsonUtil.parse(rs.getString(columnName), type);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return JsonUtil.parse(rs.getString(columnIndex), type);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return JsonUtil.parse(cs.getString(columnIndex), type);
    }
}
