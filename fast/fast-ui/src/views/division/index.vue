<template>
  <div class="layout-wrapper">
    <nav class="top-navbar">
      <div class="nav-content">
        <div class="logo">🚀 Fast Admin</div>
        <div class="menu-items">
          <a v-if="isAdmin" @click="goHome" class="menu-item">用户管理</a>
          <a class="menu-item active">行政区划</a>
          <a @click="handleLogout" class="menu-item logout">退出</a>
        </div>
      </div>
    </nav>

    <main class="main-content">
      <div class="content-card">
        <div class="toolbar-header">
          <div class="search-area">
            <input v-model="queryParams.divName" placeholder="输入区划名称..." class="search-input" />
            <button @click="handleSearch" class="btn-primary">查询</button>
          </div>
          <button v-if="isAdmin" @click="handleAdd" class="btn-success">
            + 新增区划
          </button>
        </div>

        <div class="table-container">
          <table class="standard-table">
            <thead>
            <tr>
              <th>编码</th>
              <th>区划名称</th>
              <th>责任人</th>
              <th>联系电话</th>
              <th>办公地址</th>
              <th v-if="isAdmin" width="150">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in dataList" :key="item.id">
              <td>{{ item.divCode }}</td>
              <td>
                <a class="link-type" @click="handleDetail(item)">
                  {{ item.divName }}
                </a>
              </td>
              <td>{{ item.manager || '-' }}</td>
              <td>{{ item.phone || '-' }}</td>
              <td>{{ item.address || '-' }}</td>
              <td v-if="isAdmin">
                <button class="btn-text" @click="handleEdit(item)">修改</button>
                <span class="divider">|</span>
                <button class="btn-text-danger" @click="handleDelete(item.id)">删除</button>
              </td>
            </tr>
            </tbody>
          </table>

          <div class="pagination-bar">
            <span class="total-text">共 {{ total }} 条数据</span>
            <div class="page-btns">
              <button class="btn-page" :disabled="queryParams.pageNum <= 1" @click="changePage(-1)">上一页</button>
              <span class="current-page">{{ queryParams.pageNum }}</span>
              <button class="btn-page" :disabled="dataList.length < queryParams.pageSize" @click="changePage(1)">下一页</button>
            </div>
          </div>
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
          <div class="form-row"><label>区划编码</label><input v-model="form.divCode"></div>
          <div class="form-row"><label>名称</label><input v-model="form.divName"></div>
          <div class="form-row"><label>责任人</label><input v-model="form.manager"></div>
          <div class="form-row"><label>电话</label><input v-model="form.phone"></div>
          <div class="form-row"><label>地址</label><input v-model="form.address"></div>
        </div>
        <div class="modal-footer">
          <button @click="cancel" class="btn-default">取消</button>
          <button @click="submitForm" class="btn-primary">保存</button>
        </div>
      </div>
    </div>

    <div v-if="detailOpen" class="modal-mask">
      <div class="modal-box detail-box">
        <div class="modal-header">
          <h3>📜 详细信息 - {{ detailForm.divName }}</h3>
          <span class="close-icon" @click="detailOpen = false">×</span>
        </div>
        <div class="modal-body">
          <div class="detail-grid">
            <div class="detail-item"><span class="label">编码：</span>{{ detailForm.divCode }}</div>
            <div class="detail-item"><span class="label">名称：</span>{{ detailForm.divName }}</div>
            <div class="detail-item"><span class="label">责任人：</span>{{ detailForm.manager || '无' }}</div>
            <div class="detail-item"><span class="label">电话：</span>{{ detailForm.phone || '无' }}</div>
            <div class="detail-item full-width"><span class="label">地址：</span>{{ detailForm.address || '无' }}</div>

            <div class="divider-line"></div>

            <div class="detail-item"><span class="label">创建人：</span>{{ detailForm.createBy || 'System' }}</div>
            <div class="detail-item"><span class="label">创建时间：</span>{{ detailForm.createTime }}</div>
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
import { ref, onMounted, computed } from 'vue'
import request from '@/utils/request'
import { useRouter } from 'vue-router'

const router = useRouter()
const dataList = ref([])
const total = ref(0)
const queryParams = ref({ divName: '', pageNum: 1, pageSize: 10 })

// 编辑弹窗控制
const open = ref(false)
const title = ref("")
const form = ref({})

// 详情弹窗控制
const detailOpen = ref(false)
const detailForm = ref({})

const isAdmin = computed(() => {
  const roles = JSON.parse(localStorage.getItem('roles') || '[]')
  return roles.includes('admin')
})

onMounted(() => { getList() })

const getList = () => {
  request.get('/division/list', { params: queryParams.value }).then(res => {
    const data = res.data || {}
    dataList.value = data.rows || []
    total.value = data.total || 0
  })
}

// 打开详情
const handleDetail = (row) => {
  detailForm.value = { ...row }
  detailOpen.value = true
}

// 其他逻辑保持不变
const handleSearch = () => { queryParams.value.pageNum = 1; getList() }
const changePage = (step) => { queryParams.value.pageNum += step; getList() }
const goHome = () => { router.push('/home') }
const handleLogout = () => { if(confirm("确定退出？")){ localStorage.clear(); router.push('/') } }
const handleAdd = () => { form.value = {}; title.value = "新增区划"; open.value = true }
const handleEdit = (row) => { form.value = { ...row }; title.value = "修改区划"; open.value = true }
const cancel = () => { open.value = false }
const submitForm = () => {
  const method = form.value.id ? 'put' : 'post'
  request[method]('/division', form.value).then(() => { alert("保存成功"); open.value = false; getList() })
}
const handleDelete = (id) => {
  if(confirm("确定删除？")) request.delete(`/division/${id}`).then(() => { alert("删除成功"); getList() })
}
</script>

<style scoped>
/* 保持原有布局样式 */
.layout-wrapper { min-height: 100vh; background: #f0f2f5; display: flex; flex-direction: column; }
.top-navbar { height: 60px; background: #304156; color: white; }
.nav-content { padding: 0 20px; height: 100%; display: flex; align-items: center; justify-content: space-between; }
.logo { font-size: 20px; font-weight: bold; }
.menu-items { display: flex; height: 100%; }
.menu-item { padding: 0 20px; display: flex; align-items: center; cursor: pointer; color: #bfcbd9; font-size: 14px; }
.menu-item:hover, .menu-item.active { color: #fff; background: #263445; }
.main-content { flex: 1; padding: 20px; }
.content-card { background: white; padding: 20px; border-radius: 4px; min-height: calc(100vh - 100px); box-shadow: 0 2px 12px 0 rgba(0,0,0,0.05); }
.toolbar-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.search-area { display: flex; gap: 10px; }
.search-input { width: 250px; border: 1px solid #dcdfe6; border-radius: 4px; padding: 0 10px; height: 32px; }

/* 表格与按钮 */
.standard-table { width: 100%; border-collapse: collapse; }
.standard-table th { background: #fafafa; color: #606266; font-weight: 600; text-align: left; padding: 12px 10px; border-bottom: 1px solid #ebeef5; }
.standard-table td { padding: 12px 10px; border-bottom: 1px solid #ebeef5; color: #606266; font-size: 14px; }
.standard-table tr:hover { background-color: #f5f7fa; }
.link-type { color: #409EFF; cursor: pointer; text-decoration: none; font-weight: 500; }
.link-type:hover { text-decoration: underline; }
.btn-primary { background: #409EFF; color: white; border: none; padding: 0 20px; height: 32px; border-radius: 4px; cursor: pointer; }
.btn-success { background: #13ce66; color: white; border: none; padding: 0 20px; height: 32px; border-radius: 4px; cursor: pointer; }
.btn-text { background: none; border: none; color: #409EFF; cursor: pointer; }
.btn-text-danger { background: none; border: none; color: #F56C6C; cursor: pointer; }
.btn-default { background: #fff; border: 1px solid #dcdfe6; color: #606266; margin-right: 10px; height: 32px; padding: 0 15px; cursor: pointer; }
.divider { color: #eee; margin: 0 5px; }

/* 分页 */
.pagination-bar { margin-top: 20px; display: flex; justify-content: flex-end; align-items: center; }
.total-text { color: #606266; font-size: 13px; margin-right: 15px; }
.btn-page { background: #fff; border: 1px solid #dcdfe6; padding: 5px 12px; border-radius: 2px; cursor: pointer; margin: 0 5px; }
.btn-page:disabled { color: #c0c4cc; cursor: not-allowed; }
.current-page { font-weight: bold; color: #409EFF; margin: 0 5px; }

/* 弹窗通用 */
.modal-mask { position: fixed; inset: 0; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 999; }
.modal-box { background: white; width: 450px; border-radius: 4px; padding-bottom: 20px; }
.modal-header { padding: 15px 20px; border-bottom: 1px solid #ebeef5; display: flex; justify-content: space-between; align-items: center; }
.close-icon { cursor: pointer; font-size: 20px; color: #909399; }
.modal-body { padding: 20px; }
.form-row { margin-bottom: 15px; display: flex; align-items: center; }
.form-row label { width: 70px; text-align: right; margin-right: 15px; color: #606266; font-size: 14px; }
.form-row input { flex: 1; border: 1px solid #dcdfe6; height: 32px; border-radius: 4px; padding: 0 10px; }
.modal-footer { padding: 0 20px; text-align: right; }

/* 详情弹窗特有样式 */
.detail-box { width: 500px; }
.detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
.detail-item { font-size: 14px; color: #606266; }
.detail-item .label { font-weight: bold; color: #303133; margin-right: 5px; }
.full-width { grid-column: span 2; }
.divider-line { grid-column: span 2; height: 1px; background: #ebeef5; margin: 10px 0; }
</style>