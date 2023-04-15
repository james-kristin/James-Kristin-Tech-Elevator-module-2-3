<template>
  <div id="app">
    <header id="app-header">
      <h1>The Movie List</h1>
    </header>
    <main id="main-content">
      <router-view />
    </main>
  </div>
</template>

<script>
import '@/assets/reset.css'
export default {
  name: 'App'
}
</script>

<style>
body {
  background-color: #2E2E38;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
}

#app-header {
  background-color: #B60220;
  border-bottom: 1px solid #51010F;
  color: #F9F6F0;
  font-weight: bold;
  margin-bottom: 1rem;
  padding: 1rem;
  text-align: center;
  text-shadow: 2px 2px #51010F;
}

#app-header h1 {
font-size: 1.75rem;
margin-bottom: 0;
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
