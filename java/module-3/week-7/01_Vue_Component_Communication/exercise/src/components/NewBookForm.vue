<template>
  <form class="new-book-form" v-on:submit.prevent="saveBook">
      <input type="text" placeholder="Title" class="title-input" v-model="newBook.title">
      <input type="text" placeholder="Author" class="author-input" v-model="newBook.author">
      <input type="text" placeholder="ISBN" class="isbn-input" v-model="newBook.isbn">
      <button>Save</button>
  </form>
</template>

<script>
export default {
    name: 'new-book-form',
    data() {
        return {
            newBook: {
            title: '',
            author: '',
            read: false,
            isbn: ''
            }
        }
    },
    methods: {
        saveBook() {
            this.newBook.read = false;
            this.$store.commit('ADD_NEW_BOOK', this.newBook);
            this.newBook = {
                title: '',
                author: '',
                read: false,
                isbn: ''
            };
        }
    }

}
</script>

<style>

</style>