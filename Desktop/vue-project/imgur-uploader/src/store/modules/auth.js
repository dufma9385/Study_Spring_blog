import qs from 'qs';
import router from '@/router';
import cookies from 'vue-cookies';

const state = {
  token: cookies.get('imgur_token'),
};

const getters = {
  isLoggedIn: state => !!state.token,
};

const mutations = {
  setToken(state, token) {
    state.token = token;
  },
};

const actions = {
  logout({ commit }) {  
    // state.token 값 null 로 바꾸기.
    commit('setToken', null);
    cookies.remove('imgur_token');
  },
  login() {
    const ROOT_URL = 'https://api.imgur.com';
    const CLIENT_ID = process.env.VUE_APP_CLIENT_ID;
    const queryString = {
      client_id: CLIENT_ID,
      response_type: 'token',
    };
    const fullUrl = `${ROOT_URL}/oauth2/authorize?${qs.stringify(queryString)}`
    window.location.href = fullUrl;
  },
  
  finalizeLogin({ commit }, hashString) {
    const queryObject = qs.parse(hashString.replace('#', ''));
    commit('setToken', queryObject.access_token);
    cookies.set('imgur_token', queryObject.access_token)
    router.push('/');
  }
};

export default {
  state, getters, mutations, actions
}

// `http://localhost:8081/oauth2/callback
// #
// access_token=b8403596df8833c3a38898e4432dd23c4cecbe39
// &
// expires_in=315360000
// &
// token_type=bearer
// &
// refresh_token=0f2bfff86bf47c0db5694cf2bde0a1e0ded5ec7e
// &
// account_username=eduneo
// &
// account_id=116383075`