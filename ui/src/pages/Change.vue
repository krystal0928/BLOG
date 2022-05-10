<template>
  <div>
    <Background/>
    <!-- change form -->
    <el-form 
      ref="changeFormRef"
      :rules="rules"
      :model="form" 
      label-position="left"
      label-width="80px"
      class="change-form">
      <h1>修改密码</h1>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入新密码" type="password"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="password2">
        <el-input v-model="form.password2" placeholder="请再次输入密码" type="password" @keyup.enter.native="onSubmit"/>
      </el-form-item>
          <el-form-item>
        <el-button type="primary" @click.prevent="onSubmit">修改密码</el-button>
      </el-form-item>
      <el-link href="/" class="home-text">返回首页</el-link>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance } from 'element-plus';
import { reactive, ref } from 'vue'
import { change } from '../api/user.js'
import { useRoute,useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const changeFormRef = ref<FormInstance>()
// do not use same name with ref
const form = reactive({
  email: route.query.email,
  password: '',
  password2: '',
})
const checkPassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } 
  setTimeout(() => {
    if (form.password !== '' && value !== form.password) {
      callback(new Error('两次密码不一致'))
    }
    if (!changeFormRef.value) 
      return changeFormRef.value.validateField('password2', () => null) 
    callback()
  }, 500)

}

const rules = reactive({
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: 'Length should be long than 6', trigger: 'blur' },
  ],
   password2: [
    { validator: checkPassword, required: true, trigger: 'blur' },
    { min: 6, message: 'Length should be long than 6', trigger: 'blur' },
  ],
})


const onSubmit = () => {
  changeFormRef.value.validate(validate => {
    if (validate) {
      change(form).then(res => {
          if (res.code == 200){
            ElMessage({
              showClose: true,
              message: '密码修改成功，请重新登录',
              type: 'success',
            })
            router.push('/login')
          }
        })
    }
  })
}

</script>
<style scoped>
.change-form {
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