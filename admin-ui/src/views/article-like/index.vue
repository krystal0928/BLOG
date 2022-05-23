<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="query" label-width="auto">
      <el-form-item label="文章标题">
        <el-input v-model="query.articleTitle" />
      </el-form-item>
      <el-form-item label="点赞人">
        <el-input v-model="query.userName" />
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
      highlight-current-row>
      <el-table-column align="center" label="ID" width="200">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="文章标题">
        <template slot-scope="scope">
        <el-tooltip
          effect="dark"
          :content="scope.row.articleTitle"
          placement="top-start">
          <span class="text-item">{{ scope.row.articleTitle }}</span>
        </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="点赞人" width="110" align="center">
        <template slot-scope="scope">
          <span class="text-item">{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="创建时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="修改时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="left" label="操作" width="200">
        <template #default="scope">
          <!-- <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row.id)">删除</el-button>
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
import { deleteArticleLike, getArticleLikeList } from '@/api/article-like'

export default {
  filters: {
  },
  data() {
    return {
      list: [],
      listLoading: true,
      query: {
        pageNo: 1,
        pageSize: 10
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
      getArticleLikeList(this.query).then(res => {
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
    handleDelete(id) {
      this.$confirm('此操作将彻底删除该文章评论, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteArticleLike(id).then(res => {
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
