package com.krystal.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.model.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
