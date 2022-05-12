package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.ArticleComment;
import com.krystal.blog.common.model.vo.ArticleCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    @Select({" select ac.*, u.username userName, acp.content parentContent ",
            " from article_comment ac ",
            " left join user u on ac.user_id = u.id ",
            " left join article_comment acp on acp.id = ac.pid ",
            " where ac.article_id = #{articleId} ",
            " and (ac.pid = '' or ac.pid is null) "})
    Page<ArticleCommentVo> getFirstLevelList(Page<ArticleCommentVo> page,
                                             @Param("articleId") Long articleId);

    @Select({" select ac.*, u.username userName, acp.content parentContent ",
            " from article_comment ac ",
            " left join user u on ac.user_id = u.id ",
            " left join article_comment acp on acp.id = ac.pid ",
            " where ac.article_id = #{articleId} ",
            " and ac.pid != '' and ac.pid is not null "})
    Page<ArticleCommentVo> getSecondLevelList(Page<ArticleCommentVo> page,
                                              @Param("articleId") Long articleId,
                                              @Param("pid") Long pid);

    
    Page<ArticleCommentVo> getArticleCommentList(Page<ArticleCommentVo> page, Article info);
}
