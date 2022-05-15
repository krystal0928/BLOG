package com.krystal.blog.api;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.annotation.NoNeedLogIn;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.model.Admin;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.ArticleType;
import com.krystal.blog.common.model.vo.ArticleTypeVo;
import com.krystal.blog.common.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 接口
@RestController
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private SnowFlakeTemplate snowFlakeTemplate;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @NoNeedLogIn
    @PostMapping(value = "/api/admin/login")
    public R login(String username, String password) {
        // 1. find User by username
        Admin admin = adminService.lambdaQuery()
                .eq(Admin::getUsername, username.trim())
                .one();
        // 2. null ??
        if (null == admin) {
            return R.error(400, "用户不存在");
        }
        // 3. password match
        if ( !admin.getPassword().equals(DigestUtil.md5Hex(password)) ) {
            return R.error(404, "用户名或者密码错误");
        }
        String token = admin.getId() + "," + System.currentTimeMillis();
        HashMap<String,String> map = new HashMap<>();
        map.put("username",admin.getUsername());
        map.put("token",token);
        return R.okData("登录成功！",map);
    }

    /**
     * 获取用户信息
     * @return
     */
    @PostMapping(value = "/api/admin/info")
    public R getUserInfo(@RequestHeader("token") String token)  {
        Admin admin = adminService.getUserByToken(token);
        if (null == admin) {
            return R.error(400, "用户不存在");
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", admin.getUsername());
        return R.okData("登录成功！", map);
    }

    /**
     * 分页查询管理员信息
     * @param info info
     * @param pageNo pageNo
     * @param pageSize pageSize
     * @return
     */
    @PostMapping(value = "/api/admin/list")
    public R articleList(Admin info,
                         @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        Page<Admin> list = adminService.getAdminList(page, info);

        return R.okData("查询管理员列表成功", list.getRecords())
                .put("total", list.getTotal());
    }

    /**
     * 编辑
     * @param info
     * @return
     */
    @PostMapping(value = "/api/admin/edit")
    public R edit(Admin info){
        // 判断新增或者修改
        if (null == info.getId()) {
            info.setId(snowFlakeTemplate.getIdLong());
            info.setCreateTime(new Date());

            // 密码加密
            if (StrUtil.isBlank(info.getPassword())) {
                return R.error(400, "管理员密码不能为空!");
            }
            info.setPassword(DigestUtil.md5Hex(info.getPassword()));

            if (!adminService.save(info)) {
                return R.error(400, "新增管理员失败!");
            }
            return R.error(200, "新增管理员成功!");
        } else {
            Admin admin = adminService.getById(info.getId());
            if (null == admin) {
                return R.error(400, "管理员不存在，不能修改!");
            }

            info.setUpdateTime(new Date());
            // 判断密码是否为空
            if (!StrUtil.isBlank(info.getPassword())) {
                info.setPassword(DigestUtil.md5Hex(info.getPassword()));
            } else {
                // 设置为原来的密码
                info.setPassword(admin.getPassword());
            }
            if (!adminService.updateById(info)) {
                return R.error(400, "修改管理员失败!");
            }
            return R.error(200, "修改管理员成功!");
        }
    }

    /**
     * 删除
     * @param id id
     * @return
     */
    @PostMapping(value = "/api/admin/delete")
    public R delete(Long id){
        // 查询该记录是否存在
        Admin admin = adminService.lambdaQuery()
                .eq(Admin::getId, id)
                .one();
        if (null == admin) {
            return R.error(400,"管理员不存在，删除失败！");
        }
        // 删除
        if (!adminService.removeById(id)) {
            return R.error(400,"删除管理员失败！");
        }
        return R.error(200,"删除管理员成功！");
    }


}
