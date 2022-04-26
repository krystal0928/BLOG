<template>
  <div>
    <Background/>
     <!-- login form -->
    <el-form 
      ref="loginFormRef"
      :rules="rules"
      :model="form" 
      label-position="left"
      label-width="80px" 
      class="login-form">
      <h1>个人博客登录</h1>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入密码" type="password" @keyup.enter.native="toNextorSubmit"/>
        <el-link href="javascript:void(0);" @click="toForget">忘记密码</el-link>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.prevent="toNextorSubmit">登录</el-button>
        <el-button @click.prevent="onRegister">注册</el-button>
      </el-form-item>
      <el-link href="#/" class="home-text">返回首页</el-link>
    </el-form>
    <el-dialog
      v-model="dialogVisible"
      title="二次验证绑定"
      :close-on-click-modal="false"
      width="30%" >
      <div>
        <el-row :gutter="10" class="row">
          <el-col :span="8">
            <label style="line-height: 32px;">请输入验证码：</label>
          </el-col>
          <el-col :span="16">
            <el-input v-model="form.code" placeholder="请输入验证码" @keyup.enter.native="onSubmit"/>
          </el-col>
        </el-row>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click.prevent="onSubmit">登录</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { login,loginCheck } from '../api/user.js'
import { useRouter,useRoute } from 'vue-router'
import { ElMessage, FormInstance } from 'element-plus'
import { useStore } from 'vuex'

const store = useStore()
const loginFormRef = ref<FormInstance>();
const router = useRouter()
const route = useRoute()
const dialogVisible = ref(false)
// do not use same name with ref
const form = reactive({
  username: route.query.username,
  password: '',
  code: ''
})
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 10, message: 'Length should be 2 to 10', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: 'Length should be long than 6', trigger: 'blur' },
  ],
})

const toNextorSubmit = () => {
  loginFormRef.value.validate(validate =>{
    if(validate){
      loginCheck(form.username).then(res =>{
        if (res.code === 200) {
          if (res.data == 1){
            dialogVisible.value = true
          } else {
            login(form).then(res =>  {
              if (res.code == 200) {
                router.push('/')
                store.commit('init', res.data)
                ElMessage({
                  showClose: true,
                  message: res.msg,
                  type: 'success',
                })
              }
            })
          }
        }
      })
    }
  })
}

const onSubmit  = ()  =>{
  login(form).then(res =>  {
    if (res.code == 200) {
      router.push('/')
      store.commit('init', res.data)
      ElMessage({
        showClose: true,
        message: res.msg,
        type: 'success',
      })
    }
  })
}
const onRegister = () => {
  router.push('/register')
}
const toForget = () => {
  router.push('/forget')
}
</script>
<style scoped>
.login-form {
  padding: 30px;
  background-color: rgba(255, 255, 255, 0.6);
  width: 380px;
  border-radius: 8px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.home-text {
  float: right;
}
</style>