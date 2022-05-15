import request from '@/utils/request'

// 文章分类分页查询
export function getArticleTypeList(params) {
  return request({
    url: '/api/article/type/list',
    method: 'post',
    data: params
  })
}

// 文章分类编辑
export function editArticleType(params) {
  return request({
    url: '/api/article/type/edit',
    method: 'post',
    data: params
  })
}

// 文章分类删除
export function deleteArticleType(id) {
  return request({
    url: '/api/article/type/delete',
    method: 'post',
    data: { 
      id 
    }
  })
}
