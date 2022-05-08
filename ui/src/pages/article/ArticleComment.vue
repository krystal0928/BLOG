<template>
  <div class="comment-wrap card">
    <span class="title">评论</span>
    <div class="comment">
      <img class="head-img" src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp">
      <el-input v-model="comment.content" type="textarea" :autosize="{ minRows: 3, maxRows: 3 }" placeholder="请留言..." @keydown="checkToken"></el-input>
    </div>
    <el-button class="btn" type="primary" @click="sendComment" :disabled="!logIn">发送</el-button>
    <el-divider />
    <span class="title">评论列表</span>
    <div class="comment-list">
      <div class="level1">
        <img class="comment-head-img" src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp">
        <div class="context">
          <div class="box">
            <div class="user">
              <span class="name">名称</span>
              <time class="time">时间</time>
            </div>
            <p class="content">评论内容.......</p>
            <div class="action">
              <el-button>回复评论</el-button>
            </div>
          </div>
          <div class="level2">
            <img class="comment-head-img" src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp">
            <div class="context">
              <div class="box">
                <div class="user">
                  <span class="name">名称</span>
                  <time class="time">时间</time>
                </div>
                <p class="content">评论内容.......</p>
                <div class="action">
                  <el-button>回复评论</el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ElMessageBox } from 'element-plus';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { addComment } from '../../api/article';

const props = defineProps(['articleId'])
const router = useRouter()
const store = useStore()

const logIn = computed(
  mapGetters(['getLogIn']).getLogIn.bind({ $store: store })
)
const user:any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)

const comment: any = ref({})

onMounted(() => {
  comment.value.articleId = props.articleId
})

// 检查用户是否登录
const checkToken = () => {
  if (!logIn.value) {
    ElMessageBox.confirm('登录之后才可以发布评论哦！',
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

// 发布评论
const sendComment = () => {
  if (!checkToken()) {
    return false
  }
  addComment(comment.value).then(res => {
    if (res.code == 200) {
      // 评论成功, 刷新评论列表
    }
  })
}
</script>
<style scoped>
.comment-wrap {
  margin-top: 20px;
}
.title {
  font-size: 18px;
  line-height: 30px;
  font-weight: 600;
  color: #252933;
}
.comment {
  display: flex;
}
.head-img {
  flex: 0 0 auto;
  height: 40px;
  border-radius: 50%;
  place-self: center;
  margin-right: 10px;
}
.btn {
  margin-top: 10px;
  position: relative;
  left: calc(100% - 60px);
}
.comment-list {
  margin-top: 10px;
}
.level1 {
  display: inline-flex;
  width: 100%;
}

.comment-head-img {
  flex: 0 0 auto;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}
.context {
  display: inline-flex;
  flex-direction: column;
  width: 100%;
}
.box {
  display: inline-flex;
  flex-direction: column;
}
.user {
  display: inline-flex;
}
.name {
  flex: 1 0 auto;
  font-weight: 550;
  font-size: 15px;
  color: #252933;
}
.time {
  color: #8a919f;
  flex: 0 0 50px;
  place-items: top;
}
.content {
  font-weight: 400;
  font-size: 14px;
  line-height: 2rem;
  color: #515767;
  margin: 8px;
}
.level2 {
  margin-top: 10px;
  padding: 16px;
  background: rgba(247,248,250,.7);
  border-radius: 4px;
}
</style>