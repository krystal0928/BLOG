package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.beans.ApplicationTemplate;
import com.krystal.blog.common.enums.ArticleStatusEnum;
import com.krystal.blog.common.mapper.ArticleMapper;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.ArticleService;
import com.krystal.blog.common.util.Const;
import com.krystal.blog.common.util.FileUtil;
import com.krystal.blog.common.util.HtmlUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ApplicationTemplate applicationTemplate;

    @Override
    public List<ArticleVo> selectArticleList(Long userId) {
        return articleMapper.selectArticleList(userId);
    }

    @Override
    public boolean publish(Article info) {
        // 修改摘要
        info.setPubDescription(info.getDescription());
        // 内容转换成 html 文件
        String htmlStr = HtmlUtil.templateToHtml("article.html", info.getContent());
        String filePath = FileUtil.addPathSeparate(applicationTemplate.getBaseDirectory());
        FileUtil.buildFileByPath(filePath);
        String fileName = String.format("%s/%s.html", Const.BLOG_ARTICLE, info.getId());
        boolean flag = HtmlUtil.htmlToFile(filePath, fileName, htmlStr);
        if (!flag) {
            return false;
        }
        // 修改数据库
        info.setStatus(ArticleStatusEnum.STATUS1.getCode());
        info.setFilepath(fileName);
        info.setUpdateTime(new Date());
        return this.updateById(info);
    }
}
