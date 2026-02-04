<template>
  <div class="layout-wrapper">
    <nav class="top-navbar">
      <div class="nav-content">
        <div class="logo">🚀 Fast Admin</div>
        <div class="menu-items">
          <a class="menu-item active">用户管理</a>
          <a @click="goDivision" class="menu-item">行政区划</a>
          <a @click="handleLogout" class="menu-item logout">退出</a>
        </div>
      </div>
    </nav>

    <main class="main-content">
      <div class="content-card">
        <div class="toolbar-header">
          <div class="title-box">
            <h2>用户列表</h2>
            <span class="subtitle">点击用户名查看详情</span>
          </div>
          <button @click="handleAdd" class="btn-primary">
            + 新建用户
          </button>
        </div>

        <div class="table-container">
          <table class="standard-table">
            <thead>
            <tr>
              <th width="80">ID</th>
              <th>用户名 (点击详情)</th>
              <th>身份</th>
              <th>性别</th>
              <th>创建时间</th>
              <th width="180">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="user in userList" :key="user.userId">
              <td>{{ user.userId }}</td>
              <td>
                <a class="link-type" @click="handleDetail(user)">
                  {{ user.userName }}
                </a>
              </td>
              <td>
                   <span :class="['tag', user.role === 'admin' ? 'tag-admin' : 'tag-common']">
                     {{ user.role === 'admin' ? '管理员' : '普通用户' }}
                   </span>
              </td>
              <td>{{ user.sex === 0 ? '男' : (user.sex === 1 ? '女' : '未知') }}</td>
              <td>{{ user.createTime }}</td>
              <td>
                <button class="btn-text" @click="handleEdit(user)">编辑</button>
                <span class="divider">|</span>
                <button class="btn-text-danger" @click="handleDelete(user.userId)">删除</button>
              </td>
            </tr>
            </tbody>
          </table>
          <div v-if="userList.length === 0" class="empty-state">暂无数据</div>
        </div>
      </div>
    </main>

    <div v-if="open" class="modal-mask">
      <div class="modal-box">
        <div class="modal-header">
          <h3>{{ title }}</h3>
          <span class="close-icon" @click="cancel">×</span>
        </div>
        <div class="modal-body">
          <div class="form-row"><label>用户名</label><input v-model="form.userName" type="text"></div>
          <div class="form-row"><label>密码</label><input v-model="form.password" type="password" :placeholder="form.userId ? '不改请留空' : '请输入密码'"></div>
          <div class="form-row">
            <label>角色</label>
            <select v-model="form.role">
              <option value="common">普通用户</option>
              <option value="admin">管理员</option>
            </select>
          </div>
          <div class="form-row">
            <label>性别</label>
            <select v-model="form.sex">
              <option :value="0">男</option>
              <option :value="1">女</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button @click="cancel" class="btn-default">取消</button>
          <button @click="submitForm" class="btn-primary">确定</button>
        </div>
      </div>
    </div>

    <div v-if="detailOpen" class="modal-mask">
      <div class="modal-box detail-box">
        <div class="modal-header">
          <h3>👤 用户详情 - {{ detailForm.userName }}</h3>
          <span class="close-icon" @click="detailOpen = false">×</span>
        </div>
        <div class="modal-body">
          <div class="detail-grid">
            <div class="detail-item"><span class="label">ID：</span>{{ detailForm.userId }}</div>
            <div class="detail-item"><span class="label">用户名：</span>{{ detailForm.userName }}</div>
            <div class="detail-item"><span class="label">角色：</span>{{ detailForm.role === 'admin' ? '管理员' : '普通用户' }}</div>
            <div class="detail-item"><span class="label">性别：</span>{{ detailForm.sex === 0 ? '男' : '女' }}</div>

            <div class="divider-line"></div>

            <div class="detail-item full-width"><span class="label">创建时间：</span>{{ detailForm.createTime }}</div>

            <div class="detail-item"><span class="label">修改人：</span>{{ detailForm.updateBy || '-' }}</div>
            <div class="detail-item"><span class="label">修改时间：</span>{{ detailForm.updateTime || '-' }}</div>
          </div>
        </div>
        <div class="modal-footer">
          <button @click="detailOpen = false" class="btn-primary">关闭</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/utils/request'
import { useRouter } from 'vue-router'

const router = useRouter()
const userList = ref([])
const open = ref(false)
const title = ref("")
const form = ref({})

// 详情弹窗控制
const detailOpen = ref(false)
const detailForm = ref({})

onMounted(() => { getList() })

const getList = () => {
  request.get('/user/list').then(res => {
    if (res.code === 200) userList.value = res.data
  })
}

// 打开详情
const handleDetail = (row) => {
  detailForm.value = { ...row }
  detailOpen.value = true
}

// 路由跳转
const goDivision = () => { router.push('/division') }
const handleLogout = () => { if(confirm("确定退出？")){ localStorage.clear(); router.push('/') } }

// 增删改逻辑
const handleAdd = () => { form.value = { sex: 0, role: 'common' }; title.value = "新建用户"; open.value = true }
const handleEdit = (row) => { form.value = { ...row }; title.value = "编辑用户"; open.value = true }
const cancel = () => { open.value = false }

const submitForm = () => {
  const method = form.value.userId ? 'put' : 'post'
  request[method]('/user', form.value).then(res => {
    if(res.code === 200) {
      alert("操作成功");
      open.value = false;
      getList()
    } else {
      alert(res.msg)
    }
  }).catch(err => {
    console.error(err)
    alert("操作失败")
  })
}

const handleDelete = (id) => {
  if(confirm("确定删除？")) request.delete(`/user/${id}`).then(() => { alert("删除成功"); getList() })
}
</script>

<style scoped>
/* 布局样式 */
.layout-wrapper { min-height: 100vh; background: #f0f2f5; display: flex; flex-direction: column; }
.top-navbar { height: 60px; background: #304156; color: white; }
.nav-content { padding: 0 20px; height: 100%; display: flex; align-items: center; justify-content: space-between; }
.logo { font-size: 20px; font-weight: bold; }
.menu-items { display: flex; height: 100%; }
.menu-item { padding: 0 20px; display: flex; align-items: center; cursor: pointer; color: #bfcbd9; font-size: 14px; }
.menu-item:hover, .menu-item.active { color: #fff; background: #263445; }

.main-content { flex: 1; padding: 20px; }
.content-card { background: white; padding: 20px; border-radius: 4px; min-height: calc(100vh - 100px); box-shadow: 0 2px 12px 0 rgba(0,0,0,0.05); }

.toolbar-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; border-bottom: 1px solid #ebeef5; padding-bottom: 20px; }
.title-box h2 { margin: 0; font-size: 18px; color: #303133; }
.subtitle { font-size: 12px; color: #909399; margin-left: 10px; }

/* 表格样式 */
.standard-table { width: 100%; border-collapse: collapse; }
.standard-table th { background: #fafafa; color: #606266; font-weight: 600; text-align: left; padding: 12px 10px; border-bottom: 1px solid #ebeef5; }
.standard-table td { padding: 12px 10px; border-bottom: 1px solid #ebeef5; color: #606266; font-size: 14px; }
.standard-table tr:hover { background-color: #f5f7fa; }
.link-type { color: #409EFF; cursor: pointer; text-decoration: none; font-weight: 500; }
.link-type:hover { text-decoration: underline; }

/* 标签样式 */
.tag { padding: 2px 8px; border-radius: 4px; font-size: 12px; }
.tag-admin { background: #e8f4ff; color: #409EFF; border: 1px solid #d9ecff; }
.tag-common { background: #f4f4f5; color: #909399; border: 1px solid #e9e9eb; }

/* 按钮样式 */
.btn-primary { background: #409EFF; color: white; border: none; padding: 9px 15px; border-radius: 4px; cursor: pointer; }
.btn-default { background: #fff; border: 1px solid #dcdfe6; color: #606266; margin-right: 10px; padding: 9px 15px; cursor: pointer; border-radius: 4px; }
.btn-text { background: none; border: none; color: #409EFF; padding: 0 5px; cursor: pointer; }
.btn-text-danger { background: none; border: none; color: #F56C6C; padding: 0 5px; cursor: pointer; }
.divider { color: #dcdfe6; }
.empty-state { text-align: center; color: #909399; padding: 20px; }

/* 弹窗样式 */
.modal-mask { position: fixed; inset: 0; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 999; }
.modal-box { background: white; width: 450px; border-radius: 4px; padding-bottom: 20px; }
.modal-header { padding: 15px 20px; border-bottom: 1px solid #ebeef5; display: flex; justify-content: space-between; align-items: center; }
.close-icon { cursor: pointer; font-size: 20px; color: #909399; }
.modal-body { padding: 20px; }
.form-row { margin-bottom: 15px; display: flex; align-items: center; }
.form-row label { width: 70px; text-align: right; margin-right: 15px; color: #606266; font-size: 14px; }
.form-row input, .form-row select { flex: 1; border: 1px solid #dcdfe6; height: 32px; border-radius: 4px; padding: 0 10px; }
.modal-footer { padding: 0 20px; text-align: right; }

/* 详情弹窗样式 */
.detail-box { width: 400px; }
.detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
.detail-item { font-size: 14px; color: #606266; }
.detail-item .label { font-weight: bold; color: #303133; margin-right: 5px; }
.full-width { grid-column: span 2; }
.divider-line { grid-column: span 2; height: 1px; background: #ebeef5; margin: 10px 0; }
</style>