package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.model.vo.UserVo;

public interface UserService  extends IService<User> {

    User getUserByToken(String token) ;

    UserVo selectUser(Long userId,Long focusId);
}
