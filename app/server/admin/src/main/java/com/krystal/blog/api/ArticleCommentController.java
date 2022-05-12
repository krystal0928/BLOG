package com.krystal.blog.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleCommentVo;
import com.krystal.blog.common.service.ArticleCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

// 接口
@RestController
@Slf4j
public class ArticleCommentController {
    @Resource
    private ArticleCommentService articleCommentService;

    /**
     * 分页查询文章
     * @param info
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/api/comment/list")
    public R articleList(Article info,
                         @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<ArticleCommentVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleCommentVo> list = articleCommentService.getArticleCommentList(page, info);

        return R.okData("查询评论成功", list.getRecords())
                .put("total", list.getTotal());
    }

}
