<template>
  <div class="common-layout">
    <el-container>
      <el-main>
        <MdEditor v-model="text"/>
      </el-main>
    </el-container>
  </div>
</template>


<script lang="ts" setup>
import { ref } from 'vue'
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'

const text = ref("请输入内容...")

</script>


<style scoped>

</style>