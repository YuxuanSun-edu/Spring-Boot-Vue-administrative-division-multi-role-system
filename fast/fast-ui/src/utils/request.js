import axios from 'axios'

// 创建 axios 实例
const service = axios.create({
    baseURL: 'http://localhost:8080', // 你的 SpringBoot 后端地址
    timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers['Authorization'] = token // 带上令牌
    }
    return config
}, error => {
    return Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
    const code = res.data.code || 200
    if (code === 401) {
        alert("登录已过期，请重新登录")
        localStorage.clear()
        location.reload()
    } else if (code !== 200) {
        alert(res.data.msg || "系统错误")
        return Promise.reject(res.data)
    }
    return res.data
}, error => {
    console.error(error) // 打印错误方便调试
    return Promise.reject(error)
})

export default service