<template>
  <div>
    <header class="view-header">
      <h2>User Profile</h2>
    </header>
    <loading-spinner v-if="isUserLoading" />
    <div v-else id="view-content">
      <user-profile v-bind:user="user" />
      <h3>Public Bookmarks</h3>
      <loading-spinner v-if="isTableLoading" />
      <public-bookmarks-table v-else  
          v-bind:publicBookmarks="bookmarks" v-bind:hideUser="true"
          v-on:refresh:publicBookmarks="getPublicBookmarks" />
    </div>
  </div>
</template>

<script>
import userService from '../services/UserService';
import bookmarkService from '../services/BookmarkService';
import PublicBookmarksTable from '../components/PublicBookmarksTable.vue';
import LoadingSpinner from '../components/LoadingSpinner.vue';
import UserProfile from '../components/UserProfile.vue';

export default {
  components: { PublicBookmarksTable, LoadingSpinner, UserProfile },
  data() {
    return {
      isUserLoading: true,
      isTableLoading: true,
      userId: this.$route.params.userId,
      bookmarks: [],
      user: {}
    }
  },
  created() {
    this.getUserProfile();
    this.getPublicBookmarks();
  },
  methods: {
    getUserProfile() {
      this.isUserLoading=true;
      userService.getUserProfile(this.userId)
          .then(response => {
            this.user = response.data;
            this.isUserLoading = false;
          })
          .catch((error) => {
            this.isUserLoading = false;
            const response = error.response.data;
            // Something else unexpected happened
            console.error(`Could not get user profile for user id ${this.userId}.`, response.message);
            this.$store.commit('ADD_NOTIFICATION', {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
          });
    },
    getPublicBookmarks() {
      this.isTableLoading = true;
      bookmarkService.getPublicBookmarksForUser(this.userId)
          .then(response => {
            this.bookmarks = response.data
            this.isTableLoading = false;
          })
          .catch(error => {
            this.isTableLoading=false;
            // Something unexpected happened
            this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
            let response = error.response;
            console.error(`Getting user's (id=${this.user.id}) public bookmarks was unsuccessful: `, response.message);
          });
    }
  }
}
</script>

<style>
#user-profile {
  display: flex;
  align-items: center;
  gap: 2rem;
  border: 1px solid rgba(136, 193, 231, 0.7);
  border-radius: 0.5rem;
  margin-bottom: 2rem;
  padding: 1.5rem;
  max-width: 50rem;
}
.user-name {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
.img-profile {
  font-size: 120px;
  max-width: 120px;
  border-radius: 50%;
}
</style>