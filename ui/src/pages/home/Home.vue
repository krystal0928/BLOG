<template>
  <div class="wapper">
    <div class="left-card">
      <header class="list-header card" style="display:;">
        <nav role="navigation" class="list-nav">
          <ul class="nav-list left">
            <li class="nav-item active">
              <a href="/" >推荐</a>
            </li>
            <li class="nav-item">
              <a href="/?sort=newest" >最新</a>
            </li>
            <li class="nav-item" >
              <a href="/?sort=three_days_hottest" >热榜</a>
            </li>
          </ul> 
          <div class="dorp-down-area"></div>
        </nav>
      </header>
      <div class="article-card card">
        <div class="entry-list-wrap">
          <div name="entry-list" tag="div" class="entry-list list">
            <ArticleItem></ArticleItem>
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
import ArticleItem from '../article/ArticleItem.vue';

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