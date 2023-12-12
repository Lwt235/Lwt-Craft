import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/index/Index'
import Note from '../views/project/Note'
import empty_note from '../views/empty/empty_note'
import AddNumber from '../views/project/AddNumber'
import Test from '../views/project/Test'
import Upload from '../views/project/Upload'
import Community from '../views/project/Community'
import empty_community from '../views/empty/empty_community'
import Register from '../views/index/Register'
import Reset from '../views/index/Reset'
import Question from '../views/index/Question'

const routes = [
  {
    path: '/',
    name: 'Index',
    meta: { hidden: true },
    component: Index
  },
  {
    path: '/Register',
    name: 'Register',
    component: Register
  },
  {
    path: '/Reset',
    name: 'Reset',
    component: Reset
  },
  {
    path: '/Question',
    name: 'Question',
    component: Question
  },
  {
    path: '/empty_note',
    name: 'empty_note',
    meta: { hidden: true },
    component: empty_note
  },
  {
    path: '/empty_community',
    name: 'empty_community',
    meta: { hidden: true },
    component: empty_community
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
  },
  {
    path: '/Upload',
    name: 'Upload',
    component: Upload
  },
  {
    path: '/Community',
    name: 'Community',
    component: Community
  }
]


const routerHistory = createWebHistory()
const router = createRouter({
  history: routerHistory,
  routes
})
export default router
