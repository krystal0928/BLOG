<template>
  <div class="card">
    <h3 class="title">文章分类</h3>
    <li class="box-card" v-for="item in list">
      <div class="signin-tip signin" >
        <a :href="`/article/type/${item.id}`">{{item.typeName}}</a>
      </div>
    </li>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { articleTypeList } from '../../api/articleType';

const list: any = ref({})

const loadArticleTypeList = () => {
  const param = {
    pageNo: 1,
    pageSize: 20
  }
  articleTypeList(param).then(res => {
    if (res.code === 200) {
      list.value = res.data
    }
  })
}

onMounted(() => {
  loadArticleTypeList()
})
</script>
<style scoped>
.title {
  margin: 0;
  padding-bottom: 10px;
  border-bottom: 1px solid #e2e2e2;
}
.box-card {
  margin-top: 10px;
}
</style>