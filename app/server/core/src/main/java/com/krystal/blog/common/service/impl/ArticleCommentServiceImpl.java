package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleCommentMapper;
import com.krystal.blog.common.model.ArticleComment;
import com.krystal.blog.common.model.vo.ArticleCommentVo;
import com.krystal.blog.common.service.ArticleCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper,ArticleComment> implements ArticleCommentService {

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public Page<ArticleCommentVo> getFirstLevelList(Page<ArticleCommentVo> page, Long articleId) {
        return articleCommentMapper.getFirstLevelList(page, articleId);
    }

    @Override
    public Page<ArticleCommentVo> getSecondLevelList(Page<ArticleCommentVo> page, Long articleId, Long pid) {
        return articleCommentMapper.getSecondLevelList(page, articleId, pid);
    }
}
