package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleCollectionMapper;
import com.krystal.blog.common.mapper.ArticleLikeMapper;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.ArticleLike;
import com.krystal.blog.common.service.ArticleCollectionService;
import com.krystal.blog.common.service.ArticleLikeService;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper,ArticleLike> implements ArticleLikeService {
}
