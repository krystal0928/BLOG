package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.mapper.ArticleMapper;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

}
