package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.common.model.ArticleCollection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleCollectionMapper extends BaseMapper<ArticleCollection> {
}
