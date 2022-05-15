import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },
  {
    path: '/article',
    component: Layout,
    redirect: '/article/list',
    name: 'article',
    meta: { title: '文章管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'type-list',
        name: 'article-type-list',
        component: () => import('@/views/article-type/index'),
        meta: { title: '文章分类列表', icon: 'nested' }
      },
      {
        path: 'list',
        name: 'article-list',
        component: () => import('@/views/article/index'),
        meta: { title: '文章列表', icon: 'table' }
      },
      {
        path: 'comment',
        name: 'article-comment',
        component: () => import('@/views/article-comment/index'),
        meta: { title: '评论列表', icon: 'tree' }
      },
      {
        path: 'like',
        name: 'article-like',
        component: () => import('@/views/article-like/index'),
        meta: { title: '点赞列表', icon: 'form' }
      },
      {
        path: 'collect',
        name: 'article-collect',
        component: () => import('@/views/article-collection/index'),
        meta: { title: '收藏列表', icon: 'link' }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/list',
    name: 'user',
    meta: { title: '用户管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: 'user-list',
        component: () => import('@/views/user/index'),
        meta: { title: '用户列表', icon: 'user' }
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/list',
    name: 'admin',
    meta: { title: '系统管理', icon: 'el-icon-cogs' },
    children: [
      {
        path: 'list',
        name: 'admin-list',
        component: () => import('@/views/admin/index'),
        meta: { title: '管理员列表', icon: 'eye' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
