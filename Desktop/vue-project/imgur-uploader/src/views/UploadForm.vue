<template>
  <div class="dropper">
    <input type="file">
    <span>Drag images here</span>
  </div>
</template>

<script>
export default {
  name: 'UploadForm',
}
</script>

<style>
.dropper {
  height: 30vh;
  border: 2px dashed black;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
.dropper:hover {
  background-color: #999;
}
input {
  width: 100%;
  height: 100%;
  position: absolute;
  opacity: 0;  
  cursor: pointer;
}
</style>
