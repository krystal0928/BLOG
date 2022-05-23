import request from '@/utils/request'

export function getArticleList(params) {
  return request({
    url: '/api/article/list',
    method: 'post',
    data: params
  })
}

export function editArticle(params) {
  return request({
    url: '/api/article/edit',
    method: 'post',
    data: params
  })
}

export function deleteArticle(id) {
  return request({
    url: '/api/article/deleteArticle',
    method: 'post',
    data: { id }
  })
}
