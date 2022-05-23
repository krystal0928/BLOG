package com.krystal.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krystal.blog.common.beans.ApplicationTemplate;
import com.krystal.blog.common.enums.ArticleStatusEnum;
import com.krystal.blog.common.mapper.ArticleMapper;
import com.krystal.blog.common.model.Article;
import com.krystal.blog.common.model.vo.ArticleCommentVo;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.ArticleService;
import com.krystal.blog.common.util.Const;
import com.krystal.blog.common.util.FileUtil;
import com.krystal.blog.common.util.HtmlUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ApplicationTemplate applicationTemplate;

    @Override
    public Page<ArticleVo> selectArticleListPublic(Page<ArticleVo> page, Long loginUserId, String title, String orderFlag) {
        return articleMapper.selectArticleListPublic(page, loginUserId, title,  orderFlag);
    }

    @Override
    public Page<ArticleVo> selectArticleListPersonal(Page<ArticleVo> page, Long loginUserId, Long userId, Integer status) {
        return articleMapper.selectArticleListPersonal(page, loginUserId, userId, status);

    }

    @Override
    public Page<ArticleVo> selectArticleListUserFocus(Page<ArticleVo> page,
                                                      Long loginUserId,
                                                      Long userId,
                                                      String title) {
        return articleMapper.selectArticleListUserFocus(page, loginUserId, userId, title);
    }

    @Override
    public Page<ArticleVo> selectCollectArticle(Page<ArticleVo> page,
                                                Long loginUserId,
                                                Long userId) {
        return articleMapper.selectCollectArticle(page, loginUserId, userId);
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

    @Override
    public ArticleVo selectArticle(Long id, Long userId) {
        return articleMapper.selectArticle(id,userId);
    }

    @Override
    public Page<ArticleVo> getArticleList(Page<ArticleVo> page, ArticleVo info) {
        return articleMapper.getArticleList(page, info);
    }
}
