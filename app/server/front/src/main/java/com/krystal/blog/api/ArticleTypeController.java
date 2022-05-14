package com.krystal.blog.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.annotation.NoNeedLogIn;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.ArticleType;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.ArticleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ArticleTypeController {
    @Resource
    private ArticleTypeService articleTypeService;

    @NoNeedLogIn
    @PostMapping(value = "/api/article/type-list")
    public R getArticleTypeList(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<ArticleType> page = new Page<>(pageNo, pageSize);
        Page<ArticleType> list = articleTypeService.getArticleTypeList(page);

        return R.okData("文章分类查询成功!", list.getRecords())
                .put("total", list.getTotal());
    }
}
