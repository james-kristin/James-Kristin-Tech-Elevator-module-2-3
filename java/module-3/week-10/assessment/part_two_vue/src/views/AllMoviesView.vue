<template>
  <div class="view-container">
    <header class="view-header">
      <h1>All Movies</h1>
    </header>
    <div id="view-content">
      <movie-filter v-on:filterValueChange="filterMovies" />
      <loading-spinner v-if="isLoading"/>
      <div class="error" v-else-if="error">
        <p>Sorry! Something unexpected happened. {{error}} Please try again later.</p>
      </div>
      <movie-list v-else v-bind:movieList="movieList" />
    </div>
  </div>
</template>

<script>
import movieService from "../services/MovieService";
import LoadingSpinner from '../components/LoadingSpinner.vue';
import MovieList from '../components/MovieList.vue';
import MovieFilter from '../components/MovieFilter.vue';

export default {
  components: { LoadingSpinner, MovieList, MovieFilter },
  data() {
    return {
      error: null,
      isLoading: true,
      movieList: []
    }
  },
  created() {
    this.isLoading = true;
    movieService.getMovies()
      .then(response => {
        this.movieList = response.data;
        this.isLoading = false;
      })
      .catch((error) => {
        this.isLoading = false;
        this.error = `Could not get the movie list.`;
        console.log(this.error, error.response);
      });
  },
  methods: {
    filterMovies(filter) {
      this.isLoading = true;
      this.error='';
      movieService.filterMovies(filter.titleString, filter.genreList)
        .then(response => {
          this.movieList = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          this.error = `Could not get the movie list.`;
          console.log(this.error, error.response);
        });
    }
  }
}
</script>

<style>

</style>