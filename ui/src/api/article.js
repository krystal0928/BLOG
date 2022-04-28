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