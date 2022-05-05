<template>
  <div>
    <Background/>
    <!-- 忘记密码 -->
    <el-form 
      ref="forgetForm"
      :rules="rules"
      :model="form" 
      label-position="left"
      label-width="80px" 
      class="forget-form">
      <h1>忘记密码</h1>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱">
          <template #append>
            <el-button  @click.prevent="sendCode" :disabled="form.checkEmail">{{form.buttonTitle}}</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="value">
        <el-input v-model="form.value" placeholder="请输入验证码,不区分大小写" type="password" @keyup.enter.native="toChange"/>
      </el-form-item>
      <el-form-item>
        <el-button  @click.prevent="toChange" >下一步</el-button>
      </el-form-item>
      <el-link href="#/login" class="home-text">返回登录</el-link>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { sendForgetEmail } from '../api/user.js'
import { confirmEmail } from '../api/user.js'
import { useRouter } from 'vue-router'
import { ElMessage, FormInstance } from 'element-plus'

const count = ref(60)
const router = useRouter()
const forgetForm = ref<FormInstance>()
// do not use same name with ref
const form = reactive({
  email: '',
  value: '',
  checkEmail: true,
  buttonTitle: "发送验证码",
  checkValue: true,
})
const checkEmail  = (rule: any, value: any, callback: any) => {
  if (value === '') {
    form.checkEmail = true
    callback(new Error('请输入邮箱地址'))
  } 
  setTimeout(() => {
    const verify = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
    if (!verify.test(value)) {
      form.checkEmail = true
      callback(new Error('邮箱格式不正确！'))
    } else {
      form.checkEmail = false
    }
    if (!forgetForm.value) 
      return forgetForm.value.validateField('email', () => null) 
    callback()
  }, 500)
}

const rules = reactive({
  email: [
    { validator: checkEmail, required: true, trigger: 'change' },
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
  ],
  value: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 6, max: 6, message: 'Length should be  6', trigger: 'blur' },
  ],
})

const sendCode = () => {
  sendForgetEmail(form.email).then(res => {
    if (res.code == 200) {
      let emailTimer = setInterval(() =>{
        count.value--
        form.buttonTitle = `${count.value} 秒后重发`
        form.checkEmail = true
        if (count.value <= 0) {
          clearInterval(emailTimer)
          emailTimer = null
          form.buttonTitle =  "重新发送验证码"
          form.checkEmail = false
          count.value = 60
        }
      },1000)
      ElMessage({
        showClose: true,
        message: res.msg,
        type: 'success',
      })
    }
  })
}
const toChange = () => {
  forgetForm.value.validate(validate =>{
    if(validate){
      confirmEmail(form).then(res =>{
        if (res.code == 200) {
          ElMessage({
            showClose: true,
            message: res.msg,
            type: 'success',
          })
          router.push({
            path:'/change',
            query:{
              email: form.email
            }
          });
        }
      })
    }
  })
}
</script>
<style scoped>
h1 {
  margin-bottom: 25px;
  margin-top: 10px;
}
.forget-form {
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