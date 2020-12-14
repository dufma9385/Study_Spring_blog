import Vue from 'vue'
import Vuex from 'vuex'

import auth from './modules/auth'
import images from './modules/images'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {},  // 핵심 Data
  getters: {},  // state (가공한) getter
  mutations: {},  // state 를 변경하는 함수(동기)
  actions: {},  // 기타 모든 함수(비동기 가능)
  modules: {  // 쪼개기
    auth, images
  }
})
