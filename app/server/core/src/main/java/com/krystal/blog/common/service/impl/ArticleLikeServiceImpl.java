package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleLikeMapper;
import com.krystal.blog.common.model.ArticleLike;
import com.krystal.blog.common.model.vo.ArticleLikeVo;
import com.krystal.blog.common.service.ArticleLikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper,ArticleLike> implements ArticleLikeService {

    @Resource
    private ArticleLikeMapper articleLikeMapper;

    @Override
    public Page<ArticleLikeVo> getArticleLikeList(Page<ArticleLikeVo> page, ArticleLikeVo info) {
        return articleLikeMapper.getArticleLikeList(page, info);
    }
}
