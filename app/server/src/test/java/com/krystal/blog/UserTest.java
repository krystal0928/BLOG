package com.krystal.blog;

import com.krystal.blog.mapper.UserMapper;
import com.krystal.blog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Slf4j
public class UserTest extends BlogApplicationTests {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testSelect() {
        User user = userMapper.selectById(1L);
        log.info(user.toString());
    }

    @Test
    public void testInsert() {
        User user = User.builder()
                .id(4L)
                .username("邹小胖4")
                .password("krystal")
                .nickname("宝")
                .build();
        int result = userMapper.insert(user);
        log.info(result + " ");
    }

    @Test
    public void testRedis(){
        RBucket<String> bucket = redissonClient.getBucket("idiotalex@163.com");
        log.info("{}",null==bucket);
        log.info("{}",null==bucket.get());
        log.info(bucket.get().toString());
    }
}
