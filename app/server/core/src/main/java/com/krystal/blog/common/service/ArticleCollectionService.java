package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.vo.ArticleCollectionVo;
import com.krystal.blog.common.model.vo.ArticleLikeVo;


public interface ArticleCollectionService extends IService<ArticleCollection> {
    // 分页查询
    Page<ArticleLikeVo> getArticleCollectionList(Page<ArticleLikeVo> page, ArticleCollectionVo info);
}
