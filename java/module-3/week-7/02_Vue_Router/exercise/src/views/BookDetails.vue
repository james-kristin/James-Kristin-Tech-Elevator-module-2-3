<template>
  <div>
    <div>{{book.title}}</div>
    <div>{{book.author}}</div>
    <img v-if="book.isbn" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'" />
    <div>{{read}}</div> 
  </div>
</template>

<script>
export default {
    name: 'BookDetails',
    computed: {
        book() {
            return this.$store.state.books.find((book) => {
                return book.isbn == this.$route.params.isbn;
            });
        },
        read() {
            if (this.book.read) {
                return 'I have read this book';
            } else {
                return 'I have not read this book';
            }
        }
    }
}
</script>

<style>

</style>