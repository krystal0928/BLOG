<template>
  <div class="app-container">
    <el-form ref="form" :inline="true" :model="query" label-width="auto">
      <el-form-item label="分类名称">
        <el-input v-model="query.typeName" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSearch">搜索</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="onAdd">新增</el-button>
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
      <el-table-column label="分类名称">
        <template slot-scope="scope">
        <el-tooltip
          effect="dark"
          :content="scope.row.typeName"
          placement="top-start">
          <span class="text-item">{{ scope.row.typeName }}</span>
        </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="创建时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_ut" label="修改时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="left" label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row)">删除</el-button>
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
    <!-- 编辑区 -->
    <el-dialog title="编辑文章分类" :visible.sync="dialogVisible">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="分类名称" prop="typeName">
          <el-input v-model="form.typeName" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getArticleTypeList, editArticleType, deleteArticleType } from '@/api/article-type'

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
        typeName: ''
      },
      total: 0,
      dialogVisible: false,
      form: {},
      rules: {
        typeName: [
          { required: true, message: '请输入文章分类名称', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getArticleTypeList(this.query).then(res => {
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
    onAdd() {
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.form = {...row}
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('此操作将彻底删除文章分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteArticleType(row.id).then(res => {
          if (res.code === 200) {
            this.$message.success(res.msg)
            this.fetchData()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
    onSubmit() {
      this.$refs['form'].validate((valid) => {
        if (!valid) {
          return false
        }
        editArticleType(this.form).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false
            this.$message.success(res.msg)
            this.fetchData()
          }
        })
      })
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
