package com.boot.example.demo.Mapper;


import com.boot.example.demo.dto.QuestionDTO;
import org.apache.ibatis.annotations.*;
import com.boot.example.demo.model.User;

import java.util.List;


@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account,token,gmtcreate,gmtmodified,avatar_url) values " +
            "(#{name},#{account_id},#{token},#{gmtcreate},#{gmtmodified},#{avatar_url})")
    void insert(User user);


    @Select("select * from user where token = #{token}")
    User findToken(@Param("token") String token);

    @Select("select * from user")
    List<User> searchAll();

    @Select("select * from user where id = #{id}")
    User findByID(@Param("id") Integer id);

    @Select("select * from user ")
    List<QuestionDTO> findByIDCookie();

    @Update("update user set name = #{name},avatar_url = #{avatar_url},gmtmodified = #{gmtmodified} where id = #{id}")
    void update(User dbuser);

    @Select("select * from user where account = #{account_id}")
    User SearchAccountId(@Param("account_id")String account_id);
}