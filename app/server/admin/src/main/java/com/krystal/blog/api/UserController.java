package com.krystal.blog.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.ArticleVo;
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
        Page<ArticleVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleVo> list = userService.getUserList(page, info);

        return R.okData("用户信息查询成功", list.getRecords())
                .put("total", list.getTotal());
    }




}
