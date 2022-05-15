package com.krystal.blog.api;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.ArticleType;
import com.krystal.blog.common.model.vo.ArticleTypeVo;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.ArticleService;
import com.krystal.blog.common.service.ArticleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

// 接口
@RestController
@Slf4j
public class ArticleTypeController {
    @Resource
    private ArticleTypeService articleTypeService;
    @Resource
    private ArticleService articleService;
    @Resource
    private SnowFlakeTemplate snowFlakeTemplate;

    /**
     * 分页查询文章类型
     * @param info
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/api/article/type/list")
    public R articleList(ArticleType info,
                         @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Page<ArticleTypeVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleTypeVo> list = articleTypeService.getArticleTypeList(page, info);

        return R.okData("查询文章分类成功", list.getRecords())
                .put("total", list.getTotal());
    }

    /**
     * 编辑文章分类
     * @param info
     * @return
     */
    @PostMapping(value = "/api/article/type/edit")
    public R edit(ArticleType info){
        // 判断新增或者修改
        if (null == info.getId()) {
            info.setId(snowFlakeTemplate.getIdLong());
            info.setCreateTime(new Date());

            if (!articleTypeService.save(info)) {
                return R.error(400, "新增文章分类失败!");
            }
            return R.error(200, "新增文章分类成功!");
        } else {
            info.setUpdateTime(new Date());

            if (!articleTypeService.updateById(info)) {
                return R.error(400, "修改文章分类失败!");
            }
            return R.error(200, "修改文章分类成功!");
        }
    }

    /**
     * 删除文章分类
     * @param id
     * @return
     */
    @PostMapping(value = "/api/article/type/delete")
    public R delete(Long id){
        // 查询该分类下是否有文章
        List<Article> list = articleService.lambdaQuery()
                .eq(Article::getTypeId, id)
                .list();
        if (list.isEmpty()) {
            if(!articleTypeService.removeById(id))
                return R.error(400,"文章删除失败！");
            return R.ok("文章删除成功！");
        }
        return R.error(400,"该分类下有文章，不能删除！");
    }


}
