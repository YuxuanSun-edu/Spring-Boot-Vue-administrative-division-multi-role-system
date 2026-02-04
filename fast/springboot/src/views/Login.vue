<template>
  <div class="login-wrapper">
    <h2>系统登录</h2>
    <div class="form-item">
      <input v-model="form.userName" placeholder="请输入用户名" />
    </div>
    <div class="form-item">
      <input v-model="form.password" type="password" placeholder="请输入密码" />
    </div>
    <button @click="handleLogin">立即登录</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import request from '@/utils/request'

const form = ref({
  userName: '',
  password: ''
})

const handleLogin = async () => {
  if (!form.value.userName || !form.value.password) {
    alert("请填写完整信息")
    return
  }

  // 调用后端 LoginController 中的 /login 接口
  const res = await request.post('/login', form.value)

  if (res.code === 200) {
    // 1. 存储 Token 和权限数据
    localStorage.setItem('token', res.token)
    localStorage.setItem('roles', JSON.stringify(res.roles))
    localStorage.setItem('permissions', JSON.stringify(res.permissions))

    alert("登录成功！")
    // 2. 这里可以跳转到首页
    // router.push('/index')
  }
}
</script>

<style scoped>
.login-wrapper { width: 300px; margin: 100px auto; text-align: center; }
.form-item { margin-bottom: 20px; }
input { padding: 8px; width: 100%; }
button { width: 100%; padding: 10px; cursor: pointer; }
</style>