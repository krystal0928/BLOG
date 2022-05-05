<template>
  <div>
    <li v-for="article in articleList" data-growing-title="entryList" class="item">
      <div class="entry" style="margin-bottom: 10px;">
        <div  class="meta-container">
          <a href="#"  target="_blank" rel="" class="user-message">
            <div class="popover-box user-popover">{{article.userName}}</div>
          </a>
          <div class="date">{{article.createTime}}</div> 
          <div class="tag_list">
            <a href="/tag/Flutter" target="_blank" rel="" class="tag">Flutter</a>
          </div>
        </div>
        <div class="content-wrapper" style="border-bottom: 1px solid rgba(228, 230, 235, 0.5);">
          <div class="content-main">
            <div class="title-row">
              <a :href="`#/article/${article.id}?userId=${article.userId}`" target="_blank" rel="" :title="article.title" class="title">{{article.title}}</a>
              </div> 
            <div class="abstract">
              <a :href="`#/article/${article.id}?userId=${article.userId}`" target="_blank" rel="">
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
                <img src="../../assets/comment.png"  @click="toArticle(article.id,article.userId)"/>
                <span >{{article.commentCount}}</span>
              </li>
            </ul>
          </div>
          <img src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" alt="程序员全职接单一个月的感触" class="lazy thumb" data-src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/29092e57c0aa49be99cfdc7c8b3f5ae8~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?" loading="lazy" style="">
        </div>
      </div>
    </li>
  </div>
</template>
<script lang="ts" setup>
import { ElMessageBox } from 'element-plus';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { addArticleCollect, addArticleLike, deleteArticleCollect, deleteArticleLike, selectArticleList } from '../../api/article';

const router = useRouter()
const store = useStore()
const user: any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)

const articleList: any = ref([])

onMounted(() => {
  selectArticleList().then(res => {
    if (res.code == 200) {
      articleList.value = res.data
    }
  })
})

const toArticle = (id,userId) =>{
  console.log(id)
  router.push({
    path: `/article/${id}`,
    query: {userId}
  })
}

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
    }).catch(() => {})
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
  if (checkToken()) {
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
}

</script>
<style scoped>

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
</style>