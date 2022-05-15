package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleCollectionMapper;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.vo.ArticleCollectionVo;
import com.krystal.blog.common.model.vo.ArticleLikeVo;
import com.krystal.blog.common.service.ArticleCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleCollectionServiceImpl extends ServiceImpl<ArticleCollectionMapper,ArticleCollection> implements ArticleCollectionService {

    @Resource
    private ArticleCollectionMapper articleCollectionMapper;

    @Override
    public Page<ArticleLikeVo> getArticleCollectionList(Page<ArticleLikeVo> page, ArticleCollectionVo info) {
        return articleCollectionMapper.getArticleCollectionList(page, info);
    }
}
