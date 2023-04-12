<template>
  <div id="card-container">
    <!-- Task 4 - Update the BookList to use the BookCard component -->
    <book-card v-for="book of bookList" v-bind:key="book.id" v-bind:book="book" />
  </div>
</template>

<script>
import BookCard from './BookCard.vue'
export default {
  components: { BookCard },
  props: ['bookList']
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