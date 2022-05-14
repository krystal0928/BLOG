<template>
  <div class="wapper">
    <div class="left-card">
      <header class="list-header card">
        <div class="container">
          <h2>{{ form.typeName }}</h2>
          <span class="text1">
            该分类下一共有
            <span class="text2">{{ form.articleCount }}</span>
            篇文章
          </span>
        </div>
        <nav class="list-nav">
          <ul class="nav-list left">
            <li class="nav-item">
              <a :class="tabIndex === 0 ? 'tab-active' : ''" @click="showTab0" >推荐</a>
            </li>
            <li class="nav-item">
              <a :class="tabIndex === 1 ? 'tab-active' : ''" @click="showTab1" >最新</a>
            </li>
          </ul>
          <div class="dorp-down-area"></div>
        </nav>
      </header>
      <div class="article-card card">
        <div class="entry-list-wrap">
          <div name="entry-list" tag="div" class="entry-list list">
            <ArticleItem v-if="tabIndex === 0" permission="public" orderFlag="likeCount"></ArticleItem>
            <ArticleItem v-if="tabIndex === 1" permission="public" orderFlag="create_time"></ArticleItem>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { articleType } from '../../api/articleType';
import ArticleItem from  '../article/ArticleItem.vue'

const route = useRoute()
const tabIndex = ref(0)
const form: any = ref({})

const loadArticleType = () => {
  articleType(route.params.id).then(res => {
    if (res.code === 200) {
      form.value = res.data
    }
  })
}

const showTab0 = () => {
  tabIndex.value = 0
}

const showTab1 = () => {
  tabIndex.value = 1
}


onMounted(() => {
  loadArticleType()
})

</script>
<style scoped>
.wapper {
  margin: 20px auto 10px;
  width: 70%;
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-between;
}
.left-card {
  flex: 1 0 100%;
  margin-right: 20px;
}
.list-header {
  padding: 1rem 1rem;
  border-bottom: 1px solid hsla(0,0%,59.2%,.1);
  margin-bottom: 10px;
}
.container {
  width: 400px;
  margin: 0 auto;
  text-align: center;
}
.text1 {
  color: #b4b2b2;
}
.text2 {
  color: #1f1e1e;
  font-weight: 600;
  font-size: 18px;
}
.list-nav {
  justify-content: flex-start!important;
}
.list-header .list-nav, .list-header .nav-list {
  display: flex;
  justify-content: space-between;
}
.nav-list {
  align-items: center;
  line-height: 1;
  display: flex;
  justify-content: space-between;
}
.nav-item {
  position: relative;
  cursor: pointer;
  padding: 0 1.2rem;
  font-size: 1.16rem;
  border-right: 1px solid hsla(0,0%,59.2%,.2);
}
.tab-active {
  color: #409eff;
  font-weight: 600;
}
.entry-list {
  width: 100%;
  background-color: #fff;
  position: relative;
}
.entry-list-wrap {
  width: 100%;
}
</style>