package com.krystal.blog.api;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.model.Admin;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

// 接口
@RestController
@Slf4j
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /**
     * 分页查询文章
     * @param info
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/api/article/list")
    public R articleList(ArticleVo info,
                         @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<ArticleVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleVo> list = articleService.getArticleList(page, info);

        return R.okData("查询文章成功", list.getRecords())
                .put("total", list.getTotal());
    }

    /**
     * 编辑
     * @param info info
     * @return
     */
    @PostMapping(value = "/api/article/edit")
    public R edit(Article info){
        Article article = articleService.getById(info.getId());
        if (null == article) {
            return R.error(400, "文章信息不存在，不能修改!");
        }

        info.setUpdateTime(new Date());
        if (!articleService.updateById(info)) {
            return R.error(400, "修改文章信息失败!");
        }
        return R.error(200, "修改文章信息成功!");
    }


    /**
     * 删除文章
     * @param id
     * @return
     */
    @PostMapping(value = "/api/article/deleteArticle")
    public R deleteArticle(Long id){
        if(!articleService.removeById(id))
            return R.error(400,"文章删除失败！");
        return R.ok("文章删除成功！");
    }


}
