import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        component: () => import('../views/login/Login.vue')
    },
    {
        path: '/home',
        component: () => import('../views/home/Home.vue') // 暂时还没建这个，不影响登录测试
    },
    {
        path: '/division',
        name: 'Division',
        component: () => import('../views/division/index.vue')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 简单的路由守卫
router.beforeEach((to, from, next) => {
    if (to.path === '/') return next()
    const token = localStorage.getItem('token')
    if (!token) return next('/')
    next()
})

export default router