<template>
  <div>
    <div class="container">
      <div class="column-view">
        <div class="main-area article-area">
          <article class="article card">
            <h1 class="article-title">
              {{article.title}}
            </h1>
            <div class="author-info-block">
              <a :href="`/user/${article.userId}`" class="avatar-link">
                <img v-if="!article.userImg" src="https://i03piccdn.sogoucdn.com/cafc10742b9b77da" class="avatar">
                <img v-if="article.userImg" :src="article.userImg" class="avatar">
                <!-- <img src="https://p9-passport.byteacctimg.com/img/user-avatar/fc7d615744af612d3010a85f7db27f6f~300x300.image" data-src="https://p9-passport.byteacctimg.com/img/user-avatar/fc7d615744af612d3010a85f7db27f6f~300x300.image" loading="lazy" class="lazy avatar avatar"> -->
              </a>
              <div class="author-info-box">
                <div class="author-name">
                  <a :href="`/user/${article.userId}`" class="username ellipsis">
                    <span class="name" style="max-width: 128px;">{{article.userName}}</span>
                  </a>
                </div>
                <div class="meta-box">
                  <time datetime="2021-11-26T02:56:50.000Z" class="time">{{article.creatTime}}</time>
                  <span class="views-count">
                    &nbsp;点赞 {{article.likeCount}}
                  </span>
                  <span class="views-count">
                    &nbsp;&nbsp;收藏 {{article.collectCount}}
                  </span>
                </div>
              </div>
              <el-button :disabled="loginUserId == article.userId" class="follow-button" @click="toChangeFocus(article.userId)" v-if="reader.focused == 0">关注</el-button>
              <el-button :disabled="loginUserId == article.userId" class="follow-button" @click="toChangeFocus(article.userId)" v-if="reader.focused == 1">取消关注</el-button>
            </div>
            <div v-html="article.content" class="editor-content-view"></div>
          </article>
          <!-- 评论 -->
          <ArticleComment id="comment" :article-id="article.id" @update="reloadArticle"></ArticleComment>
        </div>
        <div class="sidebar">
          <div class="fixed card">
            <div class="sidebar-block author-block">
              <a :href="`/user/${article.userId}`" class="user-item item">

                <img v-if="!article.userImg" src="https://i03piccdn.sogoucdn.com/cafc10742b9b77da" class="avatar">
                <img v-if="article.userImg" :src="article.userImg" class="avatar">
                <!-- <img src="https://p9-passport.byteacctimg.com/img/user-avatar/fc7d615744af612d3010a85f7db27f6f~300x300.image" class="lazy avatar avatar" loading="lazy"> -->
                <div class="info-box" >
                  <a :href="`/user/${article.userId}`" class="username">
                    <span class="name" style="max-width: 128px;">{{reader.username}}</span>
                  </a>
                  <div :title="reader.motto" class="motto" >{{reader.motto}}</div>
                </div>
              </a>
              <div class="stat-item item" >
                <span class="content" >获得点赞&nbsp;{{reader.likeCount}}</span>
              </div>
              <div class="stat-item item">
                <span class="content">文章被收藏&nbsp;{{reader.collectCount}}</span>
              </div>
              <div class="stat-item item">
                <span class="content">粉丝&nbsp;{{reader.focusCount}}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="article-suspended-panel">

        <div class="panel-btn with-badge" @click="tochangeLike(article.id)" >
          <el-badge :value="article.likeCount" class="badge" type="info">
            <img class="sprite-icon" v-if="article.liked == 1" src="../../assets/like.png"  />
            <img class="sprite-icon" v-else src="../../assets/unlike.png" />
          </el-badge>
        </div>

        <div class="panel-btn with-badge" @click="tochangeCollect(article.id)" >
          <el-badge :value="article.collectCount" class="badge" type="info">
            <img class="sprite-icon" v-if="article.collected == 1" src="../../assets/collect.png"  />
            <img class="sprite-icon" v-else src="../../assets/uncollect.png" />
          </el-badge>
        </div>
        <div class="panel-btn with-badge"  >
          <el-badge :value="article.commentCount" class="badge" type="info">
            <a  href="#comment">
              <img class="sprite-icon" src="../../assets/comment.png"  />
            </a>
          </el-badge>
        </div>


        <el-backtop  class="backtop" :visibility-height="180">UP </el-backtop>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ElMessage, ElMessageBox } from 'element-plus';
import { ref, onMounted, reactive, computed } from 'vue'
import { useRoute } from 'vue-router'
import { mapGetters, useStore } from 'vuex';
import { addArticleCollect, addArticleLike, deleteArticleCollect, deleteArticleLike, getPublishArticleById } from '../../api/article'
import { addUserFocus, deleteUserFocus, getUserVoById } from '../../api/user';
import router from '../../router/router';
import ArticleComment from './ArticleComment.vue'

const route = useRoute()
let article:any = ref({
})
let reader:any = ref({})

const store = useStore()
const user:any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)

const loginUserId = user.value.token?.split(',')[0]

const reloadArticle = () => {
  getPublishArticleById(route.params.id).then(res => {
    if (res.code == 200) {
      article.value = res.data
    }
  })
}

onMounted(() => {
  reloadArticle()
  getUserVoById(loginUserId, route.query.userId).then(res => {
    if (res.code == 200) {
      reader.value = res.data
    }
  })
})

const checkToken = () => {
  if (!user.value.token) {
    ElMessageBox.confirm('登录已过期，请重新登录！',
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

const toChangeFocus = (articleUserId) => {
  if (checkToken()) {
    if(reader.value.Id != articleUserId) {
      if (reader.value.focused == 0) {
        addUserFocus(articleUserId).then(res => {
          if (res.code == 200) {
            ElMessage.success(res.msg)
            reader.value.focused = 1;
            reader.value.focusCount++;
          }
        })
      }
      if (reader.value.focused == 1){
        deleteUserFocus(articleUserId).then(res => {
          if (res.code == 200) {
            ElMessage.success(res.msg)
            reader.value.focused = 0;
            reader.value.focusCount--;
          }
        })
      }
    }
  }
}
const tochangeLike = (articleId) =>{
  if (checkToken()) {
    if(article.value.id == articleId) {
      if (article.value.liked == 0) {
        addArticleLike(articleId).then(res => {
          if (res.code == 200) {
            ElMessage.success(res.msg)
            article.value.liked = 1;
            article.value.likeCount++;
            reader.value.likeCount++;
          }
        })
      }
      if (article.value.liked != 0){
        deleteArticleLike(articleId).then(res => {
          if (res.code == 200) {
            ElMessage.success(res.msg)
            article.value.liked = 0;
            article.value.likeCount--;
            reader.value.likeCount--;
          }
        })
      }
    }
  }
}

const tochangeCollect = (articleId) =>{
  if(article.value.id == articleId) {
    if (article.value.collected == 0) {
      addArticleCollect(articleId).then(res => {
        if (res.code == 200) {
          ElMessage.success(res.msg)
          article.value.collected = 1;
          article.value.collectCount++;
          reader.value.collectCount++;
        }
      })
    }
    if (article.value.collected != 0){
      deleteArticleCollect(articleId).then(res => {
        if (res.code == 200) {
          ElMessage.success(res.msg)
          article.value.collected = 0;
          article.value.collectCount--;
          reader.value.collectCount--;
        }
      })
    }
  }
}
</script>
<style>
.editor-content-view {
  border-radius: 5px;
  padding: 0 10px;
  margin-top: 20px;
  overflow-x: auto;
}

.editor-content-view p,
.editor-content-view li {
  white-space: pre-wrap; /* 保留空格 */
}
.editor-content-view ul li {
  list-style: disc;
}

.editor-content-view ol li {
  list-style: auto;
}

.editor-content-view blockquote {
  border-left: 8px solid #d0e5f2;
  padding: 10px 10px;
  margin: 10px 0;
  background-color: #f1f1f1;
}

.editor-content-view code {
  font-family: monospace;
  background-color: #eee;
  padding: 3px;
  border-radius: 3px;
}
.editor-content-view pre>code {
  display: block;
  padding: 10px;
}

.editor-content-view table {
  border-collapse: collapse;
}
.editor-content-view td,
.editor-content-view th {
  border: 1px solid #ccc;
  min-width: 50px;
  height: 20px;
}
.editor-content-view th {
  background-color: #f1f1f1;
}

.editor-content-view ul,
.editor-content-view ol {
  padding-left: 20px;
}

.editor-content-view input[type="checkbox"] {
  margin-right: 5px;
}
</style>
<style scoped>
.container {
  position: relative;
  margin: 20px auto;
  width: 60%;
}
.column-view {
  padding: 0 0 8rem;
  display: flex;
}
.article-area {
  margin-bottom: 1.5rem;
}
.main-area {
  flex: 1 0 400px;
  position: relative;
  max-width: 100%;
  box-sizing: border-box;
}
.article {
  position: relative;
  padding-top: 2.667rem;
  padding-bottom: 3.33rem;
  z-index: 1;
}
.article-title{
  margin: 0 0 1.667rem;
  font-size: 2.667rem;
  font-weight: 600;
  line-height: 1.31;
  color: #252933;
}
.fixed {
  position: fixed;
}
.author-info-block {
  display: flex;
  align-items: center;
}
.author-name {
  margin-left: 0.5rem;
  height: 1.5rem;
}
.author-info-block .avatar {
  flex: 0 0 auto;
  margin-right: 1rem;
  width: 3.333rem;
  height: 3.333rem;
  border-radius: 50%;
}
.author-info-block, .team-follow {
  margin-bottom: 1.667rem;
}
.author-info-block .author-info-box {
  min-width: 0;
  flex-grow: 1;
  flex: 1;
  min-height: 43px;
}
.follow-button {
  margin: 0 0 0 auto;
  padding: 0 1rem;
  height: 34px;
  font-size: 14px;
  color: #1e80ff;
  background: rgba(30,128,255,.05);
  border: 1px solid rgba(30,128,255,.3);
  border-radius: 4px;
  display: flex;
  align-items: center;
}
.sidebar {
  flex: 0 1 240px;
  position: relative;
  margin-left: 20px;
}
.sidebar .sidebar-block {
  margin-bottom: 20px;
}
.sidebar-block {
  position: relative;
  margin-bottom: 1.5rem;
  border-radius: 2px;
}
.author-block {
  border-radius: 4px;
  background: #fff;
}
.user-item {
  border-bottom: 1px solid #e4e6eb;
  padding-bottom: 1.416rem;
}
.item {
  display: flex;
  align-items: center;
}
.user-item .avatar {
  flex: 0 0 auto;
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
}
.user-item .info-box {
  flex: 1 1 auto;
  margin-left: 1.333rem;
}
.username, .motto {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.stat-item:first-of-type {
  margin-top: 1.416rem;
}
.stat-item {
  margin-top: 0.667rem;
}

.stat-item .content {
  font-size: 1.167rem;
  color: #252933;
  font-weight: 400;
}

.article-suspended-panel {
  position: fixed;
  margin-left: -45px;
  top: 140px;
  z-index: 2;
}
.panel-btn {
  position: relative;
  margin-bottom: 30px;
  width: 30px;
  height: 30px;
  background-color: #fff;
  background-position: 50%;
  background-repeat: no-repeat;
  border-radius: 50%;
  box-shadow: 0 2px 4px 0 rgb(0 0 0 / 4%);
  cursor: pointer;
  text-align: center;
  font-size: 1.67rem;
}
.sprite-icon {
  width: 30px;
  color: #8a919f;
  height: 30px;
}

.panel-btn.share-btn .share-popup .share-item {
  display: flex;
  align-items: center;
  height: 44px;
  padding: 0 15px;
}
.panel-btn.share-btn .share-popup .share-item .share-icon {
  color: #8a919f;
  width: 20px;
  height: 20px;
  font-size: 1.67rem;
}
.panel-btn.share-btn .share-popup {
  /* display: block; */
  display: none;
  position: absolute;
  top: 0;
  flex-direction: column;
  left: calc(100% + 14px);
  z-index: 30;
  background: #fff;
  border-radius: 4px;
  padding: 9px 0;
  width: -webkit-max-content;
  width: -moz-max-content;
  width: max-content;
  box-shadow: 0 8px 24px rgb(81 87 103 / 16%);
}
.panel-btn.share-btn .share-popup .share-item .share-item-title {
  margin-left: 8px;
  font-size: 14px;
  color: #515767;
}
.wechat-qrcode {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  top: 0;
  left: calc(100% + 8px);
  border-radius: 4px;
  background-color: #fff;
  padding: 20px 0 16px;
  box-shadow: 0 8px 24px rgb(81 87 103 / 16%);
  width: 120px;
}
.wechat-qrcode .wechat-qrcode-img {
  width: 80px;
  height: 80px;
}
.wechat-qrcode .wechat-qrcode-title {
  font-size: 14px;
  line-height: 22px;
  color: #515767;
  margin-top: 12px;
}
</style>