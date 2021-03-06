package com.krystal.blog.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.common.model.User;
import com.krystal.blog.common.model.UserFocus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFocusMapper extends BaseMapper<UserFocus> {

}
