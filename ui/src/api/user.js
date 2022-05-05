import axios from './request.js'

// login
export function login(param) {
    return axios({
        url: '/api/user/login',
        method: 'post',
        data: param
    })
}

export function register(param) {
    return axios({
        url: '/api/user/register',
        method: 'post',
        data: param
    })
}

export function sendRegisterEmail(email) {
    return axios({
        url: '/api/user/sendRegisterEmail',
        method: 'post',
        data: {email}
    })
}
export function sendForgetEmail(email) {
    return axios({
        url: '/api/user/sendForgetEmail',
        method: 'post',
        data: {email}
    })
}

export function confirmEmail(param) {
    return axios({
        url: '/api/user/confirmEmail',
        method: 'post',
        data: param
    })
}

export function change(param) {
    return axios({
        url: '/api/user/change',
        method: 'post',
        data: param
    })
}

export function generateCode(param) {
    return axios({
        url: '/api/user/generateCode',
        method: 'post',
        data: param
    })
}

export function bindTFA(param) {
    return axios({
        url: '/api/user/bindTFA',
        method: 'post',
        data: param
    })
}

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

export function getUserVoById(focusId) {
    return axios({
        url: '/api/user/getUserVoById',
        method: 'post',
        data: {focusId}
    })
}