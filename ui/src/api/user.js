import axios from './request.js'

// 登录
export function login(param) {
  return axios({
    url: '/api/user/login',
    method: 'post',
    data: param
  })
}

// 获取用户信息
export function getUserInfo(param) {
  return axios({
    url: '/api/user/getUserInfo',
    method: 'post',
    data: param
  })
}

// 注册
export function register(param) {
  return axios({
    url: '/api/user/register',
    method: 'post',
    data: param
  })
}

// 更新用户信息
export function updateInfo(param) {
  return axios({
    url: '/api/user/updateInfo',
    method: 'post',
    data: param
  })
}

// 发送邮件-注册
export function sendRegisterEmail(email) {
  return axios({
    url: '/api/user/sendRegisterEmail',
    method: 'post',
    data: {email}
  })
}

// 发送邮件-忘记密码
export function sendForgetEmail(email) {
  return axios({
    url: '/api/user/sendForgetEmail',
    method: 'post',
    data: {email}
  })
}

// 校验验证码
export function confirmEmail(param) {
  return axios({
    url: '/api/user/confirmEmail',
    method: 'post',
    data: param
  })
}

// 修改密码
export function change(param) {
  return axios({
    url: '/api/user/change',
    method: 'post',
    data: param
  })
}

// 生成密钥
export function generateCode(param) {
  return axios({
    url: '/api/user/generateCode',
    method: 'post',
    data: param
  })
}

// 二次验证绑定
export function bindTFA(param) {
  return axios({
    url: '/api/user/bindTFA',
    method: 'post',
    data: param
  })
}

// ？查看用户状态
export function checkUserStatus() {
  return axios({
    url: '/api/user/checkUserStatus',
    method: 'post'
  })
}

export function loginCheck(username) {
  return axios({
    url: '/api/user/loginCheck',
    method: 'post',
    data: {username}
  })
}

export function getUserVoById(userId, focusId) {
  return axios({
    url: '/api/user/getUserVoById',
    method: 'post',
    data: {userId, focusId}
  })
}

// 关注
export function addUserFocus(focusId) {
  return axios({
    url: '/api/user/addUserFocus',
    method: 'post',
    data: {focusId}
  })
}

// 取消关注
export function deleteUserFocus(focusId) {
  return axios({
    url: '/api/user/deleteUserFocus',
    method: 'post',
    data: {focusId}
  })
}

// 关注列表
export function getFocusUserList(param) {
  return axios({
    url: '/api/user/getFocusUserList',
    method: 'post',
    data: param
  })
}

// 粉丝列表
export function getFansUserList(param) {
  return axios({
    url: '/api/user/getFansUserList',
    method: 'post',
    data: param
  })
}