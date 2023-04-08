<template>
  <div id="app">
    <app-header />
    <main id="main-content">
      <router-view />
    </main>
  </div>
</template>

<script>
import '@/assets/reset.css'
// Task 1 - create and use the new AppHeader component
import AppHeader from './components/AppHeader.vue'
export default {
  components: { AppHeader },
  name: 'App'
}
</script>

<style>
/* Color palette: https://coolors.co/fffbdb-da7422-cfbd63-c0b387-30362f */
body {
  background-color: #30362F;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
}

h1 {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
}

h2 {
  font-size: 1.3rem;
  margin-bottom: 1.5rem;
}

h3 {
  font-size: 1.15rem;
  margin-bottom: 1rem;
}

main {
  padding: 2rem;
}

.view-container {
  background-color: #CFBD63;
  border: 1px solid #625834;
  color: #30362F;
  margin: auto;
  max-width: 1600px;
  padding: 2rem;
}

.view-header {
  color: #30362F;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>
