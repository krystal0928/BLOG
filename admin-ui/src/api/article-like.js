import request from '@/utils/request'

// 分页查询
export function getArticleLikeList(params) {
  return request({
    url: '/api/article/like/list',
    method: 'post',
    data: params
  })
}
