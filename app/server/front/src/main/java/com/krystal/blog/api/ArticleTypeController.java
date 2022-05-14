package com.krystal.blog.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.annotation.NoNeedLogIn;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.ArticleType;
import com.krystal.blog.common.model.vo.ArticleTypeVo;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.ArticleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ArticleTypeController {
    @Resource
    private ArticleTypeService articleTypeService;

    /**
     * 分页查询文章分类信息
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    @NoNeedLogIn
    @PostMapping(value = "/api/article/type-list")
    public R getArticleTypeList(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<ArticleType> page = new Page<>(pageNo, pageSize);
        Page<ArticleType> list = articleTypeService.getArticleTypeList(page);

        return R.okData("文章分类查询成功!", list.getRecords())
                .put("total", list.getTotal());
    }

    /**
     * 查询文章分类信息
     * @param id id
     * @return
     */
    @NoNeedLogIn
    @PostMapping(value = "/api/article/type/{id}")
    public R getArticleType(@PathVariable(value = "id") Long id) {
        ArticleTypeVo articleTypeVo = articleTypeService.getArticleType(id);

        return R.okData("文章分类查询成功!", articleTypeVo);
    }
}
