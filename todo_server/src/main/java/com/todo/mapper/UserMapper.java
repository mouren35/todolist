package com.todo.mapper;

import com.todo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 数据层
 */
@Mapper
public interface UserMapper {

    User login(@Param("phone") String phone,@Param("password") String password);

    void register(User user);

    User hasRegister(String phone);
    @Select("select nick from tb_user where phone = #{phone} ;")
    String getUserNameByPhone(String phone);
}
