<template>
  <el-form 
    ref="loginFormRef"
    :rules="rules"
    :model="form" 
    label-width="120px" class="login-form">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入用户名"/>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="form.password" placeholder="请输入密码" type="password"/>
      <span @click="toForget" class="h">忘记密码</span>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click.prevent="onSubmit">登录</el-button>
      <el-button @click.prevent="onRegister">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { login } from '../api/user.js'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { FormInstance } from 'element-plus/lib/components/form';

const loginFormRef = ref<FormInstance>();
const router = useRouter()
// do not use same name with ref
const form = reactive({
  username: '',
  password: ''
})
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 10, message: 'Length should be 3 to 10', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: 'Length should be long than 6', trigger: 'blur' },
  ],
})

const onSubmit = () => {
  loginFormRef.value.validate(validate =>{
    if(validate){
      login(form).then(res =>  {
        if (res.code == 200) {
          router.push('/')
          ElMessage({
            showClose: true,
            message: res.msg,
            type: 'success',
          })
        }
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
  margin: 50px auto;
  max-width: 350px
}
.h:hover{
  color: rgba(54, 142, 0, 0.739);
  cursor: pointer;
}
</style>