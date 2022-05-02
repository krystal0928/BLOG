package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleCollectionMapper;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.service.ArticleCollectionService;
import org.springframework.stereotype.Service;

@Service
public class ArticleCollectionServiceImpl extends ServiceImpl<ArticleCollectionMapper,ArticleCollection> implements ArticleCollectionService {
}
