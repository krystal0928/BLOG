package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select({" select u.username ,u.img, u.motto, ",
            " ifnull(count(f.id), 0) focusCount, ",
            " (select count(id) from user_focus where user_id = #{userId} and focus_id = #{focusId}) as focused, ",
            " from user u ",
            " left join user_focus f on u.id = f.focus_id ",
            " where u.id = #{focusId} "})
    UserVo selectUser(@Param("userId") Long userId, @Param("focusId") Long focusId);
}
