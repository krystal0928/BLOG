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
            <li v-for="article in articleList" data-growing-title="entryList" class="item">
              <div class="entry" style="margin-bottom: 10px;">
                <div  class="meta-container">
                  <a :href="`#/user/${article.userId}`" target="_blank" rel="" class="user-message">
                    <div class="popover-box user-popover">{{article.userName}}</div>
                  </a>
                  <div class="date">{{article.createTime.substring(0,10)}}</div> 
                  <div class="tag_list">
                    <a href="/tag/Flutter" target="_blank" rel="" class="tag">Flutter</a>
                  </div>
                </div>
                <div class="content-wrapper" style="border-bottom: 1px solid rgba(228, 230, 235, 0.5);">
                  <div class="content-main">
                    <div class="title-row">
                      <a :href="`#/article/${article.id}`" target="_blank" rel="" :title="article.title" class="title">{{article.title}}</a>
                      </div> 
                    <div class="abstract">
                      <a :href="`#/article/${article.id}`" target="_blank" rel="">
                        <div >{{article.description}}</div>
                      </a>
                      </div>
                    <ul class="action-list">
                      <li class="item" @click="tochangeLike(article.id)">
                        <img v-if="article.liked == 1" src="../../assets/like.png"  />
                        <img v-else src="../../assets/unlike.png" />
                        <span>{{article.likeCount}}</span>
                      </li>
                      <li class="item" @click="tochangeCollect(article.id)">
                        <img v-if="article.collected == 1" src="../../assets/collect.png"  />
                        <img v-else src="../../assets/uncollect.png" />
                        <span >{{article.collectCount}}</span>
                      </li>
                      <li class="item">
                        <img src="../../assets/comment.png"  @click="toArticle(article.id)"/>
                        <span >{{article.commentCount}}</span>
                      </li>
                    </ul>
                  </div>
                  <img src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" alt="程序员全职接单一个月的感触" class="lazy thumb" data-src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" loading="lazy" style="">
                </div>
              </div>
            </li>
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
import { ElMessage, ElMessageBox } from 'element-plus';
import { ref, onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router';
import { addArticleLike, selectArticleList, deleteArticleLike, addArticleCollect, deleteArticleCollect } from '../../api/article'
import store from '../../store/store';


let articleList:any = ref([
  {
    id: 1,
    userName: '邹',
    createTime: '1天前',
    title: '为了看Flutter到底有没有人用我竟然222',
    description: 'sdfasdffsd',
    collectCount: 11213,
    likeCount: 123,
    commentCount: 123,
    liked: 0,
    collected: 0,
    cover: 'https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?'
  }
])
const headers = reactive({
  'token': store.getters.getUser?.token || ''
})

const router = useRouter()

onMounted(() => {
  selectArticleList().then(res => {
    if (res.code == 200) {
      articleList.value = res.data
    }
  })
})

const toArticle = (id) =>{
  router.push({
    path: `/article/${id}`
  })
}

const checkToken = () => {
  if (headers.token == '') {
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

const tochangeLike = (articleId) =>{
  if (checkToken()) {
    articleList.value.forEach(element => {
      if(element.id == articleId) {
        if (element.liked == 0) {
          addArticleLike(articleId).then(res => {
            if (res.code == 200) {
              element.liked = 1;
              element.likeCount++;
            }
          })
        } 
        if (element.liked != 0){
          deleteArticleLike(articleId).then(res => {
            if (res.code == 200) {
              element.liked = 0;
              element.likeCount--;
            }
          })
        }
      }
    })
  }
}

const tochangeCollect = (articleId) =>{
  articleList.value.forEach(element => {
    if(element.id == articleId) {
      if (element.collected == 0) {
        addArticleCollect(articleId).then(res => {
          if (res.code == 200) {
            element.collected = 1;
            element.collectCount++;
          }
        })
      } 
      if (element.collected != 0){
        deleteArticleCollect(articleId).then(res => {
          if (res.code == 200) {
            element.collected = 0;
            element.collectCount--;
          }
        })
      }
    }
  })
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
.entry-list {
  width: 100%;
  background-color: #fff;
  position: relative;
}
.entry-list-wrap {
  width: 100%;
}
.entry {
  cursor: pointer;
  position: relative;
  background: #fff;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.meta-container {
  color: #86909c;
}
.meta-container, .meta-row {
  display: flex;
  align-items: center;
}
.meta-container .user-message {
  display: flex;
  align-items: center;
  margin-right: 8px;
  max-width: 162px;
  font-size: 13px;
  line-height: 22px;
  color: #4e5969;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
}
.date:before {
  left: 0;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  display: block;
  width: 1px;
  height: 14px;
  background: #e5e6eb;
  content: " ";
}
.meta-container .date {
  position: relative;
  padding: 0 10px;
  line-height: 22px;
  font-size: 13px;
  flex-shrink: 0;
}
.date:after, .date:before {
  right: 0;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  display: block;
  width: 1px;
  height: 14px;
  background: #e5e6eb;
  content: " ";
}
.tag_lis {
  display: flex;
  align-items: center;
}
.tag_list .tag {
  position: relative;
  flex-shrink: 0;
  font-size: 13px;
  line-height: 22px;
  padding: 0 8px;
  color: #86909c;
}
.content-wrapper {
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid #e5e6eb;
  margin-top: 10px;
  width: 100%;
}
.content-wrapper .content-main {
  flex: 1 1 auto;
  min-width: calc(100% - 140px);
}
.title-row {
  display: flex;
  margin-bottom: 8px;
}
.title {
  font-weight: 700;
  font-size: 16px;
  line-height: 24px;
  color: #1d2129;
  width: 100%;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.abstract {
  margin-bottom: 10px;
}
.abstract a {
  color: #86909c;
  font-size: 13px;
  line-height: 22px;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.action-list, .action-list>.item {
  display: flex;
  align-items: center;
}

.action-list>.item {
  position: relative;
  margin-right: 20px;
  font-size: 13px;
  line-height: 20px;
  color: #4e5969;
  flex-shrink: 0;
}
.action-list>.item img {
  display: block;
  width: 16px;
  height: 16px;
  margin-right: 5px;
  background-size: 100%;
}
.action-list>.item:hover {
  color: #007fff;
}
.thumb {
  flex: 0 0 auto;
  width: 120px;
  height: 80px;
  margin-left: 24px;
  background-color: #fff;
  border-radius: 2px;
}
.right-card {
  flex: 1;
}
.article-card {
  display: flex;
  overflow: hidden;
}
</style>