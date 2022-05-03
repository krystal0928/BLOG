import { createRouter, createWebHashHistory } from 'vue-router'

// 1. 定义路由组件.
// 也可以从其他文件导入
const Login = () => import ('../pages/Login.vue')
const Register = () => import('../pages/Register.vue')
const Layout = () => import('../pages/home/Layout.vue')
const Home = () => import('../pages/home/Home.vue')
const Forget = () => import('../pages/Forget.vue')
const Change = () => import('../pages/Change.vue')
const BindTFA = () => import('../pages/BindTFA.vue')
const ArticleEdit = () => import('../pages/article/ArticleEdit.vue')
const ArticleContent = () => import('../pages/article/ArticleContent.vue')

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes = [
    { 
        path: '/', component: Layout, redirect: '/home', children: [
        { path: '/home', component: Home },
        { path: '/article/:id', component: ArticleContent }
    ]},
    { path: '/article-edit', component: ArticleEdit },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
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