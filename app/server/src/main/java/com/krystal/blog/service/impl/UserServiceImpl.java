package com.krystal.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.mapper.UserMapper;
import com.krystal.blog.model.User;
import com.krystal.blog.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper,User> implements UserService {

    @Override
    public User getUserByToken(String token) {
        Long id = Long.parseLong(token.split(",")[0]);
        User user = this.lambdaQuery()
                .eq(User::getId,id).one();
        return user;
    }
}
