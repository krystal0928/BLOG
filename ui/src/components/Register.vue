<template>
  <el-form 
    ref="registerFormRef"
    :rules="rules"
    :model="form" 
    label-width="120px" class="login-form">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入用户名"/>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="form.password" placeholder="请输入密码" type="password"/>
    </el-form-item>
    <el-form-item label="确认密码" prop="password2">
      <el-input v-model="form.password2" placeholder="请再次输入密码" type="password"/>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email" placeholder="请输入邮箱">
        <template #append>
          <el-button  @click.prevent="sendCode" :disabled="form.checkEmail">{{ form.sendEmailTitle }}</el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="value">
      <el-input v-model="form.value" placeholder="请输入验证码,不区分大小写" type="password"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click.prevent="onSubmit" >注册</el-button>
    </el-form-item>
  </el-form>
  

  
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance } from 'element-plus';
import { reactive, ref } from 'vue'
import { confirmEmail, register, sendRegisterEmail } from '../api/user.js'
import { useRouter } from 'vue-router'
import { fa } from 'element-plus/lib/locale';

const router = useRouter()
const registerFormRef = ref<FormInstance>()
// do not use same name with ref
const form = reactive({
  username: '',
  password: '',
  password2: '',
  email: '',
  value: '',
  sendEmailTitle: '发送验证码',
  count: 60,
  checkEmail: true,
})

let count = ref(60)

const checkPassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } 
  setTimeout(() => {
    if (form.password !== '' && value !== form.password) {
      callback(new Error('两次密码不一致'))
    }
    if (!registerFormRef.value) 
      return registerFormRef.value.validateField('password2', () => null) 
    callback()
  }, 500)

}
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
    if (!registerFormRef.value) 
      return registerFormRef.value.validateField('email', () => null) 
    callback()
  }, 500)
}
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 10, message: 'Length should be 3 to 10', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: 'Length should be long than 6', trigger: 'blur' },
  ],
   password2: [
    { validator: checkPassword, required: true, trigger: 'blur' },
    { min: 6, message: 'Length should be long than 6', trigger: 'blur' },
  ],
  email: [
    { validator: checkEmail, required: true, trigger: 'change' },
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
  ],
  value: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 6, max: 6, message: 'Length should be  6', trigger: 'blur' },
  ],
})


const onSubmit = () => {
  registerFormRef.value.validate(validate =>{
    if (validate) {
      confirmEmail(form).then(res =>{
        if (res.code == 200) {
          register(form).then(res => {
            if (res.code == 200){
              ElMessage({
                showClose: true,
                message: '注册成功',
                type: 'success',
              })
              router.push('/login')
            }
          })
        }
      })
    }
  })
}

const sendCode = () => {
  sendRegisterEmail(form.email).then(res => {
    if (res.code == 200) {
      let emailTimer = setInterval(() => {
        count.value = count.value - 1
        form.sendEmailTitle =  `${count.value} 秒后重发`
        form.checkEmail = true
        if (count.value <= 0) {
          clearInterval(emailTimer);
          emailTimer = null;
          form.sendEmailTitle  = '重新发送验证码'
          form.checkEmail = false
          count.value = 60
        }
      }, 1000)
      ElMessage({
        showClose: true,
        message: res.msg,
        type: 'success',
      })
    }
  })
}

</script>
<style scoped>
.login-form {
  margin: 50px auto;
  max-width: 400px
}
</style>