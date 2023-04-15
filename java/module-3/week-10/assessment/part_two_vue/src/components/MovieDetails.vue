<template>
  <article>
    <section class="movie-details">
      <h1 class=movie-title>{{movie.title}}</h1>
      <div class="movie-tagline" v-if="movie.taglines">{{movie.taglines[0]}}</div>
      <div class="movie-property">
        <label>Release Date:</label><span>{{movie.releaseDate}}</span>
      </div>
      <div class="movie-property">
        <label>Director:</label><span>{{directorList}}</span>
      </div>
      <div class="movie-property">
        <label>Genres:</label><span>{{genreList}}</span>
      </div>
      <div class="movie-property">
        <label>Summary:</label><p>{{movie.summary}}</p>
      </div>
    </section>
    <aside class="movie-poster">
      <img v-bind:src="imageBaseUrl + movie.posterImage" v-bind:alt="movie.title" />
    </aside>
  </article>
</template>

<script>

export default {
  props: ['movie'],
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_REMOTE_API
    }
  },
  computed: {
    directorList() {
      return this.movie.directors.map(d => d.name).join(', ');
    },
    genreList() {
      return this.movie.genres.map(g => g.name).join(', ');
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

.movie-title {
  font-weight: bold;
}

.movie-tagline {
  margin-bottom: 2rem;
}

.movie-poster {
  height: 400px;
  padding: 1.5rem;
}

.movie-details {
  padding: 2.5rem;
}

.movie-property {
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