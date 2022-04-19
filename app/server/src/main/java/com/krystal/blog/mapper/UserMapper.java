package com.krystal.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.krystal.blog.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
