import { createRouter, createWebHashHistory } from 'vue-router'

// 1. 定义路由组件.
// 也可以从其他文件导入
const Login = import ('../components/Login.vue')
const Register = import('../components/Register.vue')
const Home = import('../components/Home.vue')
const Forget = import('../components/Forget.vue')
const Change = import('../components/Change.vue')
const BindTFA = import('../components/BindTFA.vue')

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes = [
    { path: '/', redirect: '/home'},
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/home', component: Home },
    { path: '/forget', component: Forget },
    { path: '/change', component: Change },
    { path: '/bindTFA', component: BindTFA },
]

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router;