import request from '@/utils/request'

export function getCommentList(params) {
  return request({
    url: '/api/comment/list',
    method: 'post',
    data: params
  })
}
