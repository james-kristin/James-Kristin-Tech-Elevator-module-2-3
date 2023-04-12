<template>
  <div id="card-container">
    <div v-for="book of bookList" v-bind:key="book.id" v-bind:book="book">
      {{JSON.stringify(book)}}
    </div>
  </div>
</template>

<script>
export default {
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