package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleCommentVo;
import com.krystal.blog.common.model.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    Page<ArticleVo> selectArticleListPublic(Page<ArticleVo> page,
                                            @Param("loginUserId") Long loginUserId,
                                            @Param("title") String title,
                                            @Param("orderFlag") String orderFlag);


    Page<ArticleVo> selectArticleListUserFocus(Page<ArticleVo> page,
                                               @Param("loginUserId") Long loginUserId,
                                               @Param("userId") Long userId,
                                               @Param("title") String title);

    Page<ArticleVo> selectCollectArticle(Page<ArticleVo> page,
                                         @Param("loginUserId") Long loginUserId,
                                         @Param("userId") Long userId);


    Page<ArticleVo> selectArticleListPersonal(Page<ArticleVo> page,
                                              @Param("loginUserId") Long loginUserId,
                                              @Param("userId") Long userId,
                                              @Param("status") Integer status);

    ArticleVo selectArticle(@Param("id")Long id, @Param("userId") Long userId);

    Page<ArticleVo> getArticleList(Page<ArticleVo> page,
                                   @Param("info") ArticleVo info);
}
