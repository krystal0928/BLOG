package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleCollectionMapper;
import com.krystal.blog.common.mapper.ArticleCommentMapper;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.ArticleComment;
import com.krystal.blog.common.service.ArticleCollectionService;
import com.krystal.blog.common.service.ArticleCommentService;
import org.springframework.stereotype.Service;

@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper,ArticleComment> implements ArticleCommentService {
}
