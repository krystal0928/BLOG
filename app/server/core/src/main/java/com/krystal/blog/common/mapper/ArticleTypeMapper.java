package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.common.model.Admin;
import com.krystal.blog.common.model.ArticleType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTypeMapper extends BaseMapper<ArticleType> {


}
