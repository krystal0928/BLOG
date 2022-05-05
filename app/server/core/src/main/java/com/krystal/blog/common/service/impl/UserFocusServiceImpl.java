package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.UserFocusMapper;
import com.krystal.blog.common.model.UserFocus;
import com.krystal.blog.common.service.UserFocusService;
import org.springframework.stereotype.Service;

@Service
public class UserFocusServiceImpl extends ServiceImpl<UserFocusMapper,UserFocus> implements UserFocusService {


}
