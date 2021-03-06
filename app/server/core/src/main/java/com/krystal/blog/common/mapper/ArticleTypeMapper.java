package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.model.Admin;
import com.krystal.blog.common.model.ArticleType;
import com.krystal.blog.common.model.vo.ArticleTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleTypeMapper extends BaseMapper<ArticleType> {

    // 查询文章分类信息
    ArticleTypeVo getArticleType(@Param("id") Long id);

    //  分页查询
    Page<ArticleTypeVo> getArticleTypeList(Page<ArticleTypeVo> page,
                                           @Param("info") ArticleType info);
}
