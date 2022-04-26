import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import store from './store/store.js'
import 'element-plus/dist/index.css'

createApp(App).use(router).use(store).mount('#app')
