<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
<style>
.text-item {
  display: flex;
  text-overflow: ellipsis;
  height: 20px;
}
</style>
