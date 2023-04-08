<template>
  <loading-spinner v-if="isLoading"/>
  <div v-else class="view-container">
    <header class="view-header">
      <h1>Author Details</h1>
    </header>
    <div id="view-content">
      <div class="error" v-show="error">
        <p>Sorry! Something unexpected happened. {{error}} Please try again later.</p>
      </div>
      <author-details v-show="!error" v-bind:author="authorData" />
    </div>
  </div>
</template>

<script>
import bookService from "../services/BookService";
import LoadingSpinner from '../components/LoadingSpinner.vue';
import AuthorDetails from '../components/AuthorDetails.vue';

export default {
  components: { LoadingSpinner, AuthorDetails },
  data() {
    return {
      error: null,
      isLoading: true,
      authorId: this.$route.params.authorId,
      authorData: {}
    }
  },
  created() {
    // Task 5 - Update the view get data from the Book Service
    this.isLoading = true;
    bookService.getAuthorById(this.authorId)
      .then(response => {
        this.authorData = response.data;
        this.isLoading = false;
      })
      .catch((error) => {
        this.isLoading = false;
        this.error = `Could not get author for id ${this.authorId}.`;
        console.log(this.error, error.response);
      });
  }
}
</script>

<style>

</style>