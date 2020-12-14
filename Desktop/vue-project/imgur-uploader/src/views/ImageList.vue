<template>
  <div>
    <div v-for="image in allImages" :key="image.id" >
      <img :src="image.link" :alt="image.name">
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'ImageList',
  // computed: mapState(['images']), + import 필요
  computed: mapGetters(['allImages']),
  methods: mapActions(['fetchImages']),
  created() {
    this.fetchImages();
  },
}
</script>

<style>

</style>