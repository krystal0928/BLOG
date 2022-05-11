package com.krystal.blog.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.AdminMapper;
import com.krystal.blog.common.mapper.UserMapper;
import com.krystal.blog.common.model.Admin;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.UserVo;
import com.krystal.blog.common.service.AdminService;
import com.krystal.blog.common.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

}
