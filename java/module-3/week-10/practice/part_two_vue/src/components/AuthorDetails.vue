<template>
  <article>
    <aside class="author-photo">
      <img v-bind:src="imageBaseUrl + author.image" v-bind:alt="author.fullName" />
    </aside>
    <section class="author-details">
      <h1 class=author-name>{{author.fullName}}</h1>
      <div class="info-property" v-if="author.birth">
        <label>Birth:</label><span>{{author.birth}}</span>
      </div>
      <div class="info-property" v-if="author.death">
        <label>Death:</label><p>{{author.death}}</p>
      </div>
      <div class="info-property">
        <label>About:</label><p>{{author.about}}</p>
      </div>
    </section>
  </article>
</template>

<script>

export default {
  props: ['author'],
  data() {
    return {
      imageBaseUrl: process.env.VUE_APP_REMOTE_API
    }
  }
}
</script>

<style scoped>
article {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1.5rem;
  padding-bottom: 1.5rem;
}

.book-title {
  display: block;
  margin-bottom: 0.5rem;
}

.author-name {
  font-weight: bold;
}

.author-photo {
  background-color: #FFFBDB;
  border: 1px solid #B2A36C;
  height: 400px;
  padding: 1.5rem;
}


.author-details {
  background-color: #FFFBDB;
  border: 1px solid #B2A36C;
  padding: 2.5rem;
}

.info-property {
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