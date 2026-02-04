<template>
  <div class="login-container">
    <div class="login-card">
      <div class="title-header">
        <h1>🚀 Fast 管理系统</h1>
      </div>

      <div class="form-content">
        <div class="input-group">
          <label>账号</label>
          <input v-model="form.userName" type="text" placeholder="请输入用户名" />
        </div>

        <div class="input-group">
          <label>密码</label>
          <input v-model="form.password" type="password" placeholder="请输入密码" />
        </div>

        <div v-if="isRegister" class="input-group">
          <label>确认密码</label>
          <input v-model="form.confirmPassword" type="password" placeholder="请再次输入密码" />
        </div>

        <button v-if="!isRegister" class="primary-btn" @click="handleLogin" :disabled="loading">
          {{ loading ? '登录中...' : '立即登录' }}
        </button>

        <button v-else class="success-btn" @click="handleRegister" :disabled="loading">
          {{ loading ? '注册中...' : '立即注册' }}
        </button>

        <div class="switch-mode">
          <span v-if="!isRegister">
            还没有账号？ <a @click="toggleMode">去注册</a>
          </span>
          <span v-else>
            已有账号？ <a @click="toggleMode">去登录</a>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import request from '@/utils/request'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)
const isRegister = ref(false) // 控制当前是登录还是注册

const form = ref({
  userName: '',
  password: '',
  confirmPassword: ''
})

// 切换模式时清空表单
const toggleMode = () => {
  isRegister.value = !isRegister.value
  form.value = { userName: '', password: '', confirmPassword: '' }
}

// 登录逻辑
const handleLogin = () => {
  if(!form.value.userName || !form.value.password) return alert("请输入账号密码")

  loading.value = true
  request.post('/login', form.value).then(res => {
    // 1. 存 Token
    localStorage.setItem('token', res.token)

    // 2. 存角色
    const roles = res.roles || []
    localStorage.setItem('roles', JSON.stringify(roles))

    // 3. 路由跳转
    // 判断是否有 admin 权限，决定跳哪里
    if (roles.includes('admin')) {
      router.push('/home')
    } else {
      router.push('/division')
    }
  }).catch(err => {
    // 系统错误 (HTTP 500 等)
    console.error("注册详细报错:", err)

    // ★★★ 修改这里：注释掉 alert ★★★
    // 原因：你的 request.js (全局拦截器) 已经弹过一次了，这里再弹就是重复。
    // alert(err.msg || "系统错误")  <-- 把这一行删掉或注释掉

  })
      .finally(() => {
        loading.value = false
      })
}

// 注册逻辑
const handleRegister = () => {
  if(!form.value.userName || !form.value.password) return alert("请输入完整信息")
  if(form.value.password !== form.value.confirmPassword) return alert("两次输入的密码不一致")

  loading.value = true

  // 发送注册请求
  request.post('/register', {
    userName: form.value.userName,
    password: form.value.password
  }).then(res => {
    // 业务状态码 200 表示成功
    if(res.code === 200) {
      alert("注册成功！请登录")
      toggleMode() // 注册成功后切回登录模式
    } else {
      // 业务错误 (如用户名重复，但没抛异常)
      alert(res.msg)
    }
  }).catch(err => {
    // 系统错误 (HTTP 500 等)
    console.error("注册详细报错:", err)

    // ★★★ 改动点：直接显示后端返回的 msg，不要自己加前缀 ★★★
    // 既然后端已经返回了人话 "该用户名已存在"，这里直接弹出来就行
    const errorText = err.msg || err.message || "系统错误"
    alert(errorText)
  }).finally(() => {
    loading.value = false
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100%;
  background: #2d3a4b;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: linear-gradient(135deg, #2d3a4b 0%, #1c2530 100%);
}

.login-card {
  width: 400px;
  background: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 10px 25px rgba(0,0,0,0.2);
}

.title-header { text-align: center; margin-bottom: 30px; }
.title-header h1 { margin: 0; color: #303133; font-size: 26px; }
.title-header p { margin: 10px 0 0; color: #909399; font-size: 14px; }

.input-group { margin-bottom: 20px; }
.input-group label { display: block; margin-bottom: 8px; color: #606266; font-size: 14px; font-weight: bold;}
.input-group input { width: 100%; height: 40px; box-sizing: border-box; border: 1px solid #dcdfe6; border-radius: 4px; padding: 0 10px; }
.input-group input:focus { border-color: #409EFF; outline: none; }

button {
  width: 100%;
  height: 40px;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}
.primary-btn { background-color: #409EFF; }
.primary-btn:hover { background-color: #66b1ff; }

.success-btn { background-color: #67C23A; }
.success-btn:hover { background-color: #85ce61; }

button:disabled { background-color: #a0cfff; cursor: not-allowed; }

.switch-mode { margin-top: 20px; text-align: center; font-size: 14px; color: #606266; }
.switch-mode a { color: #409EFF; cursor: pointer; font-weight: bold; text-decoration: underline; }
</style>