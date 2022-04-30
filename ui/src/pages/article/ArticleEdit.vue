<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <el-row class="head-row">
          <el-col :span="1"></el-col>
          <el-col :span="2">
            <span class="back" @click="backVisible = true">返回首页</span>
          </el-col>
          <el-col :span="17">
            <div class="article-bar__input-box">
              <input v-model="form.title"  maxlength="100" :placeholder="titleHolder" class="article-bar__title article-bar__title--input text-input" > 
              <span class="article-bar__number"><span class="">{{form.title.length}}</span> /100</span> 
            </div>
          </el-col>
          <el-col :span="1.5">
            <el-button class="draft" @click="toSaveDraft">保存草稿</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button class="publish" @click="toPublishArticle">发布文章</el-button>
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
              <MdEditor class="editor" v-model="form.content" placeholder="请输入内容..."/>
            </el-main>
          </el-container>
        </div>
      </el-main>
      <el-dialog
        v-model="backVisible"
        title="确认是否离开此网站？"
        width="30%"
      >
        <span>系统可能不会保存您所做的更改</span>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="backVisible = false">取消</el-button>
            <el-button type="primary" @click="toHome" >确认</el-button >
          </span>
        </template>
      </el-dialog>
       <el-dialog v-model="dialogFormVisible" title="发布文章">
        <el-form :model="form">
          <el-form-item label="文章封面：" :label-width="formLabelWidth">
            <el-radio-group v-model="form.imgFlag">
              <el-radio :label="0">无</el-radio>
              <el-radio :label="1">有</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="form.imgFlag == 1" label="" :label-width="formLabelWidth">
            <el-upload
              class="avatar-uploader"
              :action="uploadUrl"
              :headers="headers"
              accept="image/jpeg,image/jpg,image/png"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              >
              <img v-if="coverImg" :src="coverImg" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="文章摘要：" :label-width="formLabelWidth">
            <el-input v-model="form.description" placeholder="请输入内容！"  autocomplete="off" :rows="2" type="textarea" />
          </el-form-item>
          <!-- 0为公开 1为私密 2为仅粉丝可见 -->
          <el-form-item label="文章权限：" :label-width="formLabelWidth">
            <el-radio-group v-model="form.permission">
              <el-radio :label="0">公开</el-radio>
              <el-radio :label="1">私密</el-radio>
              <el-radio :label="2">粉丝可见</el-radio>
            </el-radio-group>
          </el-form-item>
      </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="PublishArticle">确认</el-button>
          </span>
        </template>
      </el-dialog>
    </el-container>
    
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, reactive } from 'vue'
import { useStore, mapGetters } from 'vuex'
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox, UploadProps } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { publishArticle, saveDraft, uploadUrl } from '../../api/article'

const dialogFormVisible = ref(false)
const backVisible = ref(false)
const coverImg = ref('')
const titleHolder = ref("请输入文章标题（2-100个字）")
const formLabelWidth = '140px'

const router = useRouter()
const store = useStore()
const form:any = reactive({
  title: '',
  content: '',
  description: null,
  imgFlag: 0,
  coverImg: null,
  permission: 0
})

const headers = reactive({
  'token': store.getters.getUser?.token || ''
})
const checkArticle = () => {
  if (form.title.length < 2) {
    ElMessage({
      showClose: true,
      message: "文章标题最少2个字!",
      type: 'error',
    })
    return false;
  }
  if (form.content.length == 0) {
    ElMessage({
      showClose: true,
      message: "文章内容不能为空",
      type: 'error',
    })
    return false;
  }
  return true
}
const toSaveDraft = () => {
  if (checkArticle()) {
    saveDraft(form).then(res => {
      if (res.code == 200) {
        dialogFormVisible.value = false
        ElMessage({
          showClose: true,
          message: "文章已保存至草稿箱!",
          type: 'success',
        })
      } 
    })
  }
}
const toPublishArticle = () =>{
  if (checkArticle()) {
    dialogFormVisible.value = true
  }
}
const PublishArticle = () => {
  if (form.description == null) {
    ElMessage({
      showClose: true,
      message: "摘要不能为空!",
      type: 'error',
    })
    return;
  }
  publishArticle(form).then(res => {
    if (res.code ==200) {
      dialogFormVisible.value = false
      ElMessage({
        showClose: true,
        message: "文章已成功发布!",
        type: 'success',
      })
      router.push({
        path: '/home'
      })
    }
  })
  
}
const handleAvatarSuccess: UploadProps['onSuccess'] = (response, uploadFile ) => {
  coverImg.value = URL.createObjectURL(uploadFile.raw!)
  if(response.code == 200) {
    form.coverImg = response.data[0]
  } else {
    ElMessage({
      showClose: true,
      message: "图片上传失败，请重试！",
      type: 'error',
    })
  }
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.size / 1024 / 1024 > 3) {
    ElMessage.error('图片大小需小于3MB!')
    return false
  }
  // coverImg.value = URL.createObjectURL(rawFile)
  return true
}

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


</script>

<style>
.el-main{
  padding:0;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
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
  text-align: left;
}
</style>