<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
// import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <RouterView></RouterView>
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
</style>
