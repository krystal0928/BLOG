<template>
  <el-form 
    ref="bindTFAFormRef"
    :rules="rules"
    :model="form" 
    label-width="120px" class="login-form">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入用户名"/>
    </el-form-item>
    <el-form-item label="确认密码" prop="password">
      <el-input v-model="form.password" placeholder="请输入新密码" type="password"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click.prevent="onConfirm">确认</el-button>
    </el-form-item>
  </el-form>
  <div class="qrcode" ref="qrCodeUrl"></div>
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance } from 'element-plus';
import { reactive, ref } from 'vue'
// import QRCode from 'qrcodejs2'
import {  generateCode } from '../api/user.js'
import { useRoute,useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const bindTFAFormRef = ref<FormInstance>()
const qrCodeUrl = ref<any>()
// do not use same name with ref
const form = reactive({
  username: route.query.username,
  password: '',
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


const onConfirm = () => {
  bindTFAFormRef.value.validate(validate => {
    if (validate) {
      generateCode(form).then(res => {
          if (res.code == 200){
            ElMessage({
              showClose: true,
              message: res.msg,
              type: 'success',
            })
            // new QRCode(qrCodeUrl, {
            //   text: res.url, // 需要转换为二维码的内容
            //   width: 100,
            //   height: 100,
            //   colorDark: '#000000',
            //   colorLight: '#ffffff',
            //   correctLevel: QRCode.CorrectLevel.H
            // })
          }
        })
    }
  })
}

</script>

<style scoped>
.qrcode{
  display: inline-block;
}
.qrcode img {
  width: 132px;
  height: 132px;
  background-color: #fff; 
  padding: 6px; 
  box-sizing: border-box;
}
</style>