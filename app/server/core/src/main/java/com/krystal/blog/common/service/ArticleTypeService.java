package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.ArticleType;

public interface ArticleTypeService extends IService<ArticleType> {
    // 查询文章分类
    Page<ArticleType> getArticleTypeList(Page<ArticleType> page);
}
