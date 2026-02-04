import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue') // 确保路径对
    },
    {
        path: '/index',
        name: 'Index',
        component: () => import('../views/Index.vue') // 假设你有这个首页文件
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// --- 路由守卫 (Frontend Guard) ---
router.beforeEach((to, from, next) => {
    // 1. 看用户要去哪里
    if (to.path === '/login') {
        // 如果去登录页，直接放行
        next()
    } else {
        // 2. 如果去其他页面，检查兜里有没有 Token
        const token = localStorage.getItem('token')
        if (token) {
            // 有 Token，放行
            next()
        } else {
            // 没 Token，踢回登录页
            next('/login')
        }
    }
})

export default router