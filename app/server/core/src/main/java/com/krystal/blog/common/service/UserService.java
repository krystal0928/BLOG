package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.model.vo.UserVo;

import java.util.List;

public interface UserService  extends IService<User> {

    User getUserByToken(String token) ;

    UserVo selectUser(Long userId,Long focusId);

    List<UserVo> selectFocusUserList(Long userId, Long tokenUserId);


    Long getUserIdFromToken(String token);
}
