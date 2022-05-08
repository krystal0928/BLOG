<template>
  <div>
    <li v-for="user in userList">
      <div class="item">
        <img src="https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/mirror-assets/1697148e7969d916ec3~tplv-t2oaga2asx-no-mark:180:180:180:180.awebp" />
        <div class="text">
          <a href="javascript:void(0)" class="name one-line" @click="toUser(user.id)">{{ user.username }}</a>
          <span class="motto one-line">{{ user.motto }}</span>
        </div>
        <el-button :disabled="user.id==logInUserId" class="btn" v-if="user.focused == 0" type="primary" @click="follow(user)" >关注</el-button>
        <el-button :disabled="user.id==logInUserId" class="btn" v-if="user.focused == 1" type="primary" @click="follow(user)">取消关注</el-button>
      </div>
    </li>
    <el-pagination
      small
      background
      layout="prev, pager, next"
      :total="pagination.total"
      :current-page="pagination.pageNo"
      :page-size="pagination.pageSize"
      @current-change="handleCurrentChange"
      class="mt-4"
    />
  </div>
</template>
<script lang="ts" setup>
import { ElMessageBox } from 'element-plus';
import { computed, inject, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router';
import { mapGetters, useStore } from 'vuex';
import { getFocusUserList, getFansUserList, addUserFocus, deleteUserFocus } from '../../api/user'

const reload: Function = inject('reload')

const props = defineProps(['userId', 'action'])
const store = useStore()
const router = useRouter()
const emit = defineEmits(['update'])
const user: any = computed(
  mapGetters(['getUser']).getUser.bind({ $store: store })
)


const logInUserId = user.value.token?.split(',')[0]
const pagination = reactive({
  pageNo: 1,
  pageSize: 10,
  total: 0,
  loginUserId: logInUserId,
  userId: props.userId || 0
})

const userList = ref([])

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

onMounted(() => {
  loadUserList()
})

const loadUserList = () => {
  if (props.action === 'follow') {
    getFocusUserList({...pagination}).then(res => {
      if (res.code === 200) {
        userList.value = res.data
      }
    })
  }
  if (props.action === 'fans') {
    getFansUserList({...pagination}).then(res => {
      if (res.code === 200) {
        userList.value = res.data
      }
    })
  }
}

const handleCurrentChange = (val) => {
  pagination.pageNo = val
  loadUserList()
}

const toUser = (userId) => {
  router.push({
    path: `/user/${userId}`
  }).then(_ => {
    reload()
  })
}

// 关注
const follow = (user:any) => {
  if (checkToken()) {
    console.log(user.id)
    if(logInUserId != user.id) {
      if (user.focused == 0) {
        addUserFocus(user.id).then(res => {
          if (res.code == 200) {
            user.focused = 1;
            user.fansCount++;
            // 通知父组件
            emit('update')
          }
        })
      }
      if (user.focused == 1){
        deleteUserFocus(user.id).then(res => {
          if (res.code == 200) {
            user.focused = 0;
            user.fansCount--;
            // 通知父组件
            emit('update')
          }
        })
      }
    }
  }
}
</script>
<style scoped>
.item {
  display: flex;
  padding-top: 10px;
  padding-bottom: 10px;
  margin-bottom: 10px;
  border-bottom: 1px solid rgba(228, 230, 235, 0.5);
}
img {
  height: 50px;
  border-radius: 50%;
}
.one-line {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.text {
  margin: 0 20px;
  display: flex;
  flex: 1 1 auto;
  flex-direction: column;
}
.name {
  flex-direction: column;
  font-size: 16px;
  font-weight: 600;
}
.motto {
  font-size: 14px;
  margin-top: 10px;
}
.btn {
  place-self: flex-end;
}
</style>