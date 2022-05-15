import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/admin/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/api/admin/info',
    method: 'post'
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

// 分页查询
export function getAdminList(params) {
  return request({
    url: '/api/admin/list',
    method: 'post',
    data: params
  })
}

// 编辑
export function editAdmin(params) {
  return request({
    url: '/api/admin/edit',
    method: 'post',
    data: params
  })
}

// 删除
export function deleteAdmin(id) {
  return request({
    url: '/api/admin/delete',
    method: 'post',
    data: { 
      id 
    }
  })
}
