<template>
  <div>
    <el-form 
      ref="bindTFAFormRef"
      :rules="rules"
      :model="form" 
      label-width="120px" 
      class="login-form">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入新密码" type="password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.prevent="onConfirm" >确认</el-button>
      </el-form-item>
    </el-form>
    <el-dialog
      v-model="dialogVisible"
      title="二次验证绑定"
      :close-on-click-modal="false"
      width="30%" >
      <span>请扫描二维码，并输入验证码</span>
      <qrcode-vue v-if="state.url" :value="state.url" :size="state.size" level="H" />
      <el-input v-model="state.code" placeholder="请输入验证码"/>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="onBindTFA">Confirm</el-button >
      </span>
    </template>
  </el-dialog>
    
  </div>
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance } from 'element-plus';
import { reactive, ref} from 'vue'
import QrcodeVue from 'qrcode.vue'
import {  bindTFA, generateCode } from '../api/user.js'
import { useRoute,useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const bindTFAFormRef = ref<FormInstance>()
const dialogVisible = ref(false)
// do not use same name with ref
const form = reactive({
  username: route.query.username,
  password: '',
})
const state = reactive({
  url: '',
  secret: '',
  size: 100,
  code: ''
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
            state.url = res.url
            state.secret = res.secret
            dialogVisible.value = true
          }
        })
    }
  })
}

const onBindTFA = () => {
  const param = {
    username: form.username,
    secret: state.secret,
    code: state.code}
    bindTFA(param).then(res => {
      if(res.code == 200) {
        ElMessage({
          showClose: true,
          message: res.msg,
          type: 'success',
        })
        dialogVisible.value = false
        router.push("/home")
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