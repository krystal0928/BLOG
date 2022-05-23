package com.krystal.blog.api;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krystal.blog.common.annotation.NoNeedLogIn;
import com.krystal.blog.common.beans.ApplicationTemplate;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.beans.SnowFlakeTemplate;
import com.krystal.blog.common.enums.ArticleDeletedEnum;
import com.krystal.blog.common.enums.ArticleStatusEnum;
import com.krystal.blog.common.model.*;
import com.krystal.blog.common.model.vo.ArticleCommentVo;
import com.krystal.blog.common.model.vo.ArticleVo;
import com.krystal.blog.common.service.*;
import com.krystal.blog.common.util.FileUtil;
import com.krystal.blog.common.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    @Resource
    private ApplicationTemplate applicationTemplate;
    @Resource
    private EmailService emailService;

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
        if (null == info.getId()) {
            info.setId(snowFlakeTemplate.getIdLong());
            info.setStatus(ArticleStatusEnum.STATUS0.getCode());
        }
        // 如果是已发布文章，保存草稿
        if (ArticleStatusEnum.STATUS1.getCode().equals(info.getStatus())) {
            info.setStatus(ArticleStatusEnum.STATUS2.getCode());
        }
        info.setUserId(user.getId());
        if (!articleService.saveOrUpdate(info))
            return R.error(400,"文章保存失败！");
        return R.ok("文章已保存至草稿箱！");
    }

    /**
     * 文章发布
     * @param info
     * @return
     */
    @PostMapping("/api/article/publishArticle")
    public R publishArticle (@RequestHeader("token") String token,
                             Article info) {
        Assert.notNull(info.getTitle(),"标题不能为空！");
        Assert.notNull(info.getContent(),"内容不能为空！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"该用户不存在！");

        // 设置参数
        if (null == info.getUserId()){
            info.setUserId(user.getId());
        }
        // 如果不存在 ID 表示新增
        if (null == info.getId()) {
            info.setId(snowFlakeTemplate.getIdLong());
            info.setCreateTime(new Date());
            articleService.save(info);
        }
        // 查询数据库
        Article article = articleService.getById(info.getId());
        if (null == article) {
            return R.error(400,"原文章不存在！");
        }
        if (!articleService.publish(info))
            return R.error(400,"文章发布失败！");
        return R.ok("文章已发布！");
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @PostMapping("/api/article/delete")
    public R deleteArticle (Long id) {
        Assert.notNull(id, "文章 ID 不能为空！");

        // 查询文章是否存在
        Article article = articleService.getById(id);
        if (null == article) {
            return R.error(400 ,"文章不存在！");
        }

        // 判断文章状态，为 2：已修改 特殊处理
        if (ArticleStatusEnum.STATUS2.getCode().equals(article.getStatus())) {
            // 设置文章状态为草稿
            article.setStatus(ArticleStatusEnum.STATUS0.getCode());
        } else if (ArticleStatusEnum.STATUS1.getCode().equals(article.getStatus())) {
            // 如果文章状态是 1：已发布 需要删除已发布文件
            String filePath = FileUtil.addPathSeparate(applicationTemplate.getBaseDirectory(), article.getFilepath());
            if (cn.hutool.core.io.FileUtil.exist(filePath)) {
                cn.hutool.core.io.FileUtil.del(filePath);
            }
            // 设置为删除
            article.setDeleted(ArticleDeletedEnum.STATUS1.getCode());
        }
        if (!articleService.updateById(article)) {
            return R.error(400, "文章删除失败！");
        }

        return R.error(200, "文章删除成功！");
    }

    /**
     * 删除草稿
     * @param id
     * @return
     */
    @PostMapping("/api/article/deleteDraft")
    public R deleteDraft (Long id) {
        Assert.notNull(id, "文章 ID 不能为空！");

        // 查询文章是否存在
        Article article = articleService.getById(id);
        if (null == article) {
            return R.error(400 ,"文章不存在！");
        }

        // 判断文章状态，为 2：已修改 特殊处理
        if (ArticleStatusEnum.STATUS2.getCode().equals(article.getStatus())) {
            // 先从已发布文件里面读取内容，还原到数据库
            String filePath = FileUtil.addPathSeparate(applicationTemplate.getBaseDirectory(), article.getFilepath());
            if (!cn.hutool.core.io.FileUtil.exist(filePath)) {
                return R.error(400, "文章发布信息不存在");
            }
            // 读取文件
            FileReader reader = new FileReader(filePath);
            String content = reader.readString();
            // 还原发布时的信息
            article.setContent(content);
            article.setDescription(article.getPubDescription());
            // 设置文章状态为发布中
            article.setStatus(ArticleStatusEnum.STATUS1.getCode());
            if (!articleService.updateById(article)) {
                return R.error(400, "草稿箱删除失败！");
            }
        } else if (ArticleStatusEnum.STATUS0.getCode().equals(article.getStatus())) {
            article.setDeleted(ArticleDeletedEnum.STATUS1.getCode());
            if (!articleService.updateById(article)) {
                return R.error(400, "草稿箱删除失败！");
            }
        }
        return R.error(200, "草稿箱删除成功！");
    }



    /**
     * 首页加载文章
     * @return
     */
    @NoNeedLogIn
    @PostMapping("/api/article/list/public")
    public R articleListPublic(@RequestParam(value = "loginUserId", defaultValue = "0") Long loginUserId,
                               String title,
                               @RequestParam(value = "orderFlag") String orderFlag,
                               @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        Page<ArticleVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleVo> articleVoList = articleService.selectArticleListPublic(page, loginUserId, title, orderFlag);

        return R.okData("文章查询成功!", articleVoList.getRecords())
                .put("total", articleVoList.getTotal());
    }

    /**
     * 首页加载关注用户发表的文章
     * @return
     */
    @NoNeedLogIn
    @PostMapping("/api/article/list/focus")
    public R articleListFocus(@RequestParam(value = "loginUserId", defaultValue = "0") Long loginUserId,
                              @RequestParam(value = "userId", defaultValue = "0") Long userId,
                              String title,
                              @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                              @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        Page<ArticleVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleVo> articleVoList = articleService.selectArticleListUserFocus(page, loginUserId, userId, title);

        return R.okData("文章查询成功!", articleVoList.getRecords())
                .put("total", articleVoList.getTotal());
    }

    /**
     * 加载个人文章
     * @param loginUserId loginUserId
     * @param userId userId
     * @param status 1 已发布 草稿
     * @param pageNo pageNo
     * @param pageSize pageSize
     * @return
     */
    @NoNeedLogIn
    @PostMapping("/api/article/list/personal")
    public R articleListPersonal(@RequestParam(value = "loginUserId", defaultValue = "0") Long loginUserId,
                                 @RequestParam(value = "userId") Long userId,
                                 @RequestParam(value = "status") Integer status,
                                 @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        Page<ArticleVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleVo> articleVoList = articleService.selectArticleListPersonal(page, loginUserId, userId, status);

        return R.okData("文章查询成功!", articleVoList.getRecords())
                .put("total", articleVoList.getTotal());
    }

    /**
     * 加载用户关注文章
     * @return
     */
    @NoNeedLogIn
    @PostMapping("/api/article/list/getCollectArticle")
    public R getCollectArticle(@RequestParam(value = "loginUserId", defaultValue = "0") Long loginUserId,
                               @RequestParam(value = "userId", defaultValue = "0") Long userId,
                               @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        Page<ArticleVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleVo> articleVoList = articleService.selectCollectArticle(page, loginUserId, userId);

        return R.okData("文章查询成功!", articleVoList.getRecords())
                .put("total", articleVoList.getTotal());
    }



    /**
     * 获取数据库存储文章信息
     * @param id
     * @return
     */
    @PostMapping("/api/article/{id}")
    public R getArticleById(@PathVariable("id") Long id) {
        Article article = articleService.getById(id);
        if (null == article) {
            return R.error(400, "文章信息不存在");
        }
        return R.okData("success", article);
    }

    /**
     * 获取发布文章信息
     * @param token
     * @param httpServletRequest
     * @param id
     * @return
     */
    @NoNeedLogIn
    @PostMapping("/api/article/publish/{id}")
    public R getPublishArticleById(@RequestHeader("token") String token,
                                   HttpServletRequest httpServletRequest,
                                   @PathVariable("id") Long id) {
        Long userId = userService.getUserIdFromToken(token);

        ArticleVo articleVo = articleService.selectArticle(id, userId);

        if (null == articleVo || StrUtil.isEmpty(articleVo.getFilepath())) {
            return R.error(400, "文章信息不存在");
        }
        // 获取发布文章文件内容
        String filePath = FileUtil.addPathSeparate(applicationTemplate.getBaseDirectory(), articleVo.getFilepath());

        if (!cn.hutool.core.io.FileUtil.exist(filePath)) {
            return R.error(400, "文章发布信息不存在");
        }
        FileReader reader = new FileReader(filePath);
        String content = reader.readString();
        FileUtil.addPathSeparate(SystemUtil.getServerPath(httpServletRequest), articleVo.getFilepath());

        articleVo.setContent(content);
        return R.okData("success", articleVo);
    }


    /**
     * 点赞
     * @param token
     * @param articleId
     * @return
     */
    @PostMapping("/api/article/addArticleLike")
    public R addArticleLike(@RequestHeader("token") String token,
                            Long articleId){
        Article article = articleService.getById(articleId);
        if (null == article)
            return R.error(400,"该文章目前已不存在！");
        User user = userService.getUserByToken(token);
        if (null == user)
            return R.error(400,"登录已过期，请重新登录！");

        // 查询是否点赞
        ArticleLike info = articleLikeService.lambdaQuery()
                .eq(ArticleLike::getArticleId, articleId)
                .eq(ArticleLike::getUserId, user.getId())
                .one();
        if (null != info) {
            return R.error(400, "您已点赞，请勿重复操作！");
        }

        ArticleLike articleLike = ArticleLike.builder()
                .id(snowFlakeTemplate.getIdLong())
                .articleId(articleId)
                .userId(user.getId())
                .build();
        if (!articleLikeService.save(articleLike)) {
            return R.error(400,"点赞失败，请重新尝试！");
        }
        User writer = userService.getById(article.getUserId());
        emailService.sendActionEmail(user.getUsername(), article.getTitle(),"点赞了文章",writer.getEmail());
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
        if (null == articleLike) {
            return R.error(400,"您未点赞该文章，取消点赞操作不允许！");
        }

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

        // 查询是否收藏
        ArticleCollection info = articleCollectionService.lambdaQuery()
                .eq(ArticleCollection::getArticleId, articleId)
                .eq(ArticleCollection::getUserId, user.getId())
                .one();
        if (null != info) {
            return R.error(400, "您已收藏，请勿重复操作！");
        }

        ArticleCollection articleCollection = ArticleCollection.builder()
                .id(snowFlakeTemplate.getIdLong())
                .articleId(articleId)
                .userId(user.getId())
                .build();
        if (!articleCollectionService.save(articleCollection)) {
            return R.error(400,"收藏失败，请重新尝试！");
        }
        User writer = userService.getById(article.getUserId());
        emailService.sendActionEmail(user.getUsername(), article.getTitle(),"收藏了文章",writer.getEmail());
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
        if (null == articleCollection) {
            return R.error(400,"您未点赞该文章，取消收藏 操作不允许！");
        }

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

    /**
     * 删除评论
     * @param token
     * @param commentId
     * @return
     */
    @PostMapping("/api/article/deleteComment")
    public R deleteComment(@RequestHeader("token") String token, Long commentId) {
        ArticleComment articleComment = articleCommentService.getById(commentId);
        if (null == articleComment)
            return  R.error(400,"该评论 目前已不存在！");
        if (articleComment.getPid() == null) {
            List<ArticleComment> list = articleCommentService.lambdaQuery()
                    .eq(ArticleComment::getPid,commentId)
                    .list();
            if (!list.isEmpty()) {
                if(!articleCommentService.remove(articleCommentService.lambdaQuery()
                        .eq(ArticleComment::getPid,commentId)
                        .getWrapper()))
                    return R.error(400,"评论删除失败！");
            }
        }
        if(!articleCommentService.removeById(commentId))
            return R.error(400,"评论删除失败！");
        return R.ok("评论删除成功！");
    }

    /**
     * 查询一级评论
     * @param articleId
     * @return
     */
    @NoNeedLogIn
    @PostMapping("/api/article/getCommentList")
    public R getCommentList(Long articleId,
                            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        if (null == articleService.getById(articleId))
            return R.error(400,"该文章目前已不存在！");

        Page<ArticleCommentVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleCommentVo> list = articleCommentService.getFirstLevelList(page, articleId);

        return R.okData("查询评论成功", list.getRecords())
                .put("total", list.getTotal());
    }

    /**
     * 查询二级评论
     * @param articleId 文章 ID
     * @param topId 评论一级 ID
     * @return
     */
    @NoNeedLogIn
    @PostMapping("/api/article/second-level-comment-list")
    public R getSecondLevelCommentList(Long articleId,
                                       Long topId,
                                       @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        if (null == articleService.getById(articleId))
            return R.error(400,"该文章目前已不存在！");

        Page<ArticleCommentVo> page = new Page<>(pageNo, pageSize);
        Page<ArticleCommentVo> list = articleCommentService.getSecondLevelList(page, articleId, topId);

        return R.okData("查询评论成功", list.getRecords())
                .put("total", list.getTotal());
    }

}
