<template>
  <div>
    <li v-for="user in userList">
      <div class="item">
        <img src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp" />
        <div class="text">
          <span class="name one-line">{{ user.username }}</span>
          <span class="motto one-line">{{ user.motto }}</span>
        </div>
        <el-button class="btn" type="primary">关注</el-button>
      </div>
    </li>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue'

const userList = ref([{username: 'NIck', motto: '士大夫手动阀示范点发射点发撒法士大夫发阿斯顿发射点发射点发射点更高的分数官方'}])
</script>
<style scoped>
.item {
  display: flex;
}
img {
  height: 50px;
  border-radius: 50%;
}
.one-line {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.text {
  margin: 0 20px;
  display: flex;
  flex: 1 1 auto;
  flex-direction: column;
}
.name {
  flex-direction: column;
  font-size: 16px;
  font-weight: 600;
}
.motto {
  font-size: 14px;
  margin-top: 10px;
}
.btn {
  place-self: flex-end;
}
</style>