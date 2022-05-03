<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <Header></Header>
      </el-header>
      <el-main>
        <!-- 组件占位 -->
        <RouterView></RouterView>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import Header from './Header.vue'

</script>

<style scoped>
.common-layout {
  min-width: 680px;
}
.header {
  height: 60px;
  padding: 0px;
  width: auto;
  background-color: rgba(241, 241, 241, 0.739);
  border-bottom: 1px solid rgb(177, 176, 176);
  /* box-shadow: 1px 1px 1px rgb(160, 158, 158); */
}
</style>