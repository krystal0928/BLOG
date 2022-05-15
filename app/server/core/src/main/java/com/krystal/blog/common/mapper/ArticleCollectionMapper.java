package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.vo.ArticleCollectionVo;
import com.krystal.blog.common.model.vo.ArticleLikeVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleCollectionMapper extends BaseMapper<ArticleCollection> {
    // 分页查询
    Page<ArticleLikeVo> getArticleCollectionList(Page<ArticleLikeVo> page, ArticleCollectionVo info);
}
