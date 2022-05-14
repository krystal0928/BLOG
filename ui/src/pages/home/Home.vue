<template>
  <div class="wapper">
    <div class="left-card">
      <header class="list-header card" style="display:;">
        <nav role="navigation" class="list-nav">
          <ul class="nav-list left">
            <li class="nav-item">
              <a :class="tabIndex === 0 ? 'tab-active' : ''" @click="showTab0" >推荐</a>
            </li>
            <li class="nav-item">
              <a :class="tabIndex === 1 ? 'tab-active' : ''" @click="showTab1" >最新</a>
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
            <ArticleItem v-if="tabIndex === 0" :permission="permission" orderFlag="likeCount"></ArticleItem>
            <ArticleItem v-if="tabIndex === 1" :permission="permission" orderFlag="create_time"></ArticleItem>
            <ArticleItem v-if="tabIndex === 2" :permission="permission"></ArticleItem>
          </div>
        </div>
      </div>
    </div>

    <div class="right-card">
      <ArticleType></ArticleType>
    </div>
  </div>
</template>


<script lang="ts" setup>
import { ElMessageBox } from 'element-plus';
import { computed, inject, onMounted, ref, watchEffect } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { articleTypeList } from '../../api/articleType';
import ArticleItem from '../article/ArticleItem.vue';
import ArticleType from '../article/ArticleType.vue';

const store = useStore()
const router = useRouter()
const permission = ref('public')
const tabIndex = ref(0)

const logIn = computed(
  mapGetters(['getLogIn']).getLogIn.bind({ $store: store })
)

const checkToken = () => {
  if (!logIn.value) {
    ElMessageBox.confirm('登录之后才可以查看关注哦！',
      '警告！',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
    ).then(() => {
      router.push({
        path: '/login'
      })
    }).catch(() => {

    })
    return false
  }
  return true
}

onMounted(()=> {
  showTab0()
})

const showTab0 = () => {
  permission.value = 'public'
  tabIndex.value = 0
}

const showTab1 = () => {
  permission.value = 'public'
  tabIndex.value = 1
}

const showTab2 = () => {
  if (checkToken())  {
    permission.value = 'focus'
    tabIndex.value = 2
  }
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
  flex: 1 0 70%;
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
  flex: 0 0 30%;
}
.article-card {
  display: flex;
  overflow: hidden;
}
</style>