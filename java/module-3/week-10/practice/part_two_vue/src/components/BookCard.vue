<template>
  <div class="book-card" v-on:click="$router.push(`/books/${book.id}`);">
    <div class="book-cover">
      <img v-bind:src="imageBaseUrl + book.coverImage" v-bind:alt="book.title" />
    </div>
    <div class="book-info">
      <h1 class=book-title>{{book.title}}</h1>
      <div class="tag-container">
        <span class="tag" v-for="tag of book.tags" v-bind:key="tag.id">
          {{tag.name}}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['book'],
    data() {
    return {
      imageBaseUrl: process.env.VUE_APP_REMOTE_API
    }
  },
}
</script>

<style scoped>
.tag-container {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  justify-content: center;
}

.book-card {
  background-color: #FFFBDB;
  border: 1px solid #A09054;
  padding: 1.5rem 0.5rem;
  text-align: center;
  width: 350px;
  cursor: pointer;
}

.book-title {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
}

.book-cover {
  height: 300px;
  margin-bottom: 2rem;
}

img {
  border: 1px solid#c7c7c7;
  height: 100%;
}

.tag {
  background-color: #DA7422;
  border-bottom: 1px solid #B05F1C;
  border-radius: 1rem;
  color: #FFFBDB;
  padding: 0.5rem 0.75rem;
}
</style>