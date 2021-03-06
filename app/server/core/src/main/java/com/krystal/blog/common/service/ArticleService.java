package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;

public interface ArticleService extends IService<Article> {

    Page<ArticleVo> selectArticleListPublic(Page<ArticleVo> page, Long loginUserId, String title, Long typeId, String orderFlag);

    Page<ArticleVo> selectArticleListPersonal(Page<ArticleVo> page, Long loginUserId, Long userId, Integer status);

    Page<ArticleVo> selectArticleListUserFocus(Page<ArticleVo> page, Long loginUserId, Long userId, String title);

    Page<ArticleVo> selectCollectArticle(Page<ArticleVo> page, Long loginUserId, Long userId);

    /**
     * 发布文章
     * @param info
     * @return
     */
    boolean publish(Article info);

    ArticleVo selectArticle(Long id, Long userId);


    Page<ArticleVo> getArticleList(Page<ArticleVo> page, ArticleVo info);
}
