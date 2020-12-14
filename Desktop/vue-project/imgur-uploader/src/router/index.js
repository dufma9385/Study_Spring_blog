import Vue from 'vue';
import VueRouter from 'vue-router';

import AuthHandler from '../views/AuthHandler.vue';
import ImageList from '../views/ImageList.vue';
import UploadForm from '../views/UploadForm.vue';

Vue.use(VueRouter)

const routes = [
  { 
    path: '/oauth2/callback',
    name: 'AuthHandler',
    component: AuthHandler,
  },
  {
    path: '/',
    name: 'ImageList',
    component: ImageList,
  },
  {
    path: '/upload',
    name: 'UploadForm',
    component: UploadForm,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
