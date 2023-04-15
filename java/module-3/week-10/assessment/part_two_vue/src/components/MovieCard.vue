<template>
  <div class="movie-card" v-on:click="$router.push(`/movies/${movie.id}`);">
    <div class="movie-poster">
      <img v-bind:src="imageBaseUrl + movie.posterImage" v-bind:alt="movie.title" />
    </div>
    <div class="movie-info">
      <h1 class=movie-title>{{movie.title}}</h1>
      <div class="genre-container">
        <span class="genre" v-for="genre of movie.genres" v-bind:key="genre.id">
          {{genre.name}}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['movie'],
    data() {
    return {
      imageBaseUrl: process.env.VUE_APP_REMOTE_API
    }
  },
}
</script>

<style>
.genre-container {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  justify-content: center;
}

.movie-card {
  background-color: #F9F6F0;
  border: 1px solid #FF9000;
  padding: 1.5rem 0.5rem;
  text-align: center;
  width: 325px;
}

.movie-title {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
}

.movie-poster {
  height: 300px;
  margin-bottom: 2rem;
}

img {
  border: 1px solid#c7c7c7;
  height: 100%;
}

.genre {
  background-color: #B60220;
  border: 1px solid #51010F;
  border-radius: 1rem;
  color: #F9F6F0;
  padding: 0.5rem 0.75rem;
}
</style>