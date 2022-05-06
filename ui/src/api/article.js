import axios from './request.js'


export function saveDraft(param) {
  return axios({
    url: '/api/article/saveDraft',
    method: 'post',
    data: param
  })
}

export function publishArticle(param) {
  return axios({
    url: '/api/article/publishArticle',
    method: 'post',
    data: param
  })
}
export function articleListPublic(param) {
  return axios({
    url: '/api/article/list/public',
    method: 'post',
    data: param
  })
}

export function articleListPersonal(param) {
  return axios({
    url: '/api/article/list/personal',
    method: 'post',
    data: param
  })
}

export function getArticleById(id) {
  return axios({
    url: `/api/article/${id}`,
    method: 'post'
  })
}

export const uploadUrl = 'http://localhost:8080/blog/api/file/upload'

export function addArticleLike(articleId) {
  return axios({
    url: '/api/article/addArticleLike',
    method: 'post',
    data: {articleId}
  })
}
export function deleteArticleLike(articleId) {
  return axios({
    url: '/api/article/deleteArticleLike',
    method: 'post',
    data: { articleId }
  })
}
export function addArticleCollect(articleId) {
  return axios({
    url: '/api/article/addArticleCollection',
    method: 'post',
    data: { articleId }
  })
}
export function deleteArticleCollect(articleId) {
  return axios({
    url: '/api/article/deleteArticleCollection',
    method: 'post',
    data: { articleId }
  })
}