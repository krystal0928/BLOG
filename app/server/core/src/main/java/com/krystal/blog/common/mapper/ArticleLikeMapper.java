package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.ArticleLike;
import com.krystal.blog.common.model.vo.ArticleLikeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {
    // 分页查询
    Page<ArticleLikeVo> getArticleLikeList(Page<ArticleLikeVo> page,
                                           @Param("info") ArticleLikeVo info);
}
