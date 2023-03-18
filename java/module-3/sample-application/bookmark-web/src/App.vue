<template>
  <div id="app">
    <app-header id="app-header" title="Bookmark Manager"/>
    <main-nav id="main-nav" />
    <main id="main-content">
      <router-view />
    </main>
    <app-footer id="app-footer"/>
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppFooter from './components/AppFooter.vue';
import MainNav from './components/MainNav.vue';
export default {
  components: {AppHeader, AppFooter, MainNav},
}
</script>

<style scoped>

</style>
