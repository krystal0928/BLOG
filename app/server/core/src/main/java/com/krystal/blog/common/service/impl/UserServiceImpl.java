package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.UserMapper;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.service.UserService;
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
