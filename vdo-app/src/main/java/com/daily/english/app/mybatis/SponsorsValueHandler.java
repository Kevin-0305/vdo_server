package com.daily.english.app.mybatis;

import com.daily.english.app.json.SponsorsJson;
import com.daily.english.app.util.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes(value = {SponsorsJson.class})
public class SponsorsValueHandler extends BaseTypeHandler<SponsorsJson> {

    @Override
    public SponsorsJson getNullableResult(ResultSet arg0, String arg1) throws SQLException {
        String i = arg0.getString(arg1);
        if (arg0.wasNull()) {
            return null;
        } else {
            return JsonUtil.parse(i, SponsorsJson.class);
        }
    }

    @Override
    public SponsorsJson getNullableResult(ResultSet arg0, int arg1) throws SQLException {
        String i = arg0.getString(arg1);
        if (arg0.wasNull()) {
            return null;
        } else {
            return JsonUtil.parse(i, SponsorsJson.class);
        }
    }

    @Override
    public SponsorsJson getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
        String i = arg0.getString(arg1);
        if (arg0.wasNull()) {
            return null;
        } else {
            return JsonUtil.parse(i, SponsorsJson.class);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement arg0, int arg1, SponsorsJson arg2, JdbcType arg3) throws SQLException {
        // TODO Auto-generated method stub
        arg0.setString(arg1, JsonUtil.stringify(arg2));
    }


}
