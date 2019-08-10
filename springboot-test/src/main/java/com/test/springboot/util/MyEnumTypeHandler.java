package com.test.springboot.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 自定义类型转换
 * https://www.cnblogs.com/pandachenchen/p/8622051.html
 * 自定义泛型类型转换
 * https://blog.csdn.net/qq_26440803/article/details/83451221
 * @author Administrator
 */
public class MyEnumTypeHandler extends BaseTypeHandler<UserSexEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UserSexEnum parameter, JdbcType jdbcType)
			throws SQLException {
		 ps.setInt(i, parameter.value());
	}

	@Override
	public UserSexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int val = rs.getInt(columnName);
		return getEnum(val);
	}

	@Override
	public UserSexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return getEnum(rs.getInt(columnIndex));
	}

	@Override
	public UserSexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getEnum(cs.getInt(columnIndex));
	}
	
	/**
     * 根据值获得对应的枚举
     * @param val
     * @return
     */
    private UserSexEnum getEnum(int val){
        Class<UserSexEnum> sexClass = UserSexEnum.class;
        UserSexEnum[] sexs = sexClass.getEnumConstants();
        
        for(UserSexEnum se:sexs){
            if(se.value() == val){
                return se;
            }
        }
        return null;
    }

}
