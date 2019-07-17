package com.boot.example.demo.Mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.boot.example.demo.model.User;


@Mapper
public interface UserMapper {

    @Insert("insert into user (id,name,account,token,gmtcreate,gmtmodified) values " +
            "(#{id},#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
     void insert(User user);

}