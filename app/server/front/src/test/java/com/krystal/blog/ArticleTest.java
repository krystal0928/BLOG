package com.krystal.blog;

import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.ArticleService;
import com.krystal.blog.common.util.HtmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public class ArticleTest extends BlogApplicationTests {

    @Resource
    private ArticleService articleService;
    @Test
    public void insertTest(){
//        Article info = Article.builder().id(3424891L).userId(2L).content("你好哇！").title("nihao").description("text").build();
//        articleService.save(info);
    }

    @Test
    public void selectArticleList() {
        List<ArticleVo> view = articleService.selectArticleList(1l);
        log.info(view.toString());
    }

    @Test
    public void testHtmlTemplate() {
        String template = "<p>「这是我参与11月更文挑战的第5天，活动详情查看：<a href=\"https://juejin.cn/post/7023643374569816095/\" title=\"https://juejin.cn/post/7023643374569816095/\" target=\"_blank\">2021最后一次更文挑战</a>」。</p>\n";
        String res = HtmlUtil.templateToHtml("article.html", template);
        log.info(res);
        boolean flag = HtmlUtil.htmlToFile("/Users/zhangxin/Documents/work_space/temp_file/article/", "1.html", res);
        log.info("res: {}", flag);
    }
}
