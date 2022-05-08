package com.krystal.blog.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.UserMapper;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.UserVo;
import com.krystal.blog.common.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByToken(String token) {
        Long id = Long.parseLong(token.split(",")[0]);
        User user = this.lambdaQuery()
                .eq(User::getId,id).one();
        return user;
    }

    @Override
    public UserVo selectUser(Long userId,Long focusId) {
        return userMapper.selectUser(userId, focusId);
    }

    @Override
    public Page<UserVo> selectFocusUserList(Page<UserVo> page, Long userId, Long loginUserId) {
        return userMapper.selectFocusUserList(page, userId, loginUserId);
    }

    @Override
    public Page<UserVo> selectFansUserList(Page<UserVo> page,Long userId, Long loginUserId) {
        return userMapper.selectFansUserList(page, userId, loginUserId);
    }

    @Override
    public Long getUserIdFromToken(String token) {
        if (!StrUtil.isEmpty(token)) {
            User user = this.getUserByToken(token);
            if (null != user)
                return user.getId();
        }
        return 0L;
    }
}
