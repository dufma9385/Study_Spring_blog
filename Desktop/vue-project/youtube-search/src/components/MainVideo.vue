<template>
    <div v-if="results.length > 0">
        <iframe :src="`https://www.youtube.com/embed/${results[0].id.videoId}`"></iframe>
    </div>
</template>

<script>
export default {
  name: 'MainVideo',
  props: {
    results:Array      
  },
}
</script>

<style>

</style>
