package com.krystal.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.mapper.ArticleMapper;
import com.krystal.blog.mapper.UserMapper;
import com.krystal.blog.model.Article;
import com.krystal.blog.model.User;
import com.krystal.blog.service.ArticleService;
import com.krystal.blog.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

}
