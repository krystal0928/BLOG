import request from '@/utils/request'

// 分页查询
export function getArticleCollectionList(params) {
  return request({
    url: '/api/article/collection/list',
    method: 'post',
    data: params
  })
}

export function deleteArticleCollection(id) {
  return request({
    url: '/api/article/collection/delete',
    method: 'post',
    data: { id }
  })
}
