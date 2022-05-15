<script lang="ts" setup>
import { nextTick, provide, ref } from 'vue'

const isRouterActive = ref(true)
provide('reload', () => {
  isRouterActive.value = false
  nextTick(() => {
    isRouterActive.value = true
  })
})
</script>

<template>
  <RouterView v-if="isRouterActive"></RouterView>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 0px;
}
body {
  margin: 0px;
  min-width: 680px;
}
ul {
  padding: 0;
  margin: 0;
}
li {
  list-style: none;
}
a {
  text-decoration: none;
  cursor: pointer;
  color: #909090;
}
a:hover {
  color: #007fff;
}
h1 {
  margin: 0;
}
.card {
  border-radius: 4px;
  border: 1px solid #e4e7ed;
  background-color: #fff;
  /* box-shadow: 0px 0px 12px rgb(0 0 0 / 12%); */
  box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.12);
  padding: 15px;
}
.el-overlay {
  z-index: 1;
}
</style>
