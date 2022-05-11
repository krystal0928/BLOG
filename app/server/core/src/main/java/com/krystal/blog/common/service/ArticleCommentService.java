package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.ArticleComment;
import com.krystal.blog.common.model.vo.ArticleCommentVo;


public interface ArticleCommentService extends IService<ArticleComment> {

    /**
     * 一级评论
     * @param page
     * @param articleId
     * @return
     */
    Page<ArticleCommentVo> getFirstLevelList(Page<ArticleCommentVo> page, Long articleId);

    /**
     * 二级评论
     * @param page
     * @param articleId
     * @param pid
     * @return
     */
    Page<ArticleCommentVo> getSecondLevelList(Page<ArticleCommentVo> page, Long articleId, Long pid);


    Page<ArticleCommentVo> getArticleCommentList(Page<ArticleCommentVo> page, Article info);
}
