<template>
  <div class="container">
    <img class="img" src="../../assets/logo.png"/>
    <span class="h" @click.prevent="onHome">首页</span>
  </div>
  <div class="search">
    <el-input
      v-model="input"
      class="w-50 m-2"
      placeholder="Please Input" >
      <template #append>
      <el-button :icon="Search" />
      </template>
    </el-input>
  </div>
  <div class="container_right">
    <el-button type="primary" class="btn-r" @click="onLogin" round>登录/注册</el-button>
    <el-dropdown>
      <el-avatar class="avatar"> user </el-avatar>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>个人中心</el-dropdown-item>
          <el-dropdown-item>文章管理</el-dropdown-item>
          <el-dropdown-item>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <el-button type="primary" class="btn-r" round>收藏</el-button>
    <el-button type="primary" :icon="Edit" class="btn-r" round>写文章</el-button>
  </div>
</template> 
<script lang="ts" setup>
import { Search } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { Edit } from '@element-plus/icons-vue'
import { ref } from 'vue';

const input = ref('')

const router = useRouter()
const onLogin = () => {
  router.push('/login')
}
const onHome = () => {
  router.push('/home')
}

</script>
<style scoped>
.container{
  float: left;
  display: flex;
} 
.container>*{
  padding: 0px 5px 0px 5px ;
  font-size: 1.33rem;
  line-height: 60px;
}
.h:hover{
  background-color: rgba(145, 141, 141, 0.739);
  cursor: pointer;
}
.img {
  height: 40px;
  line-height: 40px;
  margin: 10px;
}
.search {
  width: 200px;
  float: left;
  line-height: 60px;
  margin: 0px 40px;
}
.container_right{
  float: right;
  padding: 0px 5px 0px 5px ;
  font-size: 1.33rem;
  line-height: 60px;
}
.container_right>*{
  padding: 0px 5px 0px 5px ;
  font-size: 1.33rem;
  line-height: 60px;
}
.avatar {
  margin: 0 10px;
}
.btn-r {
  font-size: 15px;
}
</style>