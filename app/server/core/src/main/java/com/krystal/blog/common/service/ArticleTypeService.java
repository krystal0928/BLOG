package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.ArticleType;
import com.krystal.blog.common.model.vo.ArticleTypeVo;

public interface ArticleTypeService extends IService<ArticleType> {
    // 查询文章分类列表
    Page<ArticleType> getArticleTypeList(Page<ArticleType> page);

    // 查询文章分类信息
    ArticleTypeVo getArticleType(Long id);
}
