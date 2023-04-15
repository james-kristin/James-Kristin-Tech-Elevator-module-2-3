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
import AppHeader from './components/AppHeader.vue'
export default {
  components: { AppHeader },
  name: 'App'
}
</script>

<style>
body {
  background-color: #2E2E38;
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
  background-color: #f0b642;
  border: 1px solid #FF9000;
  color: #262640;
  margin: auto;
  max-width: 1600px;
  padding: 2rem;
}

.view-header {
  color: #262640;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>
