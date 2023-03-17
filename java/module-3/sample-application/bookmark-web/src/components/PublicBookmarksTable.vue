<template>
  <p v-if="publicBookmarks.length === 0">
    No public bookmarks to show. To share a bookmark, go to My Bookmarks and set the bookmark Visibility to Public.
  </p>
  <div v-else>
    <table id="table-public-bookmarks">
      <thead>
        <tr><th>Link</th><th>Description</th><th>Tags</th><th v-if="!hideUser">User</th><th>&nbsp;</th></tr>
      </thead>
      <tbody>
        <tr v-for="bookmark in publicBookmarks" v-bind:key="bookmark.bookmarkId">
          <td><a v-bind:href="bookmark.url" target="_blank">{{bookmark.title}}</a></td>
          <td>{{bookmark.description}}</td>
          <td>{{bookmark.allTags}}</td>
          <td v-if="!hideUser">
            <router-link v-bind:to="{ name: 'userProfile', params: {userId: bookmark.userId} }">
              {{bookmark.userDisplayName}}
            </router-link>
          </td>
          <td>
            <div v-if="bookmark.flagged" class="icon-button" 
                v-bind:class="bookmark.flagged?'':'icon-button-inactive'" title="Reported as inappropriate">
              <font-awesome-icon icon="fa-solid fa-triangle-exclamation" />
            </div>
            <div v-else>
              <button class="icon-button"
                v-if="bookmark.userId != user.id"
                v-on:click="toggleFlagged(bookmark)"
                v-bind:class="bookmark.flagged?'':'icon-button-inactive'" title="Report as inappropriate">
                <font-awesome-icon icon="fa-solid fa-triangle-exclamation" />
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import bookmarkService from "../services/BookmarkService";

export default {
  props: ['publicBookmarks','hideUser'],
  computed: {
    user() {
      //Get logged-in user from the store
      return this.$store.state.user;
    },
  },
  methods: {
    toggleFlagged(bookmark) {
      // Make a copy of the bookmark to modify and send to server.
      let modified = {...bookmark};
      modified.flagged = !modified.flagged;
      bookmarkService.updateBookmark(modified)
        .then(() => {
          // Tell the parent component to refresh myBookmarks
          this.$emit('refresh:publicBookmarks');
          // Post success message
          this.$store.commit("ADD_NOTIFICATION", {type: 'success', message: 'Bookmark updated.'});
        })
        .catch(error => {
          let response = error.response;
          if (response.status === 403) {
            // User is unable to alter report flag
            this.$store.commit('ADD_NOTIFICATION', {type: 'error', message: 'Sorry, you are not able to alter the report flag for this bookmark.'});
          } else {// Something unexpected happened
            this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
            console.error('Something unexpected happened when updating bookmark flag for inappropriate.', response.status, response.message);
          }
        });
    }
  }
}
</script>

<style scoped>
#table-public-bookmarks {
  width: 100%;
}
</style>