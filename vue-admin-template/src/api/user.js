import request from '@/utils/request'

// 分页查询
export function getUserList(params) {
  return request({
    url: '/api/user/list',
    method: 'post',
    data: params
  })
}
