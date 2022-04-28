package com.krystal.blog.api;

import cn.hutool.core.util.StrUtil;
import com.krystal.blog.common.R;
import com.krystal.blog.common.SnowFlakeTemplate;
import com.krystal.blog.model.Article;
import com.krystal.blog.service.ArticleService;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private SnowFlakeTemplate snowFlakeTemplate;


    /**
     * 草稿保存
     * @param info
     * @return
     */
    @PostMapping("/api/article/saveDraft")
    public R saveDraft (Article info) {
        if (StrUtil.isBlank(info.getTitle()))
            return R.error(400,"标题不能为空！");
        info.setId(snowFlakeTemplate.getIdLong());
        info.setStatus(0);
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
