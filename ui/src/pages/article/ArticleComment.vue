<template>
  <div class="comment-wrap card">
    <span class="title">评论</span>
    <div class="comment">
      <img v-if="!user.img" src="https://i03piccdn.sogoucdn.com/cafc10742b9b77da" class="head-img" >
      <img v-if="user.img" :src="user.img" class="head-img" >
      <!-- <img src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp"> -->
      <el-input v-model="comment.content" type="textarea" :autosize="{ minRows: 3, maxRows: 3 }" placeholder="请留言..." @keydown="checkToken"></el-input>
    </div>
    <el-button class="btn" type="primary" @click="sendComment" :disabled="!logIn">发送</el-button>
    <el-divider style="margin: 10px 0;" />
    <span class="title">评论列表</span>
    <div class="comment-list">
      <div class="level1" v-for="comment in commentList">
        <img v-if="!comment.userImg" src="https://i03piccdn.sogoucdn.com/cafc10742b9b77da" class="comment-head-img1" >
        <img v-if="comment.userImg" :src="comment.userImg" class="comment-head-img1" >
        <div class="context">
          <div class="box">
            <div class="user">
              <span class="name">{{ comment.userName }}</span>
              <time class="time">{{ comment.createTime }}</time>
            </div>
            <p class="content">{{ comment.content }}</p>
            <div class="action">
              <a class="reply" href="javascript:void(0)" @click="toReply(comment.id)">回复评论</a>
              <a v-if="comment.userId == logInUserId" class="remove" href="javascript:void(0)" @click="toRemove(comment.id)">删除评论</a>
            </div>
          </div>
          <div class="level2" v-for="comment2 in comment.list">
            <img v-if="!comment2.userImg" src="https://i03piccdn.sogoucdn.com/cafc10742b9b77da" class="comment-head-img2" >
            <img v-if="comment2.userImg" :src="comment2.userImg" class="comment-head-img2" >
            <!-- <img class="comment-head-img2" src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp"> -->
            <div class="context">
              <div class="box">
                <div class="user">
                  <span class="name">{{ comment2.userName }}</span>
                  <time class="time">{{ comment2.createTime }}</time>
                </div>
                <p class="content">{{ comment2.content }}</p>
                <p class="parent">回复：“ {{ comment2.parentContent }} ”</p>
                <div class="action">
                  <a class="reply" href="javascript:void(0)" @click="toReply(comment2.id)">回复评论</a>
                  <a v-if="comment2.userId == logInUserId" class="remove" href="javascript:void(0)" @click="toRemove(comment2.id)">删除评论</a>
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
import { ElMessage, ElMessageBox } from 'element-plus';
import { computed, ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { addComment, deleteComment, getCommentList, getSecondLevelCommentList } from '../../api/article';

const props = defineProps(['articleId'])
const emit = defineEmits(['update'])
const router = useRouter()
const store = useStore()

const logIn = computed(
  mapGetters(['getLogIn']).getLogIn.bind({ $store: store })
)
const user:any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)

const logInUserId = user.value.token?.split(',')[0]
const pagination:any = ref({
  pageNo: 1,
  pageSize: 10
})

const comment: any = ref({})
const commentList: any = ref([])

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
      ElMessage.success(res.msg)
      emit("update")
      loadCommentList()
    }
  })
}

// 回复评论
const toReply = (pid) => {
  ElMessageBox.prompt('请输入评论内容', '回复评论', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
  }).then(({ value }) => {
    const param = {
      articleId: props.articleId,
      content: value,
      pid: pid
    }
    addComment(param).then(res => {
      if (res.code == 200) {
        // 评论成功, 刷新评论列表
        ElMessage.success(res.msg)
        loadCommentList()
        emit('update')
      }
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消回复评论',
    })
  })
}

// 刷新评论列表
const loadCommentList = () => {
  getCommentList(pagination.value).then(res => {
    if (res.code == 200) {
      commentList.value = res.data
      commentList.value.forEach(element => {
        let param: any = {...pagination.value}
        param.pid = element.id
        // 加载子级评论
        getSecondLevelCommentList(param).then(res2 => {
          if (res2.code == 200) {
            element.list = res2.data
          }
        })
      })
    }
  })
}

// 删除评论
const toRemove = (commentId) => {
  ElMessageBox.confirm('是否确认删除该评论',
    '警告！',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    deleteComment(commentId).then(res => {
      if (res.code == 200) {
        ElMessage.success(res.msg)
        loadCommentList()
        emit('update')
      }
    })
  }).catch(() => {

  })
}

watchEffect(() => {
  comment.value.articleId = props.articleId
  pagination.value.articleId = props.articleId

  if (props.articleId) {
    loadCommentList()
  }
})
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
  width: 40px;
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
  margin-top: 10px;
}

.comment-head-img1 {
  flex: 0 0 auto;
  width: 40px;
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
  flex-wrap: wrap;
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
  flex: 0 0 auto;
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
  display: inline-flex;
  margin: 10px 0 20px;
  padding: 16px;
  background: rgba(247,248,250,.7);
  border-radius: 4px;
}
.comment-head-img2 {
  flex: 0 0 auto;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}
.parent {
  background: #f2f3f5;
  border: 1px solid #e4e6eb;
  box-sizing: border-box;
  border-radius: 4px;
  padding: 0 12px;
  line-height: 36px;
  height: 36px;
  font-size: 14px;
  color: #8a919f;
  margin: 0 0 8px;
}
.action {
  display: flex;
}
.reply {
  flex: 1 0 auto;
}
.remove {
  flex: 0 0 auto;
}
</style>