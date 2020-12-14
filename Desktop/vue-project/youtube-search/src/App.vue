<template>
  <div id="app">
    <SearchBar @input="input" v-bind:results="results"/>
    <div id="app2">
      <MainVideo v-bind:results="results"/>
      <VideoList v-bind:results="results"/>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import SearchBar from './components/SearchBar.vue'
import MainVideo from './components/MainVideo.vue'
import VideoList from './components/VideoList.vue'

export default {
  name: 'App',
  components: {
    SearchBar,
    MainVideo,
    VideoList
  },
  data(){
    return{
      results: [],
    }
  },
   methods: {
    input(a){
      console.log(a);
      
      //입력된 검색어를 가지고
      const baseURL = 'https://www.googleapis.com/youtube/v3/search'
      const apiKey = process.env.VUE_APP_YOUTUBE_API_KEY
      
      // 유투브에 요청을 보내고
      axios
      .get(baseURL, {
        params: {
          //key, part, q
          key: apiKey,
          part: 'snippet',
          type: 'video',
          q: a,
          maxResults: 10
        }
      })
      .then(res=>{
        console.log(res.data.items)
        this.results = res.data.items
      })
    }
}
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
#app2{
  display:flex;
}
</style>
