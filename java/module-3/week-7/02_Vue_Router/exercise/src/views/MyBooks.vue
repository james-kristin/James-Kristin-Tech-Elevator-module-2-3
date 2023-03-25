<template>
  <div>
      <reading-list />
      <router-link v-bind:to="{ name: 'newBook' }">Add Book to Reading List</router-link>
      <router-view />
  </div>
</template>

<script>
import ReadingList from '../components/ReadingList.vue'

export default {
    name: 'MyBooks',
    components: {
        ReadingList
    }
}
</script>

<style>

</style>