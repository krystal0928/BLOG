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

    @Select({" select a.*, u.username userName,  ",
            " ifnull(count(distinct al.id), 0) likeCount, ifnull(count(distinct ac.id), 0) commentCount, ifnull(count(distinct acl.id), 0) collectCount, ",
            " (select count(id) from article_like where article_id = a.id and user_id = #{loginUserId}) as liked, ",
            " (select count(id) from article_collection where article_id = a.id and user_id = #{loginUserId}) as collected ",
            " from article a left join user u on u.id = a.user_id ",
            " left join article_like al on al.article_id = a.id ",
            " left join article_comment ac on ac.article_id = a.id ",
            " left join article_collection acl on acl.article_id = a.id ",
            " where a.status = 1 and a.deleted = 0 and acl.user_id =#{userId} ",
            " GROUP BY a.id ",
            " order by a.create_time desc "})
    Page<ArticleVo> selectCollectArticle(Page<ArticleVo> page,
                                         @Param("loginUserId") Long loginUserId,
                                         @Param("userId") Long userId);


    @Select({" select a.*, u.username userName,  ",
            " ifnull(count(distinct al.id), 0) likeCount, ifnull(count(distinct ac.id), 0) commentCount, ifnull(count(distinct acl.id), 0) collectCount, ",
            " (select count(id) from article_like where article_id = a.id and user_id = #{loginUserId}) as liked, ",
            " (select count(id) from article_collection where article_id = a.id and user_id = #{loginUserId}) as collected ",
            " from article a left join user u on u.id = a.user_id ",
            " left join article_like al on al.article_id = a.id ",
            " left join article_comment ac on ac.article_id = a.id ",
            " left join article_collection acl on acl.article_id = a.id ",
            " where (a.status = #{status} or a.status = 2) and a.deleted = 0 and a.user_id =#{userId} ",
            " GROUP BY a.id ",
            " order by a.create_time desc "})
    Page<ArticleVo> selectArticleListPersonal(Page<ArticleVo> page,
                                              @Param("loginUserId") Long loginUserId,
                                              @Param("userId") Long userId,
                                              @Param("status") Integer status);


    @Select({"select a.id id, a.title title, a.filepath filepath, a.create_time createTime,a.permission,u.username userName, u.id userId, " ,
            " ifnull(count(distinct al.id), 0) likeCount, ifnull(count(distinct ac.id), 0) commentCount, ifnull(count(distinct acl.id), 0) collectCount, ",
            " (select count(id) from article_like where article_id = a.id and user_id = #{userId}) as liked, ",
            " (select count(id) from article_collection where article_id = a.id and user_id = #{userId}) as collected ",
            " from article a left join user u on u.id = a.user_id ",
            " left join article_like al on al.article_id = a.id ",
            " left join article_comment ac on ac.article_id = a.id ",
            " left join article_collection acl on acl.article_id = a.id ",
            " where a.id = #{id} and a.status = 1 and a.deleted = 0 ",
            " GROUP BY a.id "})
    ArticleVo selectArticle(@Param("id")Long id, @Param("userId") Long userId);

    @Select("select * from article")
    Page<ArticleVo> getArticleList(Page<ArticleVo> page, Article info);
}
