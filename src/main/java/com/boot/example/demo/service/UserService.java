package com.boot.example.demo.service;

import com.boot.example.demo.Mapper.UserMapper;
import com.boot.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void CreateOrUpdate(User user) {
        User Dbuser = userMapper.SearchAccountId(user.getAccount_id());
        if (Dbuser == null) {
            //插入
            user.setGmtcreate(System.currentTimeMillis());
            user.setGmtmodified(user.getGmtcreate());
            userMapper.insert(user);
        } else {
            //更新
            Dbuser.setGmtmodified(System.currentTimeMillis());
            Dbuser.setAvatar_url(user.getAvatar_url());
            Dbuser.setName(user.getName());
            Dbuser.setToken(user.getToken());
            userMapper.update(Dbuser);

        }
    }
}
