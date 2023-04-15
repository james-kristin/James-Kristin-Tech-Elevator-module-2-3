<template>
  <loading-spinner v-if="isLoading"/>
  <div v-else class="view-container">
    <header class="view-header">
      <h1>Movie Details</h1>
    </header>
    <div id="view-content">
      <div class="error" v-show="error">
        <p>Sorry! Something unexpected happened. {{error}} Please try again later.</p>
      </div>
      <movie-details v-show="!error" v-bind:movie="movieData" />
    </div>
  </div>
</template>

<script>
import movieService from "../services/MovieService";
import LoadingSpinner from '../components/LoadingSpinner.vue';
import MovieDetails from '../components/MovieDetails.vue';

export default {
  components: { LoadingSpinner, MovieDetails },
  data() {
    return {
      error: null,
      isLoading: true,
      // TODO - currently shows only movie 102230, Our Father
      movieId: 102230,
      movieData: {}
    }
  },
  created() {
    this.isLoading = true;
    movieService.getMovieById(this.movieId)
      .then(response => {
        this.movieData = response.data;
        this.isLoading = false;
      })
      .catch((error) => {
        this.isLoading = false;
        this.error = `Could not get movie for movie id ${this.movieId}.`;
        console.log(this.error, error.response);
      });
  }
}
</script>

<style>

</style>