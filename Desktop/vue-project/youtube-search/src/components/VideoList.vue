<template>
    <ul>
        <li v-for="result in results" :key="result.id.videoId">
          {{result.snippet.title}}
          <img v-bind:src="result.snippet.thumbnails.default.url" alt="">
        </li>
    </ul>
</template>

<script>
export default {
  name: 'VideoList',
  props: {
    results: Array
  }
}
</script>

<style>
#app{
  float: left;
}
</style>
