<template>
  <div class="view-container">
    <header class="view-header">
      <h1>All Books</h1>
    </header>
    <div id="view-content">
      <book-list-filter v-on:filterValueChange="filterList" />
      <loading-spinner v-if="isLoading"/>
      <div class="error" v-else-if="error">
        <p>Sorry! Something unexpected happened. {{error}} Please try again later.</p>
      </div>
      <book-list v-else v-bind:bookList="bookList" />
    </div>
  </div>
</template>

<script>
import bookService from "../services/BookService";
import LoadingSpinner from '../components/LoadingSpinner.vue';
import BookList from '../components/BookList.vue';
import BookListFilter from '../components/BookListFilter.vue';

export default {
  components: { LoadingSpinner, BookList, BookListFilter },
  data() {
    return {
      error: null,
      isLoading: true,
      movieList: []
    }
  },
  created() {
    this.isLoading = true;
   bookService.getBooks()
      .then(response => {
        this.bookList = response.data;
        this.isLoading = false;
      })
      .catch((error) => {
        this.isLoading = false;
        this.error = `Could not get the book list.`;
        console.log(this.error, error.response);
      });
  },
  methods: {
    filterList(filter) {
      this.isLoading = true;
      this.error='';
      bookService.filterBooks(filter.titleString, filter.genreList)
        .then(response => {
          this.bookList = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          this.error = `Could not get the book list.`;
          console.log(this.error, error.response);
        });
    }
  }
}
</script>

<style>

</style>