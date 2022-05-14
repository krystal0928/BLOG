<template>
  <div class="container">
    <img class="img" src="../../assets/logo.png"/>
    <span class="h" @click.prevent="onHome">首页</span>
  </div>
  <div class="search">
    <el-input
      v-model="title"
      class="w-50 m-2"
      placeholder="Please Input" >
      <template #append>
      <el-button :icon="Search" @click="toSeach" />
      </template>
    </el-input>
  </div>
  <div class="container_right">
    <el-button v-if="!logIn" type="primary" class="btn-r btn" @click="onLogin" round>登录/注册</el-button>
    <el-dropdown v-if="logIn">
      <el-tooltip
        class="box-item"
        effect="dark"
        :content="user.username"
        placement="left-start">
        <el-avatar class="avatar" :src="user.img"></el-avatar>
      </el-tooltip>
      <template #dropdown>
        <el-dropdown-menu>
          <!-- <el-dropdown-item @click="toUser">个人中心</el-dropdown-item> -->
          <el-dropdown-item @click="toChange">修改密码</el-dropdown-item>
          <el-dropdown-item @click="toBindTFA">二次验证绑定</el-dropdown-item>
          <el-dropdown-item @click="logOut">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <el-button v-if="logIn" type="primary" :icon="Edit" class="btn-r btn" round @click.prevent="toUser">个人中心</el-button>
    <el-button v-if="logIn" type="primary" :icon="Edit" class="btn-r btn" round @click.prevent="writeArticle">写文章</el-button>
    <div class="btn-r" style="height:60px"></div>
  </div>
</template>
<script lang="ts" setup>
import { Search } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'
import { Edit } from '@element-plus/icons-vue'
import { inject, ref, watchEffect } from 'vue'
import { computed } from 'vue'
import { useStore, mapGetters } from 'vuex'
import { ElMessageBox } from 'element-plus'
import {checkUserStatus} from '../../api/user'
import bus from '../../bus'

const reload: Function = inject('reload')

const store = useStore()
const logIn = computed(
  mapGetters(['getLogIn']).getLogIn.bind({ $store: store })
)
const user:any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)
const router = useRouter()
const route = useRoute()

const title = ref(route.query.title)

const onLogin = () => {
  router.push('/login')
}
const onHome = () => {
  router.push('/home')
}

const toSeach = () => {
  router.push({
    query: {
      title: title.value
    }
  })
  // 调用refresh事件
  bus.emit('refresh', title.value)
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
    }).catch(() => {

    })
    return false
  }
  return true
}
const toUser = () => {
  if (checkToken()) {
    const userId = user.value.token?.split(',')[0]
    router.push({
      path: `/user/${userId}`
    }).then(_ => {
      reload()
    })
  }
}

const logOut = () => {
  store.commit('logOut')
  router.push({
    path: '/home'
  })
}
const toChange = () => {
  router.push({
    path: '/change',
    query: {
      email: user?.value.email
    }
  })
}
const toBindTFA = () =>{
  checkUserStatus().then(res => {
    if (res.code==200 ) {
      if (res.data==1) {
        ElMessageBox.confirm('您已绑定二次验证码，点击确认重新绑定',
          '警告！',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
        ).then(() => {
          router.push({
            path: '/bindTFA',
            query: {
              username: user.value.username
            }
          })
        }).catch(() => {
        })
      } else {
        router.push({
          path: '/bindTFA',
          query: {
            username: user.value.username
          }
        })
      }
    }
  })
}

const writeArticle = () => {
  router.push({
    path: '/article-edit'
  })
}
</script>
<style scoped>
.container{
  float: left;
  display: flex;
}
.container>*{
  padding: 0px 5px 0px 5px ;
  font-size: 1.33rem;
  line-height: 60px;
}
.h {
  width: 50px;
}
.h:hover{
  background-color: rgba(145, 141, 141, 0.739);
  cursor: pointer;
}
.img {
  height: 40px;
  line-height: 40px;
  margin: 10px;
}
.search {
  flex: auto;
  min-width: 200px;
  float: left;
  line-height: 60px;
  margin: 0px 0 0 20px;
}
.container_right{
  float: right;
  padding: 0px 5px 0px 5px ;
  font-size: 1.33rem;
  line-height: 60px;
  display: flex;
}
.container_right>*{
  padding: 0px 5px 0px 5px ;
  font-size: 1.33rem;
  line-height: 60px;
}
.avatar {
  margin: 10px 10px;
}
.btn-r {
  font-size: 15px;
  margin: auto 5px;
}
.btn {
  width: 100px;
}
</style>