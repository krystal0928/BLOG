package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;

import java.util.List;

public interface ArticleService extends IService<Article> {

    Page<ArticleVo> selectArticleList(Page<ArticleVo> page, Long userId);

    /**
     * 发布文章
     * @param info
     * @return
     */
    boolean publish(Article info);

    ArticleVo selectArticle(Long id, Long userId);

}
