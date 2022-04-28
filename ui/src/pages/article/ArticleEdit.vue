<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <el-row class="head-row">
          <el-col :span="1"></el-col>
          <el-col :span="2">
            <span class="back" @click="toHome">返回首页</span>
          </el-col>
          <el-col :span="17">
            <div class="article-bar__input-box">
              <input maxlength="100" placeholder="请输入文章标题（5~100个字）" class="article-bar__title article-bar__title--input text-input"> 
              <span class="article-bar__number"><span class="">5</span>/100</span> 
              <span class="article-bar__number" aria-hidden="true" style="display: none;">还需要输入0个字</span>
            </div>
          </el-col>
          <el-col :span="1.5">
            <el-button class="draft" @click="saveDraft">保存草稿</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button class="publish">发布文章</el-button>
          </el-col>
          <el-col :span="1">
            <el-dropdown v-if="logIn" class="head-img">
              <el-tooltip
                class="box-item"
                effect="dark"
                :content="user.username"
                placement="left-start">
                <el-avatar class="avatar">{{user.username?.substring(0,1)}}</el-avatar>
              </el-tooltip>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>个人中心</el-dropdown-item>
                  <el-dropdown-item>文章管理</el-dropdown-item>
                  <!-- <el-dropdown-item @click="toChange">修改密码</el-dropdown-item>
                  <el-dropdown-item @click="toBindTFA">二次验证绑定</el-dropdown-item>
                  <el-dropdown-item @click="logOut">退出登录</el-dropdown-item> -->
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <div class="common-layout">
          <el-container>
            <el-main class="main" style="margin: 0;padding: 0;">
              <MdEditor class="editor" v-model="text"/>
            </el-main>
          </el-container>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue'
import { useStore, mapGetters } from 'vuex'
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import { useRouter } from 'vue-router';

const text = ref("请输入内容...")

const router = useRouter()
const store = useStore()
const logIn = computed(
  mapGetters(['getLogIn']).getLogIn.bind({ $store: store })
)
const user:any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)

const toHome = () => {
  router.push({
    path: '/'
  })
}

const saveDraft = () =>{
  console.log(text.value)
}
</script>

<style>
.el-main{
  padding:0;
}
</style>
<style scoped>
.header {
  height: 60px;
  padding: 0px;
  width: auto;
  background-color: rgba(241, 241, 241, 0.739);
  border-bottom: 1px solid rgb(177, 176, 176);
}
.back {
  cursor: pointer;
}
.article-bar__input-box {
  display: flex;
  width: 95%;
  margin: 12px 16px 9px 8px;
  border: 1px solid #ccc;
  border-radius: 21px;
  background-color: #fff;
  position: relative;
}
.article-bar__input-box .article-bar__title--input {
  width: 100%;
  margin-left: 0px;
  border-radius: 18px;
  padding:5px;
  padding-right: 88px;
  font-size: 14px;
  line-height: 24px;
  background-color: #fff;
}
.article-bar__input-box .article-bar__number {
  line-height: 36px;
  font-size: 16px;
  margin-right: 8px;
  position: absolute;
  right: 0;
}
.head-row, .head-img{
  line-height: 60px;
}
.draft {
  margin-right: 16px;
  padding: 0 16px;
  font-size: 16px;
  color: #fc5531;
  border: 1px solid #fc5531;
  border-radius: 18px;
  white-space: nowrap;
  background-color: #fff;
}
.draft:hover {
  background-color: #fff5f2;
}
.publish {
  padding: 0 16px;
  font-size: 16px;
  color: #fff;
  border: none;
  border-radius: 18px;
  white-space: nowrap;
  background: #fc5531;
}
.publish:hover {
  background: #fc1944;
}
.editor {
  height: calc(100vh - 60px);
  width: 100vw;
}
</style>