import axios from 'axios'
import qs from 'qs'
import { ElMessage } from 'element-plus'

const instance = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 3000,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
});

// 添加请求拦截器
instance.interceptors.request.use((config) => {
  // Content-Type
  if (config.headers['Content-Type'].indexOf('application/x-www-form-urlencoded') !== -1) {
    config.data = qs.stringify(config.data)
  }

  if (localStorage.getItem('user')) {
    const token = JSON.parse(localStorage.getItem('user'))?.token
    config.headers['token'] = token
  }

  return config;
}, (error) => {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(response => {
  // status
  if (response.status != 200)
    ElMessage.error('网络异常，请求失败！');
  if (response.data.code != 200) {
    ElMessage({
      showClose: true,
      message: response.data.msg,
      type: 'error',
    })
  }
  return response.data;
}, (error) => {
  // 对响应错误做点什么
  ElMessage.error('网络异常，请求失败！');
  return Promise.reject(error);
});

export default instance