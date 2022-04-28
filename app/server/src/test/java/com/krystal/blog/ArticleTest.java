package com.krystal.blog;

import com.krystal.blog.model.Article;
import com.krystal.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

@Slf4j
public class ArticleTest extends BlogApplicationTests {

    @Resource
    private ArticleService articleService;
    @Test
    public void insertTest(){
        Article info = Article.builder().id(3424891L).userId(2L).content("你好哇！".getBytes()).title("nihao").description("text").build();
        articleService.save(info);
    }
}
