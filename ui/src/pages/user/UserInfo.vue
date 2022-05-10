<template>
  <div class="user-wrap">
    <div class="context">
      <div class="left">
        <div class="user-info card">
          <div class="user-img">
            <img :src="userInfo.img">
          </div>
          <div class="user-text">
            <h1>{{ userInfo.username }}</h1>
            <span class="text">{{ userInfo.motto }}</span>
          </div>
          <div class="user-edit">
            <el-button v-if="editAble" type="primary" @click="toEditUserInfo">编辑资料</el-button>
            <div v-else>
              <el-button v-if="userInfo.focused == 0" type="primary" @click="follow(userInfo.id)">关注</el-button>
              <el-button v-if="userInfo.focused == 1" type="primary" @click="follow(userInfo.id)">取消关注</el-button>
            </div>
          </div>
        </div>
        <el-tabs v-model="activeName" class="user-tabs card" @tab-click="handleClick">
          <el-tab-pane label="文章" name="first">
            <ArticleItem permission="personal" :user-id="userId" @update="loadUserInfo"></ArticleItem>
          </el-tab-pane>
          <el-tab-pane label="收藏" name="second">
             <ArticleItem permission="collect" :user-id="userId" @update="loadUserInfo"></ArticleItem>
          </el-tab-pane>
          <el-tab-pane label="关注" name="third">
            <template #label>
              <el-badge :value="userInfo.focusCount" class="badge" type="info">
                <span>关注</span>
              </el-badge>
            </template>
            <UserItem action="follow" :user-id="userId" @update="loadUserInfo"></UserItem>
          </el-tab-pane>
          <el-tab-pane label="粉丝" name="fouth">
            <template #label>
              <el-badge :value="userInfo.fansCount" class="badge" type="info">
                <span>粉丝</span>
              </el-badge>
            </template>
            <UserItem action="fans" :user-id="userId" @update="loadUserInfo"></UserItem>
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
    <el-dialog v-model="dialogFormVisible" title="编辑资料">
      <el-form :model="changeInfo">
        <el-form-item label="头像" :label-width="formLabelWidth">
          <el-radio-group v-model="changeInfo.imgFlag">
            <el-radio :label="0">不修改</el-radio>
            <el-radio :label="1">修改</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="changeInfo.imgFlag == 1" label="" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :headers="headers"
            accept="image/jpeg,image/jpg,image/png"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            >
            <img v-if="changeInfo.img" :src="changeInfo.img" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="座右铭" :label-width="formLabelWidth">
          <el-input v-model="changeInfo.motto" placeholder="请输入内容！"  autocomplete="off" :rows="2" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="toUpdateUserInfo">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import { ElMessage, ElMessageBox, TabsPaneContext, UploadProps } from 'element-plus'
import { computed, onMounted, reactive, ref, toRefs } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { addUserFocus, deleteUserFocus, getUserVoById, updateInfo } from '../../api/user';
import { uploadUrl } from '../../api/article'
import ArticleItem from '../article/ArticleItem.vue';
import UserItem from './UserItem.vue';
import { Plus } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const store = useStore()
const dialogFormVisible = ref(false)
const img = ref('')
const formLabelWidth = '140px'

const user: any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)
const headers = reactive({
  'token': store.getters.getUser?.token || ''
})
const userId = route.params.id

const activeName = ref('first')

let editAble = ref(false)

// 检查是否可以编辑资料
const checkEditAble = () => {
  const logInUserId = user.value.token?.split(',')[0]
  editAble.value = (logInUserId == userId)
}

const userInfo:any = ref({})
const changeInfo:any = ref({})
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
const toEditUserInfo = () => {
  changeInfo.value = {...userInfo.value}
  changeInfo.value.imgFlag = 0
  dialogFormVisible.value = true
}

const toUpdateUserInfo = () =>{
  updateInfo(changeInfo.value).then(res => {
    if (res.code == 200) {
      dialogFormVisible.value = false
      ElMessage({
        showClose: true,
        message: "信息修改成功!",
        type: 'success',
      })
      loadUserInfo()
    }
  })
}

// 关注
const follow = (userId) => {
  if (checkToken()) {
    if(userInfo.value.Id != userId) {
      if (userInfo.value.focused == 0) {
        addUserFocus(userId).then(res => {
          if (res.code == 200) {
            userInfo.value.focused = 1;
            userInfo.value.fansCount++;
          }
        })
      }
      if (userInfo.value.focused == 1){
        deleteUserFocus(userId).then(res => {
          if (res.code == 200) {
            userInfo.value.focused = 0;
            userInfo.value.fansCount--;
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

const handleAvatarSuccess: UploadProps['onSuccess'] = (response, uploadFile ) => {
  // img.value = URL.createObjectURL(uploadFile.raw!)
  if(response.code == 200) {
    changeInfo.value.img = response.data[0]
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


</script>
<style>
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
::v-deep .badge .el-badge__content.is-fixed {
  top: 10px;
  right: 0px;
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