import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Cooperator from '@/components/Cooperator'
import Login from '@/components/Login'
import TiStudentView from '@/components/student.vue'
import ProgramManager from '@/components/ProgramManager.vue'
import Register from '@/components/Register.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Hello
    },
    {
      path: '/app',
      name: 'Cooperator',
      component: Cooperator
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/TiStudentView',
      name: 'TiStudentView',
      component: TiStudentView
    },
    {
      path: '/programManager',
      name: 'ProgramManager',
      component: ProgramManager
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
  ]
})
