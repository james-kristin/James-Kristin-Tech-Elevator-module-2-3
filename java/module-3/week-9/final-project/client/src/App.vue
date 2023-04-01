<template>
  <div id="app">
    <header></header>
    <nav>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
    </nav>
    <main>
      <router-view />
    </main>
    <footer></footer>
  </div>
</template>

<style scoped>

</style>