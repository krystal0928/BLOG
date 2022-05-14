import axios from './request.js'

// 查询文章分类
export function articleTypeList(param) {
  return axios({
    url: '/api/article/type-list',
    method: 'post',
    data: param
  })
}