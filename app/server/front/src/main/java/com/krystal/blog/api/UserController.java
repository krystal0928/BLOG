package com.krystal.blog.api;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.service.EmailService;
import com.krystal.blog.common.util.TwoFactorAuthUtil;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @Resource
    private EmailService emailService;


    @PostMapping(value = "/api/user/loginCheck")
    public R loginCheck(String username) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username.trim())
                .one();
        if (null == user)
            return R.error(400, "用户不存在");
        return R.okData("success",user.getStatus());
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/api/user/login")
    public R login(String username, String password, String code) {
        // 1. find User by username
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username.trim())
                .one();
        // 2. null ??
        if (null == user) {
            return R.error(400, "用户不存在");
        }
        // 3. password match
        if ( !user.getPassword().equals(DigestUtil.md5Hex(password)) ) {
            return R.error(404, "用户名或者密码错误");
        }
        if (StrUtil.isBlank(code) && user.getStatus()==1)
            return R.error(400,"验证码不能为空");
        if (!StrUtil.isBlank(code)) {
            if (!TwoFactorAuthUtil.validateTFACode(user.getSecret(), code)) {
                return R.error(400, "验证码错误，登录失败！");
            }
        }
        String token = user.getId() + "," + System.currentTimeMillis();
        HashMap<String,String> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("token",token);
        map.put("email",user.getEmail());
        return R.okData("登录成功！",map);
    }

    /**
     * 注册
     * @param info
     * @return
     */
    @PostMapping(value="/api/user/register")
    public R register(User info){
        User user = userService.lambdaQuery()
                .eq(User::getUsername,info.getUsername().trim())
                .one();

        if (null != user)
            return R.error(400,"用户已存在，请修改用户名");

        user = userService.lambdaQuery()
                .eq(User::getEmail,info.getEmail().trim())
                .one();

        if (null != user)
            return R.error(400,"该邮箱已绑定其他用户，请修改邮箱");

        info.setPassword(DigestUtil.md5Hex(info.getPassword()));
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
                .eq(User::getEmail,email.trim())
                .one();

        if (null == user)
            return R.error(400,"该用户不存在！");

        if (user.getPassword().equals(password))
            return R.error(400,"与先前密码一致，修改失败！");

        if (!userService.lambdaUpdate()
                .set(User::getPassword,DigestUtil.md5Hex(password))
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
                .eq(User::getEmail,email.trim())
                .one();
        if (null != user)
            return R.error(400,"该用户已存在！");
        String code = RandomUtil.randomStringUpper(6);
        RBucket<String> bucket = redissonClient.getBucket(email);
        bucket.set(code);
        bucket.expire(Duration.ofMinutes(1L));
        emailService.sendCodeEmail(email,"验证码", code);
        return R.ok("验证码已发送！");
    }


    /**
     * 忘记密码的验证码
     * @param email
     * @return
     */
    @PostMapping(value="/api/user/sendForgetEmail")
    public R sendForgetEmail(String email) {
        User user = userService.lambdaQuery()
                .eq(User::getEmail,email.trim())
                .one();
        if (null == user)
            return R.error(400,"该用户不存在！");
        String code = RandomUtil.randomStringUpper(6);
        RBucket<String> bucket = redissonClient.getBucket(email);
        bucket.set(code);
        bucket.expire(Duration.ofMinutes(1L));
        emailService.sendCodeEmail(email,"验证码", code);
        return R.ok("验证码已发送！");
    }

    /**
     * 校对验证码
     * @param email
     * @param value
     * @return
     */
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

    /**
     * 二次验证绑定 校验密码
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value="/api/user/generateCode")
    public R generateCode(String username, String password) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername,username.trim())
                .one();
        if (null == user)
            return R.error(400,"用户不存在");
        if (!user.getPassword().equals(DigestUtil.md5Hex(password)))
            return R.error(400,"密码错误，请重新输入！");
        String secret = TwoFactorAuthUtil.generateTFAKey();
        String key = String.format("%s_%s",user.getId(),user.getEmail());
        String url = TwoFactorAuthUtil.generateOtpAuthUrl(key, secret);
        return R.ok("密码正确，请扫描二维码进行绑定！")
                .put("secret",secret)
                .put("url", url);
    }


    /**
     * 二次验证绑定用户
     * @param username
     * @param secret
     * @param code
     * @return
     */
    @PostMapping(value="/api/user/bindTFA")
    public R bindTFA(String username, String secret, String code){
        User user = userService.lambdaQuery()
                .eq(User::getUsername,username.trim())
                .one();
        if (null == user)
            return R.error(400,"用户不存在");
        if (!TwoFactorAuthUtil.validateTFACode(secret, code))
            return R.error(400,"验证码错误，绑定失败！");
        user.setSecret(secret);
        user.setStatus(1);
        if  (!userService.updateById(user))
            return R.error(400,"绑定失败，请重试！");
        return R.ok("绑定成功");
    }


    /**
     * 二次验证确认用户状态
     * @param token
     * @return
     */
    @PostMapping(value="/api/user/checkUserStatus")
    public R checkUserStatus(@RequestHeader("token") String token) {
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"用户不存在");
        return R.okData("success",user.getStatus());
    }


}
