<template>
  <div>
    <h1>SearchBar</h1>
      <input @keypress.enter="input" v-model="a" type="text"> 
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data(){
    return {
      a: "",
    }
  },
  props:{
    results: Array,
  },
  methods: {
    input(){
      this.$emit("input", this.a);
    }
  }
}
</script>

<style>

</style>
