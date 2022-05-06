<template>
  <div class="user-wrap">
    <div class="context">
      <div class="left">
        <div class="user-info card">
          <div class="user-img">
            <img src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp">
          </div>
          <div class="user-text">
            <h1>用户名称</h1>
            <span class="text">座右铭</span>
            <span class="text">手机号</span>
            <span class="text">邮箱</span>
          </div>
          <div class="user-edit">
            <el-button type="primary">编辑资料</el-button>
          </div>
        </div>
        <el-tabs v-model="activeName" class="user-tabs card" @tab-click="handleClick">
          <el-tab-pane label="文章" name="first">
            <ArticleItem permission="personal"></ArticleItem>
          </el-tab-pane>
          <el-tab-pane label="收藏" name="second">收藏</el-tab-pane>
          <el-tab-pane label="关注" name="third">关注</el-tab-pane>
        </el-tabs>
      </div>
      <div class="right">
        <div class="user-data card">
          <span class="data-title">个人成就</span>
          <el-divider style="margin: 5px 0" />
          <div class="data-stats">
            <span class="stats-text">文章被点赞 12</span>
            <span class="stats-text">文章被收藏 4</span>
          </div>
        </div>
        <div class="follow card">
          <a href="#" class="border-r">
            关注了
            <span class="follow-text">10</span>
          </a>
          <a href="#">
            关注者
            <span class="follow-text">2</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import type { TabsPaneContext } from 'element-plus'
import { ref } from 'vue';
import ArticleItem from '../article/ArticleItem.vue';

const activeName = ref('first')

const handleClick = (tab: TabsPaneContext, event: Event) => {
}
</script>
<style scoped>
.user-wrap {
  width: 70%;
  margin: 20px auto;
}
.context {
  display: flex;
}
.left {
  flex: 1 0 400px;
}
.right {
  flex: 0 0 150px;
  margin-left: 20px;
}
.user-info {
  display: flex;
  height: 150px;
}
.user-img {
  flex: 0 0 auto;
  margin-right: 20px;
}
.user-img img {
  margin-top: 30px;
  width: 90px;
  height: 90px;
  border-radius: 50%;
}
.user-text {
  flex: 1 1 auto;
  display: flex;
  flex-direction: column;
}
.user-text h1 {
  margin-bottom: 10px;
}
.user-edit {
  justify-content: end;
  display: flex;
  flex-direction: column;
}
.user-data {
  width: 100%;
}
.data-title {
  font-size: 14px;
  font-weight: 600;
  color: #31445b;
}
.user-tabs {
  margin-top: 20px
}
.text {
  color: #72777b;
  font-size: 14px;
}
.stats-text {
  color: #000;
  font-size: 12px;
  font-weight: 400;
  margin: 5px 0;
}
.data-stats {
  display: flex;
  flex-direction: column;
}
.follow {
  width: 100%;
  margin-top: 20px;
  display: flex;
}
.follow a {
  flex: 1 1 auto;
  text-align: center;
  font-weight: 500;
  color: #31445b;
}
.border-r {
  border-right: 1px solid #efefef;
}
.follow-text {
  display: block;
}
</style>