<template>
  <div>
    <li v-for="article in articleList" data-growing-title="entryList" class="item">
      <div class="entry" style="margin-bottom: 10px;">
        <div  class="meta-container">
          <a :href="`/user/${article.userId}`" class="user-message">
            <div class="popover-box user-popover">{{article.userName}}</div>
          </a>
          <div class="date">{{article.createTime}}</div>
          <div class="tag_list">
            <a href="/tag/Flutter" rel="" class="tag">Flutter</a>
          </div>
        </div>
        <div class="content-wrapper" style="border-bottom: 1px solid rgba(228, 230, 235, 0.5);">
          <div class="content-main">
            <div class="title-row">
              <a :href="`/article/${article.id}?userId=${article.userId}`" :title="article.title" class="title">{{article.title}}</a>
              </div>
            <div class="abstract">
              <a :href="`/article/${article.id}?userId=${article.userId}`" >
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
              <li v-if="editAble" class="item">
                <el-dropdown>
                  <el-icon><more-filled /></el-icon>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item @click="toEditArticle(article.id)">编辑</el-dropdown-item>
                      <el-dropdown-item @click="toDeleteArticle(article.id)">删除</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </li>
            </ul>
          </div>
          <img :src="article.coverImg" loading="lazy" style="height:84px;">
        </div>
      </div>
    </li>
    <el-pagination
      small
      background
      layout="prev, pager, next"
      :total="pagination.total"
      :current-page="pagination.pageNo"
      :page-size="pagination.pageSize"
      @current-change="handleCurrentChange"
      class="mt-4"
    />
  </div>
</template>
<script lang="ts" setup>
import {
  MoreFilled,
} from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus';
import { computed, onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { addArticleCollect, addArticleLike, deleteArticleCollect, deleteArticleLike, articleListPublic, articleListPersonal, getCollectArticle, articleListFocus, deleteArticle } from '../../api/article';

const props = defineProps(['permission', 'userId', 'orderFlag'])

const emit = defineEmits(['update'])

const router = useRouter()
const store = useStore()

const user: any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)

let editAble = ref(false)

const logInUserId = user.value.token?.split(',')[0]

const checkEditAble = () => {
  editAble.value = (logInUserId == props.userId)
}

const pagination: any = reactive({
  pageNo: 1,
  pageSize: 10,
  total: 0,
  orderFlag: props.orderFlag || 'likeCount',
  loginUserId: logInUserId,
  userId: props.userId || 0
})
const articleList: any = ref([])

onMounted(() => {
  checkEditAble()
  loadArticclelList()
})

const loadArticclelList = () => {
  if (props.permission === 'public') {
    articleListPublic({...pagination}).then(res => {
      if (res.code == 200) {
        articleList.value = res.data
        pagination.total = Number(res.total)
      }
    })
  }
  if (props.permission === 'focus') {
    articleListFocus({...pagination}).then(res => {
      if (res.code == 200) {
        articleList.value = res.data
        pagination.total = Number(res.total)
      }
    })
  }
  if (props.permission === 'personal') {
    pagination.status = 1
    articleListPersonal({...pagination}).then(res => {
      if (res.code == 200) {
        articleList.value = res.data
        pagination.total = Number(res.total)
      }
    })
  }
  if (props.permission === 'draft') {
    pagination.status = 0
    articleListPersonal({...pagination}).then(res => {
      if (res.code == 200) {
        articleList.value = res.data
        pagination.total = Number(res.total)
      }
    })
  }
  if (props.permission === 'collect') {
    getCollectArticle({...pagination}).then(res => {
      if (res.code == 200) {
        articleList.value = res.data
        pagination.total = Number(res.total)
      }
    })
  }
}

const handleCurrentChange = (val) => {
  pagination.pageNo = val
  loadArticclelList()
}

const toArticle = (id, userId) =>{
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
              // 通知父组件
              emit('update')
            }
          })
        }
        if (element.liked != 0){
          deleteArticleLike(articleId).then(res => {
            if (res.code == 200) {
              element.liked = 0;
              element.likeCount--;
              // 通知父组件
              emit('update')
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
              // 通知父组件
              emit('update')
            }
          })
        }
        if (element.collected != 0){
          deleteArticleCollect(articleId).then(res => {
            if (res.code == 200) {
              element.collected = 0;
              element.collectCount--;
              // 通知父组件
              emit('update')
            }
          })
        }
      }
    })
  }
}

// 编辑文章
const toEditArticle = (id) => {
  router.push({
    path: '/article-edit',
    query: {id}
  })
}

// 删除文章
const toDeleteArticle = (id) => {
   ElMessageBox.confirm('是否确认删除文章？',
      '警告！',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
    ).then(() => {
      deleteArticle(id).then(res => {
        if (res.code == 200) {
          // 重新加载文章信息
          loadArticclelList()
          // 通知父组件
          emit('update')
        }
      })
    }).catch(() => {})
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