<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <Header></Header>
      </el-header>
      <div class="div-body">
        <!-- 组件占位 -->
        <RouterView></RouterView>
      </div>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import Header from './Header.vue'

</script>

<style scoped>
.header {
  display: flex;
  min-width: 800px;
  position: fixed;
  z-index: 3;
  height: 60px;
  padding: 0px 15%;
  width: 100%;
  background-color: rgba(241, 241, 241, 1);
  border-bottom: 1px solid rgb(177, 176, 176);
  /* box-shadow: 1px 1px 1px rgb(160, 158, 158); */
}
.div-body {
  padding-top: 60px;
}
</style>