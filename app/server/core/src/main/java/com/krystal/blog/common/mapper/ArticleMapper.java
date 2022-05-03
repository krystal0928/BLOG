package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select({" select a.*, u.username userName, ",
            " ifnull(count(al.id), 0) likeCount, ifnull(count(ac.id), 0) commentCount, ifnull(count(acl.id), 0) collectCount, ",
            " (select count(id) from article_like where article_id = a.id and user_id = #{userId}) as liked, ",
            " (select count(id) from article_collection where article_id = a.id and user_id = #{userId}) as collected ",
            " from article a left join user u on u.id = a.user_id ",
            " left join article_like al on al.article_id = a.id ",
            " left join article_comment ac on ac.article_id = a.id ",
            " left join article_collection acl on acl.article_id = a.id ",
            " GROUP BY a.id "})
    List<ArticleVo> selectArticleList(@Param("userId") Long userId);


}
