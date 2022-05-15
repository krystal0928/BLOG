import request from '@/utils/request'

export function getCommentList(params) {
  return request({
    url: '/api/article/comment/list',
    method: 'post',
    data: params
  })
}
