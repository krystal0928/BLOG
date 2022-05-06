package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select({" select a.*, u.username userName,  ",
            " ifnull(count(al.id), 0) likeCount, ifnull(count(ac.id), 0) commentCount, ifnull(count(acl.id), 0) collectCount, ",
            " (select count(id) from article_like where article_id = a.id and user_id = #{userId}) as liked, ",
            " (select count(id) from article_collection where article_id = a.id and user_id = #{userId}) as collected ",
            " from article a left join user u on u.id = a.user_id ",
            " left join article_like al on al.article_id = a.id ",
            " left join article_comment ac on ac.article_id = a.id ",
            " left join article_collection acl on acl.article_id = a.id ",
            " where a.status = 1 ",
            " GROUP BY a.id ",
            " order by a.create_time desc "})
    Page<ArticleVo> selectArticleListPublic(Page<ArticleVo> page, @Param("userId") Long userId);


    @Select({" select a.*, u.username userName,  ",
            " ifnull(count(al.id), 0) likeCount, ifnull(count(ac.id), 0) commentCount, ifnull(count(acl.id), 0) collectCount, ",
            " (select count(id) from article_like where article_id = a.id and user_id = #{userId}) as liked, ",
            " (select count(id) from article_collection where article_id = a.id and user_id = #{userId}) as collected ",
            " from article a left join user u on u.id = a.user_id ",
            " left join article_like al on al.article_id = a.id ",
            " left join article_comment ac on ac.article_id = a.id ",
            " left join article_collection acl on acl.article_id = a.id ",
            " where a.status = 1 and a.user_id =#{userId} ",
            " GROUP BY a.id ",
            " order by a.create_time desc "})
    Page<ArticleVo> selectArticleListPersonal(Page<ArticleVo> page, @Param("userId") Long userId);


    @Select({"select a.id id, a.title title, a.filepath filepath, a.create_time createTime,u.username userName, " ,
            " ifnull(count(al.id), 0) likeCount, ifnull(count(ac.id), 0) commentCount, ifnull(count(acl.id), 0) collectCount, ",
            " (select count(id) from article_like where article_id = a.id and user_id = #{userId}) as liked, ",
            " (select count(id) from article_collection where article_id = a.id and user_id = #{userId}) as collected ",
            " from article a left join user u on u.id = a.user_id ",
            " left join article_like al on al.article_id = a.id ",
            " left join article_comment ac on ac.article_id = a.id ",
            " left join article_collection acl on acl.article_id = a.id ",
            " where a.id = #{id} ",
            " GROUP BY a.id "})
    ArticleVo selectArticle(@Param("id")Long id, @Param("userId") Long userId);

}
