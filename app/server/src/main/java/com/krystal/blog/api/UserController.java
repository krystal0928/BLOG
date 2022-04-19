package com.krystal.blog.api;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.krystal.blog.common.EmailUtil;
import com.krystal.blog.common.R;
import com.krystal.blog.common.SnowFlakeConfig;
import com.krystal.blog.common.SnowFlakeTemplate;
import com.krystal.blog.model.User;
import com.krystal.blog.service.UserService;
import com.krystal.blog.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;

// 接口
@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private SnowFlakeTemplate snowFlakeTemplate;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/api/user/login")
    public R login(String username, String password) {
        // 1. find User by username
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
        // 2. null ??
        if (null == user) {
            return R.error(400, "用户不存在");
        }
        // 3. password match
        if (!user.getPassword().equals(password)) {
            return R.error(404, "用户名或者密码错误");
        }

        return R.ok("登录成功！");
    }

    /**
     * 注册
     * @param info
     * @return
     */
    @PostMapping(value="/api/user/register")
    public R register(User info){
        User user = userService.lambdaQuery()
                .eq(User::getUsername,info.getUsername())
                .one();

        if (null != user)
            return R.error(400,"用户已存在，请修改用户名");

        user = userService.lambdaQuery()
                .eq(User::getEmail,info.getEmail())
                .one();

        if (null != user)
            return R.error(400,"该邮箱已绑定其他用户，请修改邮箱");

        info.setId(snowFlakeTemplate.getIdLong());

        if (!userService.save(info))
            return R.error(400,"用户创建失败");

        return R.ok("用户创建成功！");
    }

    /**
     * 修改密码
     * @param email
     * @param password
     * @return
     */
    @PostMapping(value="/api/user/change")
    public R change(String email, String password){
        User user = userService.lambdaQuery()
                .eq(User::getEmail,email)
                .one();

        if (null == user)
            return R.error(400,"该用户不存在！");

        if (user.getPassword().equals(password))
            return R.error(400,"与先前密码一致，修改失败！");

        if (!userService.lambdaUpdate()
                .set(User::getPassword,password)
                .eq(User::getEmail,email)
                .update())
            return R.error(400,"密码修改失败！");

        return R.ok("密码修改成功！");
    }

    /**
     * 发送注册验证码
     * @param email
     * @return
     */
    @PostMapping(value="/api/user/sendRegisterEmail")
    public R sendRegisterEmail(String email) {
        User user = userService.lambdaQuery()
                .eq(User::getEmail,email)
                .one();
        if (null != user)
            return R.error(400,"该用户已存在！");
        String code = RandomUtil.randomStringUpper(6);
        RBucket<String> bucket = redissonClient.getBucket(email);
        bucket.set(code);
        bucket.expire(Duration.ofMinutes(1L));
        EmailUtil.sendNormalEmail(email,"验证码", code);
        return R.ok("验证码已发送！");
    }

    @PostMapping(value="/api/user/sendForgetEmail")
    public R sendForgetEmail(String email) {
        User user = userService.lambdaQuery()
                .eq(User::getEmail,email)
                .one();
        if (null == user)
            return R.error(400,"该用户不存在！");
        String code = RandomUtil.randomStringUpper(6);
        RBucket<String> bucket = redissonClient.getBucket(email);
        bucket.set(code);
        bucket.expire(Duration.ofMinutes(1L));
        EmailUtil.sendNormalEmail(email,"验证码", code);
        return R.ok("验证码已发送！");
    }


    @PostMapping(value="/api/user/confirmEmail")
    public R confirmEmail(String email, String value) {
        Assert.notNull(value, "验证码不能为空！");
        RBucket<String> bucket = redissonClient.getBucket(email);
        if (null == bucket.get())
            return R.error(400,"验证码已过期");
        if (!value.toUpperCase().equals(bucket.get().toUpperCase()))
            return R.error(400,"校验失败！");
        return R.ok("校验成功！");
    }


}
