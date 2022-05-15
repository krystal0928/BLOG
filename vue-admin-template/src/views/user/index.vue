<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="query" label-width="auto">
      <el-form-item label="标题">
        <el-input v-model="query.title" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status">
          <el-option label="草稿" value="0" />
          <el-option label="已发布" value="1" />
          <el-option label="已修改" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否删除">
        <el-select v-model="query.deleted">
          <el-option label="未删除" value="0" />
          <el-option label="已删除" value="1" />
        </el-select>
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
      <el-table-column align="center" label="ID" width="200">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="标题">
        <template slot-scope="scope">
        <el-tooltip
          effect="dark"
          :content="scope.row.title"
          placement="top-start">
          <span class="text-item">{{ scope.row.title }}</span>
        </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="摘要">
        <template slot-scope="scope">
        <el-tooltip
          effect="dark"
          :content="scope.row.description"
          placement="top-start">
          <span class="text-item">{{ scope.row.description }}</span>
        </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="作者" width="110" align="center">
        <template slot-scope="scope">
          <span class="text-item">{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="状态" width="110" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0" type="info">{{ scope.row.status | statusFilter }}</el-tag>
          <el-tag v-if="scope.row.status == 1" type="success">{{ scope.row.status | statusFilter }}</el-tag>
          <el-tag v-if="scope.row.status == 2" type="warning">{{ scope.row.status | statusFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="是否删除" width="110" align="center">
        <template slot-scope="scope">
          <el-tag type="info">{{ scope.row.deleted | deletedFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="创建时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="left" label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >Delete</el-button
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
import { getArticleList } from '@/api/article'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: '草稿',
        1: '已发布',
        2: '已修改'
      }
      return statusMap[status]
    },
    deletedFilter(deleted) {
      const statusMap = {
        0: '未删除',
        1: '已删除'
      }
      return statusMap[deleted]
    }
  },
  data() {
    return {
      list: [],
      listLoading: true,
      query: {
        pageNo: 1,
        pageSize: 10,
        title: '',
        status: null,
        deleted: null
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
      getArticleList(this.query).then(res => {
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
    }
  }
}
</script>
<style scoped>
.pagination {
  margin-top: 20px;
}
</style>
