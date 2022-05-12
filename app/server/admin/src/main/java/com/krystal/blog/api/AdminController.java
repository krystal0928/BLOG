package com.krystal.blog.api;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.annotation.NoNeedLogIn;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.model.Admin;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.UserFocus;
import com.krystal.blog.common.model.vo.UserVo;
import com.krystal.blog.common.service.AdminService;
import com.krystal.blog.common.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// 接口
@RestController
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;

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
//        if (StrUtil.isBlank(code) && admin.getStatus()==1)
//            return R.error(400,"验证码不能为空");
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

}
