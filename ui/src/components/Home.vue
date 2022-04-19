<template>
  <div class="common-layout">
    <el-container>
      <el-main>Main</el-main>
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

</style>