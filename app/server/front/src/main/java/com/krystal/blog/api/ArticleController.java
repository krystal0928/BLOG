package com.krystal.blog.api;

import cn.hutool.core.util.StrUtil;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.enums.ArticleStatusEnum;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.service.ArticleService;
import com.krystal.blog.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;
    @Resource
    private SnowFlakeTemplate snowFlakeTemplate;


    /**
     * 草稿保存
     * @param info
     * @return
     */
    @PostMapping("/api/article/saveDraft")
    public R saveDraft (@RequestHeader("token") String token, Article info) {
        Assert.notNull(info.getTitle(),"标题不能为空！");
        Assert.notNull(info.getContent(),"内容不能为空！");
        Assert.notNull(info.getDescription(),"文章摘要不能为空！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        info.setUserId(user.getId());
        info.setId(snowFlakeTemplate.getIdLong());
        info.setStatus(ArticleStatusEnum.STATUS0.getCode());
        if (!articleService.save(info))
            return R.error(400,"文章保存失败！");
        return R.ok("文章已保存至草稿箱！");
    }

    /**
     * 文章保存
     * @param info
     * @return
     */
    @PostMapping("/api/article/publishArticle")
    public R publishArticle (Article info) {
        if (StrUtil.isBlank(info.getTitle()))
            return R.error(400,"标题不能为空！");
        info.setId(snowFlakeTemplate.getIdLong());
        info.setStatus(1);
        if (!articleService.save(info))
            return R.error(400,"文章发布失败！");
        return R.ok("文章已发布！");
    }


}
