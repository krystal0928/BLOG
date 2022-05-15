package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.Admin;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.UserVo;

public interface AdminService extends IService<Admin> {

    Admin getUserByToken(String token);

    // 分页查询
    Page<Admin> getAdminList(Page<Admin> page, Admin info);
}
