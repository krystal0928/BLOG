package com.krystal.blog.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.model.vo.UserVo;
import com.krystal.blog.common.service.ArticleService;
import com.krystal.blog.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

// 接口
@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 分页查询文章
     * @param info
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/api/user/list")
    public R userList(User info,
                      @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                      @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<>(pageNo, pageSize);
        Page<User> list = userService.getUserList(page, info);

        return R.okData("用户信息查询成功", list.getRecords())
                .put("total", list.getTotal());
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping(value = "/api/user/delete")
    public R deleteArticleLike(Long id){
        if(!userService.removeById(id))
            return R.error(400,"用户删除失败！");
        return R.ok("用户删除成功！");
    }

    /**
     * 关闭用户二次验证
     * @param id
     * @return
     */
    @PostMapping(value = "/api/user/unbindTFA")
    public R unbindTFA(Long id){
        boolean flag = userService.lambdaUpdate()
                .set(User::getSecret, null)
                .set(User::getStatus, 0)
                .eq(User::getId, id)
                .update();
        if(!flag)
            return R.error(400,"关闭二次验证失败！");
        return R.ok("关闭二次验证成功！");
    }
}
