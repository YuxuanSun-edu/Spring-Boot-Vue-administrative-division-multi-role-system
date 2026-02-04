import axios from 'axios'

// 1. 创建 axios 实例
const service = axios.create({
    baseURL: 'http://localhost:8080', // 对应你后端 Spring Boot 的端口
    timeout: 10000
})

// 2. 请求拦截器：每张发出去的报文都贴上 Token 这张“门票”
service.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        // 这里的 Authorization 要和后端 JwtInterceptor 获取的 Key 一致
        config.headers['Authorization'] = token
    }
    return config
}, error => {
    return Promise.reject(error)
})

// 3. 响应拦截器：处理后端返回的 code 状态
service.interceptors.response.use(res => {
    // res.data 才是你后端 AjaxResult 返回的具体内容
    const { code, msg } = res.data
    if (code === 401) {
        alert("登录过期，请重新登录")
        localStorage.removeItem('token')
        // window.location.href = '/login'
    } else if (code === 500) {
        alert(msg || "系统错误")
    }
    return res.data
}, error => {
    return Promise.reject(error)
})

export default service