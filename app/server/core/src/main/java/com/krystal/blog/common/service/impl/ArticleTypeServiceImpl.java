package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleTypeMapper;
import com.krystal.blog.common.model.ArticleType;
import com.krystal.blog.common.model.vo.ArticleTypeVo;
import com.krystal.blog.common.service.ArticleTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements ArticleTypeService {
    @Resource
    private ArticleTypeMapper articleTypeMapper;

    @Override
    public Page<ArticleType> getArticleTypeList(Page<ArticleType> page) {
        return this.lambdaQuery()
                .page(page);
    }

    @Override
    public ArticleTypeVo getArticleType(Long id) {
        return articleTypeMapper.getArticleType(id);
    }

    @Override
    public Page<ArticleTypeVo> getArticleTypeList(Page<ArticleTypeVo> page, ArticleType info) {
        return articleTypeMapper.getArticleTypeList(page, info);
    }
}
