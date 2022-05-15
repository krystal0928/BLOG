package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.model.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select({" select u.*, ",
            " (select count(id) from user_focus where user_id = #{focusId}) focusCount, ",
            " (select count(ac.id) from article_collection ac where ac.article_id  in(select id from article where user_id = #{focusId})) collectCount, ",
            " (select count(al.id) from article_like al where al.article_id in (select id from article where user_id = #{focusId})) likeCount, ",
            " (select count(id) from user_focus where focus_id = #{focusId}) fansCount, ",
            " (select count(id) from user_focus where user_id = #{userId} and focus_id = #{focusId}) as focused ",
            " from user u ",
            " left join user_focus f on u.id = f.focus_id ",
            " where u.id = #{focusId} limit 1 "})
    UserVo selectUser(@Param("userId") Long userId, @Param("focusId") Long focusId);

    @Select({" select u.id, u.username, u.motto, u.img , " ,
            "  (select count(id) from user_focus where user_id = #{loginUserId} and focus_id = u.id) as focused " ,
            " FROM user u " ,
            " where u.id in (select focus_id from user_focus where user_id = #{userId} ) " ,
            " order by u.create_time desc "})
    Page<UserVo> selectFocusUserList(Page<UserVo> page, @Param("userId") Long userId, @Param("loginUserId") Long loginUserId);


    @Select({" select u.id, u.username, u.motto, u.img , " ,
            "  (select count(id) from user_focus where user_id = #{loginUserId} and focus_id = u.id) as focused " ,
            " FROM user u " ,
            " where u.id in (select user_id from user_focus where focus_id = #{userId} ) " ,
            " order by u.create_time desc "})
    Page<UserVo> selectFansUserList(Page<UserVo> page, @Param("userId") Long userId,@Param("loginUserId") Long loginUserId);

    Page<ArticleVo> getUserList(Page<ArticleVo> page,@Param("info") User info);

}
