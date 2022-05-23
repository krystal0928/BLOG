import request from '@/utils/request'

export function getCommentList(params) {
  return request({
    url: '/api/article/comment/list',
    method: 'post',
    data: params
  })
}

export function deleteArticleComment(id) {
  return request({
    url: '/api/article/comment/delete',
    method: 'post',
    data: { id }
  })
}
