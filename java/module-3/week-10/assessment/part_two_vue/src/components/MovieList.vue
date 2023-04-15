<template>
  <div id="card-container" >
    <movie-card v-for="movie of movieList" v-bind:key="movie.id" v-bind:movie="movie"/>
  </div>
</template>

<script>
import MovieCard from './MovieCard.vue'
export default {
  components: { MovieCard },
  props: ['movieList']
}
</script>

<style scoped>
#card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  justify-content: space-around;
}
</style>