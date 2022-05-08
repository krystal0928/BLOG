<template>
  <div class="user-wrap">
    <div class="context">
      <div class="left">
        <div class="user-info card">
          <div class="user-img">
            <img src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp">
          </div>
          <div class="user-text">
            <h1>{{ userInfo.username }}</h1>
            <span class="text">{{ userInfo.motto }}</span>
          </div>
          <div class="user-edit">
            <el-button v-if="editAble" type="primary" @click="editUserInfo">编辑资料</el-button>
            <div v-else>
              <el-button v-if="userInfo.focused == 0" type="primary" @click="follow(userInfo.id)">关注</el-button>
              <el-button v-if="userInfo.focused == 1" type="primary" @click="follow(userInfo.id)">取消关注</el-button>
            </div>
          </div>
        </div>
        <el-tabs v-model="activeName" class="user-tabs card" @tab-click="handleClick">
          <el-tab-pane label="文章" name="first">
            <ArticleItem permission="personal" :user-id="userId"></ArticleItem>
          </el-tab-pane>
          <el-tab-pane label="收藏" name="second">收藏</el-tab-pane>
          <el-tab-pane label="关注" name="third">
            <UserItem></UserItem>
          </el-tab-pane>
          <el-tab-pane label="粉丝" name="fouth">
            <UserItem></UserItem>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="right">
        <div class="user-data card">
          <span class="data-title">个人成就</span>
          <el-divider style="margin: 5px 0" />
          <div class="data-stats">
            <span class="stats-text">文章被点赞 {{ userInfo.likeCount }}</span>
            <span class="stats-text">文章被收藏 {{ userInfo.collectCount }}</span>
          </div>
        </div>
        <div class="follow card">
          <a href="javascript:void(0)" class="border-r" @click="tabPannel('third')">
            关注<span class="follow-text">{{ userInfo.focusCount }}</span>
          </a>
          <a href="javascript:void(0)" @click="tabPannel('fouth')">
            粉丝<span class="follow-text">{{ userInfo.fansCount }}</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ElMessageBox, TabsPaneContext } from 'element-plus'
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { addUserFocus, deleteUserFocus, getUserVoById } from '../../api/user';
import ArticleItem from '../article/ArticleItem.vue';
import UserItem from './UserItem.vue';

const route = useRoute()
const router = useRouter()
const store = useStore()

const user: any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)

const userId = route.params.id

const activeName = ref('first')

let editAble = ref(false)

// 检查是否可以编辑资料
const checkEditAble = () => {
  const logInUserId = user.value.token?.split(',')[0]
  editAble.value = (logInUserId == userId)
}

const userInfo:any = ref({})

// 加载用户信息
const loadUserInfo = () => {
  const logInUserId = user.value.token?.split(',')[0] || 0
  getUserVoById(logInUserId, userId).then(res => {
    if (res.code == 200) {
      userInfo.value = res.data
    }
  })
}

// 检查是否登录
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

// 编辑资料
const editUserInfo = () => {

}

// 关注
const follow = (userId) => {
  if (checkToken()) {
    if(userInfo.value.Id != userId) {
      if (userInfo.value.focused == 0) {
        addUserFocus(userId).then(res => {
          if (res.code == 200) {
            userInfo.value.focused = 1;
            userInfo.value.focusCount++;
          }
        })
      }
      if (userInfo.value.focused == 1){
        deleteUserFocus(userId).then(res => {
          if (res.code == 200) {
            userInfo.value.focused = 0;
            userInfo.value.focusCount--;
          }
        })
      }
    }
  }
}

onMounted(() => {
  checkEditAble()
  loadUserInfo()
})

// 跳转 tab pannel
const tabPannel = (pannel) => {
  activeName.value = pannel
}

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
  height: 100px;
}
.user-img {
  flex: 0 0 auto;
  margin-right: 20px;
}
.user-img img {
  width: 90px;
  height: 90px;
  border-radius: 50%;
}
.user-text {
  flex: 1 1 auto;
  display: flex;
  flex-direction: column;
  margin-top: 20px;
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
  height: 100px;
}
.data-title {
  font-size: 16px;
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
  font-size: 14px;
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