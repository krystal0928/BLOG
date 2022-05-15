package com.krystal.blog.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.ArticleComment;
import com.krystal.blog.common.model.ArticleLike;
import com.krystal.blog.common.model.vo.ArticleCommentVo;
import com.krystal.blog.common.model.vo.ArticleLikeVo;
import com.krystal.blog.common.service.ArticleCommentService;
import com.krystal.blog.common.service.ArticleLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

// 接口
@RestController
@Slf4j
public class ArticleLikeController {
    @Resource
    private ArticleLikeService articleLikeService;

    /**
     * 分页查询文章
     * @param info
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/api/article/like/list")
    public R list(ArticleLikeVo info,
                  @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                  @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<ArticleLikeVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleLikeVo> list = articleLikeService.getArticleLikeList(page, info);

        return R.okData("查询点赞信息成功", list.getRecords())
                .put("total", list.getTotal());
    }

}
