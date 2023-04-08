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
import LoadingSpinner from '../components/LoadingSpinner.vue';
import AuthorDetails from '../components/AuthorDetails.vue';

export default {
  components: { LoadingSpinner, AuthorDetails },
  data() {
    return {
      error: null,
      isLoading: true,
      authorId: this.$route.params.authorId,
      authorData: {
        "id": 302214,
        "fullName": "Nnedi Okorafor",
        "firstName": "Nnedi",
        "lastName": "Okorafor",
        "image": "/images/authors/nnedi_okorafor.jpg",
        "birth": "04-08-1974",
        "death": null,
        "about": "Nnedi Okorafor is a New York Times Bestselling writer of science fiction and fantasy for both children and adults. The more specific terms for her works are africanfuturism and africanjujuism, both terms she coined and defined. Born in the United States to two Nigerian (Igbo) immigrant parents and visiting family in Nigeria since she was a child, the foundation and inspiration of Nnedi’s work is rooted in this part of Africa. Her many works include Who Fears Death (winner of the World Fantasy Award) and the Nebula and Hugo award winning novella trilogy Binti.",
        "books": [
            { "id": 102221, "title": "Binti" },
            { "id": 102222, "title": "Home" },
            { "id": 102223, "title": "The Night Masquerade" },
            { "id": 102224, "title": "Who Fears Death", }
        ]
      }
    }
  },
  created() {
    this.isLoading = false;
  }
}
</script>

<style>

</style>