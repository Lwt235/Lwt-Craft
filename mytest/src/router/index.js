import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/index/index'
import Note from '../views/project/Note'
import AddNumber from '../views/project/AddNumber'
import empty_note from '../views/empty/empty_note'
import Test from '../views/project/Test'

const routes = [
  {
    path: '/',
    name: 'Index',
    meta: { hidden: true },
    component: Index
  },
  {
    path: '/empty_note',
    name: 'empty_note',
    meta: { hidden: true },
    component: empty_note
  },
  {
    path: '/Test',
    name: 'Test',
    component: Test
  },
  {
    path: '/AddNumber',
    name: 'AddNumber',
    component: AddNumber
  },
  {
    path: '/Note',
    name: 'Note',
    component: Note
  }
  // {
  //   path: '/dealCenter',
  //   name: 'dealCenter',
  //   //component: MyLayout,
  //   redirect: "/dealCenter/list",
  //   meta:{
  //     title: "处理中心",
  //     hidden: false,    
  //     leaf: true
  //   },
  //   children: [
  //     {
  //       path: "list",
  //       name: "dealCenter_list",
  //       meta: { title: "处理中心列表" },
  //       component: () => import('../views/dealCenter/HomeView.vue')

  //     }
  //   ]
  // },

  // {
  //   path: '/infoCenter',
  //   name: 'infoCenter',
  //   component: MyLayout,
  //   redirect: "/infoCenter/infoOne",
  //   meta:{
  //     title: "消息中心",
  //     hidden: false,    
  //     leaf: false
  //   },
  //   children: [
  //     {
  //       path: "infoOne",
  //       name: "info_one",
  //       meta: { title: "消息一" },
  //       component: () => import('../views/infoCenter/infoOne.vue')
  //     },
  //     {
  //       path: "infoTwo",
  //       name: "info_two",
  //       meta: { title: "消息二" },
  //       component: () => import('../views/infoCenter/infoTwo.vue')
  //     },
  //     {
  //       path: "infoThr",
  //       name: "info_thr",
  //       meta: { title: "消息三" },
  //       component: () => import('../views/infoCenter/infoThr.vue')
  //     }
  //   ]
  // },
  // { //404页面的配置通常放到最末尾，具体查看上面链接
  //   path: '*',
  //   name: 'notFound',
  //   meta:{ hidden: true },
  //   component: notFound
  // },
]


const routerHistory = createWebHistory()
const router = createRouter({
  history: routerHistory,
  routes
})
export default router
