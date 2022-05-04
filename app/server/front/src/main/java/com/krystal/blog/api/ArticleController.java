package com.krystal.blog.api;

import cn.hutool.core.util.StrUtil;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.enums.ArticleStatusEnum;
import com.krystal.blog.common.model.*;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;
    @Resource
    private SnowFlakeTemplate snowFlakeTemplate;
    @Resource
    private ArticleCommentService articleCommentService;
    @Resource
    private ArticleCollectionService articleCollectionService;
    @Resource
    private ArticleLikeService articleLikeService;


    /**
     * 草稿保存
     * @param info
     * @return
     */
    @PostMapping("/api/article/saveDraft")
    public R saveDraft (@RequestHeader("token") String token, Article info) {
        Assert.notNull(info.getTitle(),"标题不能为空！");
        Assert.notNull(info.getContent(),"内容不能为空！");
        Assert.notNull(info.getDescription(),"文章摘要不能为空！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        info.setUserId(user.getId());
        info.setId(snowFlakeTemplate.getIdLong());
        info.setStatus(ArticleStatusEnum.STATUS0.getCode());
        if (!articleService.save(info))
            return R.error(400,"文章保存失败！");
        return R.ok("文章已保存至草稿箱！");
    }

    /**
     * 文章发布
     * @param info
     * @return
     */
    @PostMapping("/api/article/publishArticle")
    public R publishArticle (@RequestHeader("token") String token,Article info) {
        Assert.notNull(info.getTitle(),"标题不能为空！");
        Assert.notNull(info.getContent(),"内容不能为空！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        Article article = articleService.getById(info.getId());
        if (null != article) {
            if (article.getUserId() != user.getId())
                return R.error(400, "用户不匹配");
            if (article.getStatus() == 1 || article.getStatus() == 2) {
                //删除源文件
            }
        } else if (null != article) {
            info.setUserId(user.getId());
            info.setId(snowFlakeTemplate.getIdLong());
        }
        //发布文件
        info.setPubDescription(info.getDescription());
        info.setStatus(1);
        if (!articleService.save(info))
            return R.error(400,"文章发布失败！");
        return R.ok("文章已发布！");
    }

    /**
     * 首页加载文章
     * @return
     */
    @PostMapping("/api/article/selectArticleList")
    public R selectArticleList(@RequestHeader("token") String token){
        Long userId = 0L;
        if (!StrUtil.isEmpty(token)) {
            User user = userService.getUserByToken(token);
            if (null == user)
                return R.error(400,"该用户不存在！");
            userId = user.getId();
        }
        List<ArticleVo> articleVoList = articleService.selectArticleList(userId);
        return R.okMap("文章查询成功!",articleVoList);
    }


    /**
     * 点赞
     * @param token
     * @param articleId
     * @return
     */
    @PostMapping("/api/article/addArticleLike")
    public R addArticleLike(@RequestHeader("token") String token,Long articleId){
        Article article = articleService.getById(articleId);
        if (null == article)
            return R.error(400,"该文章目前已不存在！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"登录已过期，请重新登录！");
        ArticleLike articleLike = ArticleLike.builder()
                .id(snowFlakeTemplate.getIdLong())
                .articleId(articleId)
                .userId(user.getId())
                .build();
        if (!articleLikeService.save(articleLike)) {
            return R.error(400,"点赞失败，请重新尝试！");
        }
        return R.ok("点赞成功！");
    }


    /**
     * 取消点赞
     * @param token
     * @param articleId
     * @return
     */
    @PostMapping("/api/article/deleteArticleLike")
    public R deleteArticleLike(@RequestHeader("token") String token,Long articleId){
        Article article = articleService.getById(articleId);
        if (null == article)
            return R.error(400,"该文章目前已不存在！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        ArticleLike articleLike = articleLikeService.lambdaQuery()
                .eq(ArticleLike::getArticleId,articleId)
                .eq(ArticleLike::getUserId,user.getId())
                .one();
        if (!articleLikeService.removeById(articleLike.getId())) {
            return R.error(400,"取消点赞失败，请重新尝试！");
        }
        return R.ok("取消点赞成功！");
    }

    /**
     * 收藏
     * @param token
     * @param articleId
     * @return
     */
    @PostMapping("/api/article/addArticleCollection")
    public R addArticleCollection(@RequestHeader("token") String token,Long articleId){
        Article article = articleService.getById(articleId);
        if (null == article)
            return R.error(400,"该文章目前已不存在！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        ArticleCollection articleCollection = ArticleCollection.builder()
                .id(snowFlakeTemplate.getIdLong())
                .articleId(articleId)
                .userId(user.getId())
                .build();
        if (!articleCollectionService.save(articleCollection)) {
            return R.error(400,"收藏失败，请重新尝试！");
        }
        return R.ok("收藏成功！");
    }

    /**
     * 取消收藏
     * @param token
     * @param articleId
     * @return
     */
    @PostMapping("/api/article/deleteArticleCollection")
    public R deleteArticleCollection(@RequestHeader("token") String token,Long articleId){
        Article article = articleService.getById(articleId);
        if (null == article)
            return R.error(400,"该文章目前已不存在！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        ArticleCollection articleCollection = articleCollectionService.lambdaQuery()
                .eq(ArticleCollection::getArticleId,articleId)
                .eq(ArticleCollection::getUserId,user.getId())
                .one();
        if (!articleCollectionService.removeById(articleCollection.getId())) {
            return R.error(400,"取消收藏失败，请重新尝试！");
        }
        return R.ok("取消收藏成功！");
    }


    /**
     * 发布评论
     * @param token
     * @param info
     * @return
     */
    @PostMapping("/api/article/addComment")
    public R addComment(@RequestHeader("token") String token, ArticleComment info) {
        Assert.notNull(info.getArticleId(),"文章id不能为空！");
        Assert.notNull(info.getContent(),"内容不能为空！");
        if (null == articleService.getById(info.getArticleId()))
            return R.error(400,"该文章目前已不存在！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");
        info.setUserId(user.getId());
        if (null != info.getPid()) {
            ArticleComment comment = articleCommentService.getById(info.getPid());
            if (null == comment) {
                return R.error(400,"回复的评论不存在！");
            }
        }
        info.setId(snowFlakeTemplate.getIdLong());
        if (!articleCommentService.save(info))
            return R.error(400,"评论发布失败，请重新尝试！");
        return R.ok("评论发布成功！");
    }

}
