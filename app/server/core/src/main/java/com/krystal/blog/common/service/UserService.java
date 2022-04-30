package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.User;

public interface UserService  extends IService<User> {

    User getUserByToken(String token) ;
}
