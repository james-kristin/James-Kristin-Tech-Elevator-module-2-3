<template>
  <article>
    <section class="book-details">
      <h1 class=book-title>{{book.title}}</h1>
      <div class="book-property">
        <label>{{book.authors.length===1 ? "Author:" : "Authors:"}}</label>
        <p>
          <router-link class="author-name"
              v-bind:to="{ name: 'authorDetails', params: {authorId: author.id} }"
              v-for="author of book.authors" v-bind:key="author.id">
            {{author.fullName}}
          </router-link>
        </p>
      </div>
      <div class="book-property">
        <label>First Published:</label><span>{{book.firstPublished}}</span>
      </div>
      <div class="book-property">
        <label>Genres:</label><p>{{genreList}}</p>
      </div>
      <div class="book-property">
        <label>Summary:</label><p>{{book.overview}}</p>
      </div>
    </section>
    <aside class="book-cover">
      <img v-bind:src="imageBaseUrl + book.coverImage" v-bind:alt="book.title" />
    </aside>
  </article>
</template>

<script>

export default {
  props: ['book'],
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_REMOTE_API
    }
  },
  methods: {
    getAuthorUrl(author) {
      return `/authors/${author.id}`;
    }
  },
  computed: {
    genreList() {
      return this.book.genres.map(g => g.name).join(', ');
    }
  }
}
</script>

<style scoped>
article {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  gap: 1.5rem;
  padding-bottom: 1.5rem;
}

.author-name {
  margin-right: 1rem;
}

.book-title {
  font-weight: bold;
}

.book-cover {
  background-color: #FFFBDB;
  border: 1px solid #B2A36C;
  height: 400px;
  padding: 1.5rem;
}

.book-details {
  background-color: #FFFBDB;
  border: 1px solid #B2A36C;
  padding: 2.5rem;
}

.book-property {
  display: flex;
  align-items: baseline;
  margin-bottom: 1rem;
}

img {
  border: 1px solid#c7c7c7;
  height: 100%;
}

label {
  font-size: 1.1rem;
  font-weight: bold;
  margin-right: 1rem;
}

p {
  line-height: 1.1rem;
  max-width: 600px;
}
</style>