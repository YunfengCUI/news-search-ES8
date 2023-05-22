import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexView from '../views/IndexView.vue'
import {Message} from "element-ui";
Vue.use(VueRouter)
import My_Global from '@/store/global.variable'
const AdminId = My_Global.adminUserId
const routes = [
  {
    path: '/',
    name: 'index',
    component: IndexView,
    redirect:'/home',
    children: [
      {
        path: '/home',
        component: () => import('@/views/HomeView'),
        hidden: true
      },
      {
        path: '/search/result',
        component: () => import('@/views/search/SearchResult'),
        hidden: true
      },
      {
        path: '/watch/news',
        name: 'watch',
        component: () => import('@/views/search/WatchNews'),
        hidden: true
       },
      {
        path: '/personal',
        name: 'personalCenter',
        component: () => import('@/views/PersonalCenter/PersonalCenter'),
        hidden: true,
        children: [
          {
            path: '/',
            component: () => import('@/views/PersonalCenter/PersonalInformation')
            // hidden: true
          },
          {
            path: '/information',
            name: 'PersonalInformation',
            component: () => import('@/views/PersonalCenter/PersonalInformation'),
            hidden: true
          },
          {
            path: '/collection',
            name: 'PersonalCollection',
            component: () => import('@/views/PersonalCenter/PersonalCollection'),
            hidden: true
          },
          {
            path: '/manageUser',
            name: 'ManagementUser',
            component: () => import('@/views/PersonalCenter/ManagementUser'),
            hidden: true,
            beforeEnter: (to, from,next) => {
              // reject the navigation
              if (sessionStorage.getItem('UserId')!==AdminId) {
                Message({
                  message: '您的用户权限不够!  将为您导航到首页',
                  type: "warning"
                })
                setTimeout(() =>{
                  next('/home')
                },1000)

              }
              else next()
            }

          }
        ]
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/Index'),
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if (sessionStorage.getItem('UserId')!== null) {
          next('/home')
      }
      else next()
    }
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/register/Index'),
  }
]
const router = new VueRouter({
  mode: 'history',
  routes
})
router.beforeEach(async (to, from, next) => {
  if (to.path ==='/home' || to.path === '/register' || to.path === '/login') {
    next()
  }
  else if (to.path !== '/login' && sessionStorage.getItem('UserId')==null  )
  {
    next('/login')
  }
    else next()
  // 如果用户未能验证身份，则 `next` 会被调用两次
})
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router
