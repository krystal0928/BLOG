import axios from './request.js'

// 保存草稿
export function saveDraft(param) {
  return axios({
    url: '/api/article/saveDraft',
    method: 'post',
    data: param
  })
}

// 发布文章
export function publishArticle(param) {
  return axios({
    url: '/api/article/publishArticle',
    method: 'post',
    data: param
  })
}

// 删除文章
export function deleteArticle(id) {
  return axios({
    url: '/api/article/delete',
    method: 'post',
    data: {id}
  })
}

// 删除草稿
export function deleteDraftArticle(id) {
  return axios({
    url: '/api/article/deleteDraft',
    method: 'post',
    data: {id}
  })
}

// 查询文章-公开
export function articleListPublic(param) {
  return axios({
    url: '/api/article/list/public',
    method: 'post',
    data: param
  })
}

// 查询文章-关注
export function articleListFocus(param) {
  return axios({
    url: '/api/article/list/focus',
    method: 'post',
    data: param
  })
}

// 查询文章-用户个人页面
export function articleListPersonal(param) {
  return axios({
    url: '/api/article/list/personal',
    method: 'post',
    data: param
  })
}


// 获取用户收藏的文章
export function getCollectArticle(param) {
  return axios({
    url: '/api/article/list/getCollectArticle',
    method: 'post',
    data: param
  })
}

// 获取文章信息
export function getArticleById(id) {
  return axios({
    url: `/api/article/${id}`,
    method: 'post'
  })
}

export function getPublishArticleById(id) {
  return axios({
    url: `/api/article/publish/${id}`,
    method: 'post'
  })
}

export const uploadUrl = 'http://localhost:8080/blog/api/file/upload'

// 点赞
export function addArticleLike(articleId) {
  return axios({
    url: '/api/article/addArticleLike',
    method: 'post',
    data: {articleId}
  })
}

// 取消点赞
export function deleteArticleLike(articleId) {
  return axios({
    url: '/api/article/deleteArticleLike',
    method: 'post',
    data: { articleId }
  })
}

// 收藏
export function addArticleCollect(articleId) {
  return axios({
    url: '/api/article/addArticleCollection',
    method: 'post',
    data: { articleId }
  })
}

// 取消收藏
export function deleteArticleCollect(articleId) {
  return axios({
    url: '/api/article/deleteArticleCollection',
    method: 'post',
    data: { articleId }
  })
}


// 发布评论
export function addComment(param) {
  return axios({
    url: '/api/article/addComment',
    method: 'post',
    data: param
  })
}

// 一级评论列表
export function getCommentList(param) {
  return axios({
    url: '/api/article/getCommentList',
    method: 'post',
    data: param
  })
}

// 二级评论列表
export function getSecondLevelCommentList(param) {
  return axios({
    url: '/api/article/second-level-comment-list',
    method: 'post',
    data: param
  })
}

// 删除评论
export function deleteComment(commentId) {
  return axios({
    url: '/api/article/deleteComment',
    method: 'post',
    data: {commentId}
  })
}
