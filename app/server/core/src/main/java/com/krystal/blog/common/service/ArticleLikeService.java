package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.ArticleLike;
import com.krystal.blog.common.model.vo.ArticleLikeVo;


public interface ArticleLikeService extends IService<ArticleLike> {
    // 分页查询
    Page<ArticleLikeVo> getArticleLikeList(Page<ArticleLikeVo> page, ArticleLikeVo info);
}
