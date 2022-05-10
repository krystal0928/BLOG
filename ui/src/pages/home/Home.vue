<template>
  <div class="wapper">
    <div class="left-card">
      <header class="list-header card" style="display:;">
        <nav role="navigation" class="list-nav">
          <ul class="nav-list left">
            <li class="nav-item">
              <a :class="tabIndex === 0 ? 'tab-active' : ''" href="/" >推荐</a>
            </li>
            <li class="nav-item">
              <a :class="tabIndex === 1 ? 'tab-active' : ''" href="/?sort=newest" >最新</a>
            </li>
            <li class="nav-item" >
              <a :class="tabIndex === 2 ? 'tab-active' : ''" @click="showTab2">关注</a>
            </li>
          </ul>
          <div class="dorp-down-area"></div>
        </nav>
      </header>
      <div class="article-card card">
        <div class="entry-list-wrap">
          <div name="entry-list" tag="div" class="entry-list list">
            <ArticleItem v-if="tabIndex === 0" :permission="permission"></ArticleItem>
            <ArticleItem v-if="tabIndex === 2" :permission="permission"></ArticleItem>
          </div>
        </div>
      </div>
    </div>

    <!-- <div class="right-card">
      <el-card class="box-card">
        <div class="signin-tip signin" >
          <div class="first-line" style="opacity: 1;" >
          </div>
        </div>
      </el-card>
    </div> -->
  </div>
</template>


<script lang="ts" setup>
import { inject, onMounted, ref } from 'vue';
import ArticleItem from '../article/ArticleItem.vue';
const reload: Function = inject('reload')

const permission = ref('public')
const tabIndex = ref(0)

const toChangePermission = (type) =>{
  permission.value = type
  reload()
}

onMounted(()=> {
  showTab0()
})

const showTab0 = () => {
  permission.value = 'public'
  tabIndex.value = 0
}

const showTab2 = () => {
  permission.value = 'focus'
  tabIndex.value = 2
}

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
  flex: 3;
  margin-right: 20px;
}
.list-header {
  padding: 1rem 1rem;
  border-bottom: 1px solid hsla(0,0%,59.2%,.1);
  margin-bottom: 10px;
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
.right-card {
  flex: 1;
}
.article-card {
  display: flex;
  overflow: hidden;
}
</style>