package com.krystal.blog.api;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.annotation.NoNeedLogIn;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.model.ArticleLike;
import com.krystal.blog.common.model.UserFocus;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.model.vo.UserVo;
import com.krystal.blog.common.service.EmailService;
import com.krystal.blog.common.service.UserFocusService;
import com.krystal.blog.common.util.TwoFactorAuthUtil;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.service.UserService;
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
import java.util.List;

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
    @Resource
    private UserFocusService userFocusService;


    @NoNeedLogIn
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
     * 获取用户信息
     * @return
     */
    @PostMapping(value = "/api/user/getUserInfo")
    public R getUserInfo(@RequestHeader("token") String token)  {
        User user = userService.getUserByToken(token);
        if (null == user) {
            return R.error(400, "用户不存在");
        }
        return R.okData("登录成功！",user);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @NoNeedLogIn
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
        map.put("img",user.getImg());
        return R.okData("登录成功！",map);
    }

    /**
     * 注册
     * @param info
     * @return
     */
    @NoNeedLogIn
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


    //修改用户信息
    @PostMapping(value="/api/user/updateInfo")
    public R updateInfo(@RequestHeader("token") String token,User userInfo){
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        if (!user.getId().equals(userInfo.getId()))
            return R.error(400,"不可修改其他用户信息！");

        if (!userService.lambdaUpdate()
                .set(User::getMotto,userInfo.getMotto())
                .set(User::getImg,userInfo.getImg())
                .eq(User::getId,user.getId())
                .update())
            return R.error(400,"用户信息修改失败！");

        return R.ok("用户信息修改成功！");
    }
    /**
     * 发送注册验证码
     * @param email
     * @return
     */
    @NoNeedLogIn
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
    @NoNeedLogIn
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
    @NoNeedLogIn
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

    /**
     * 关注用户
     * @param token
     * @param focusId
     * @return
     */
    @PostMapping(value="/api/user/addUserFocus")
    public R addUserFocus(@RequestHeader("token") String token, Long focusId) {
        User user = userService.getUserByToken(token);
        User focusUser = userService.lambdaQuery()
                .eq(User::getId,focusId)
                .one();
        if (null == user)
            return R.error(400,"用户不存在");
        if (null == focusUser)
            return R.error(400,"关注的用户已不存在！");
        if (user.getId() == focusId)
            return R.error(400,"不支持该操作！");
        UserFocus userFocus = userFocusService.lambdaQuery()
                .eq(UserFocus::getUserId,user.getId())
                .eq(UserFocus::getFocusId,focusId)
                .one();
        if (null != userFocus)
            return R.error(400,"您已关注该用户，请勿重复操作！");
        userFocus = UserFocus.builder()
                .id(snowFlakeTemplate.getIdLong())
                .userId(user.getId())
                .focusId(focusId)
                .build();
        if (!userFocusService.save(userFocus)) {
            return R.error(400,"关注失败，请重新尝试！");
        }
        emailService.sendActionEmail(user.getUsername(), focusUser.getUsername(),"关注了",focusUser.getEmail());
        return R.ok("关注成功！");
    }

    /**
     * 取消关注
     * @param token
     * @param focusId
     * @return
     */
    @PostMapping(value="/api/user/deleteUserFocus")
    public R deleteUserFocus(@RequestHeader("token") String token, Long focusId) {
        User user = userService.getUserByToken(token);
        User focusUser = userService.lambdaQuery()
                .eq(User::getId,focusId)
                .one();
        if (null == user)
            return R.error(400,"用户不存在");
        if (null == focusUser)
            return R.error(400,"关注的用户已不存在！");
        if (user.getId() == focusId)
            return R.error(400,"不支持该操作！");

        UserFocus userFocus = userFocusService.lambdaQuery()
                .eq(UserFocus::getUserId,user.getId())
                .eq(UserFocus::getFocusId,focusId)
                .one();
        if (null == userFocus)
            return R.error(400,"您未关注该用户，取关操作不允许！");
        if (!userFocusService.removeById(userFocus.getId())) {
            return R.error(400,"取消关注失败，请重新尝试！");
        }
        return R.ok("取关成功！");
    }

    /**
     * 根据用户id和文章作者id获取用户信息
     * @param userId
     * @param focusId
     * @return
     */
    @NoNeedLogIn
    @PostMapping(value="/api/user/getUserVoById")
    public R getUserVoById(@RequestParam(value = "userId", defaultValue = "0") Long userId,
                           Long focusId) {
        User focusUser = userService.lambdaQuery()
                .eq(User::getId, focusId)
                .one();
        if (null == focusUser)
            return R.error(400,"关注的用户已不存在！");

        UserVo userVo = userService.selectUser(userId, focusId);

        return R.okData("用户查询成功!",userVo);
    }

    /**
     * 关注列表
     * @param loginUserId
     * @param userId
     * @return
     */
    @NoNeedLogIn
    @PostMapping(value="/api/user/getFocusUserList")
    public R getFocusUserList(@RequestParam(value = "loginUserId", defaultValue = "0") Long loginUserId,
                               Long userId,
                               @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        User user = userService.lambdaQuery()
                .eq(User::getId, userId)
                .one();
        if (null == user)
            return R.error(400,"用户已不存在！");

        Page<UserVo> page = new Page<>(pageNo, pageSize);
        Page<UserVo> userVo = userService.selectFocusUserList(page, user.getId(), loginUserId);

        return R.okData("关注列表查询成功!",userVo.getRecords())
                .put("total", userVo.getTotal());
    }

    /**
     * 粉丝列表
     * @param loginUserId
     * @param userId
     * @return
     */
    @NoNeedLogIn
    @PostMapping(value="/api/user/getFansUserList")
    public R getFansUserList(@RequestParam(value = "loginUserId", defaultValue = "0") Long loginUserId,
                              Long userId,
                              @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                              @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        User user = userService.lambdaQuery()
                .eq(User::getId, userId)
                .one();
        if (null == user)
            return R.error(400,"用户已不存在！");

        Page<UserVo> page = new Page<>(pageNo, pageSize);
        Page<UserVo> userVo = userService.selectFansUserList(page, user.getId(), loginUserId);

        return R.okData("粉丝列表查询成功!",userVo.getRecords())
                .put("total", userVo.getTotal());
    }

}
