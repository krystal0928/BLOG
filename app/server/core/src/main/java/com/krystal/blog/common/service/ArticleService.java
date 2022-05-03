package com.krystal.blog.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;

import java.util.List;

public interface ArticleService extends IService<Article> {

    List<ArticleVo> selectArticleList(Long userId);

}
