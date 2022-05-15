package com.krystal.blog.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.ArticleCollection;
import com.krystal.blog.common.model.vo.ArticleCollectionVo;
import com.krystal.blog.common.model.vo.ArticleLikeVo;
import com.krystal.blog.common.service.ArticleCollectionService;
import com.krystal.blog.common.service.ArticleLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

// 接口
@RestController
@Slf4j
public class ArticleCollectionController {
    @Resource
    private ArticleCollectionService articleCollectionService;

    /**
     * 分页查询文章
     * @param info
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/api/article/collection/list")
    public R list(ArticleCollectionVo info,
                  @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                  @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<ArticleLikeVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleLikeVo> list = articleCollectionService.getArticleCollectionList(page, info);

        return R.okData("查询收藏信息成功", list.getRecords())
                .put("total", list.getTotal());
    }

}
