package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select({" select u.*, ",
            " count(f.id) focusCount, ",
            " (select count(ac.id) from article_collection ac where ac.article_id  in(select id from article where user_id = #{focusId})) collectCount, ",
            " (select count(al.id) from article_like al where al.article_id in (select id from article where user_id = #{focusId})) likeCount, ",
            " (select count(id) from user_focus where user_id = #{userId} and focus_id = #{focusId}) as focused ",
            " from user u ",
            " left join user_focus f on u.id = f.focus_id ",
            " where u.id = #{focusId} "})
    UserVo selectUser(@Param("userId") Long userId, @Param("focusId") Long focusId);

    @Select({" select u.id, u.username, u.motto, u.img , " ,
            "  (select count(id) from user_focus where user_id = #{tokenUserId} and focus_id = u.id) as focused " ,
            " FROM user u " ,
            " where u.id in (select focus_id from user_focus where user_id = #{userId} ) " ,
            " order by u.create_time desc "})
    List<UserVo> selectFocusUserList(@Param("userId") Long userId,@Param("tokenUserId") Long tokenUserId);

}
