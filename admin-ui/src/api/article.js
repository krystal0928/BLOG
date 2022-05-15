import request from '@/utils/request'

export function getArticleList(params) {
  return request({
    url: '/api/article/list',
    method: 'post',
    data: params
  })
}
