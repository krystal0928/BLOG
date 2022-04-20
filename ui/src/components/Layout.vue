<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="container">
          <img class="img" src="../assets/logo.png"/>
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
          <span class="h" @click.prevent="onLogin">登录/注册</span>
          <!-- <span class="h" @click.prevent="onLogin">二次验证绑定</span> -->
          <span class="h">收藏</span>
          <span class="h">写文章</span>
        </div>
      </el-header>
      <el-main>
        <!-- 组件占位 -->
        <RouterView></RouterView>
      </el-main>
    </el-container>
   
  </div>
</template>


<script lang="ts" setup>
import { ref } from 'vue';
import { Search } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'


const input = ref('')
const router = useRouter()
const onLogin = () => {
  router.push('\login')
}
const onHome = () => {
  router.push('\home')
}

</script>


<style scoped>
.header {
  height: 60px;
  padding: 0px;
  width: auto;
  background-color: rgba(241, 241, 241, 0.739);
}
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
</style>