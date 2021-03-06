<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="query" label-width="auto">
      <el-form-item label="ID">
        <el-input v-model="query.id" />
      </el-form-item>
      <el-form-item label="用户名称">
        <el-input v-model="query.username" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="query.email" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSearch">搜索</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" width="200">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="用户名称">
        <template slot-scope="scope">
        <el-tooltip
          effect="dark"
          :content="scope.row.username"
          placement="top-start">
          <span class="text-item">{{ scope.row.username }}</span>
        </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="座右铭">
        <template slot-scope="scope">
        <el-tooltip
          effect="dark"
          :content="scope.row.motto"
          placement="top-start">
          <span class="text-item">{{ scope.row.motto }}</span>
        </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" width="200" >
        <template slot-scope="scope">
          <span class="text-item">{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="created_at" label="创建时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="left" label="操作" width="200">
        <template #default="scope">
          <el-button size="small" :disabled="!scope.row.secret" @click="handleEdit(scope.row.id)">关闭二次验证</el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      background
      :current-page="query.pageNo"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="query.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { getUserList, deleteUser, unbindTFA } from '@/api/user'

export default {
  filters: {
  },
  data() {
    return {
      list: [],
      listLoading: true,
      query: {
        pageNo: 1,
        pageSize: 10,
        username: '',
        email: '',
      },
      total: 0
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getUserList(this.query).then(res => {
        if (res.code === 200) {
          this.list = res.data
          this.total = +res.total
          this.listLoading = false
          this.$message.success(res.msg)
        }
      })
    },
    onSearch() {
      this.fetchData()
    },
    handleSizeChange(val) {
      this.query.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.query.pageNo = val
      this.fetchData()
    },
    handleEdit(id) {
      this.$confirm('此操作将关闭用户二次登录验证, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        unbindTFA(id).then(res => {
          if (res.code === 200) {
            this.$message.success(res.msg)
            this.fetchData()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    handleDelete(id) {
      this.$confirm('此操作将彻底删除用户信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(id).then(res => {
          if (res.code === 200) {
            this.$message.success(res.msg)
            this.fetchData()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>
<style scoped>
.pagination {
  margin-top: 20px;
}
</style>
