import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/index/index'
import Note from '../views/project/Note'
import AddNumber from '../views/project/AddNumber'
import empty_note from '../views/empty/empty_note'
import Test from '../views/project/Test'
import Upload from '../views/project/Upload'
import Community from '../views/project/Community'

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
