import axios from './request.js'


export function saveDraft(param) {
    return axios({
        url: '/api/article/saveDraft',
        method: 'post',
        data: param
    })
}

export function publishArticle(param) {
    return axios({
        url: '/api/article/publishArticle',
        method: 'post',
        data: param
    })
}
export function selectArticleList(param) {
    return axios({
        url: '/api/article/selectArticleList',
        method: 'post',
        data: param
    })
}

export const uploadUrl = 'http://localhost:8080/blog/api/file/upload'