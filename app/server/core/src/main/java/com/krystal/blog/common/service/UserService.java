package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.UserVo;

import java.util.List;

public interface UserService  extends IService<User> {

    User getUserByToken(String token) ;

    UserVo selectUser(Long userId,Long focusId);

    Page<UserVo> selectFocusUserList(Page<UserVo> page, Long userId, Long loginUserId);

    Page<UserVo> selectFansUserList(Page<UserVo> page,Long userId, Long loginUserId);

    Long getUserIdFromToken(String token);
}
