<template>
  <div>
    <header class="view-header">
      <h2>Public Bookmarks</h2>
      <search-box @searchValueChange="filterList" :delay="900" />
    </header>
    <loading-spinner v-if="isLoading" />
    <div v-else id="view-content">
      <notification-list />
      <public-bookmarks-table
        :publicBookmarks="bookmarks"
        @refresh:publicBookmarks="getPublicBookmarks"
      />
    </div>
  </div>
</template>

<script>
import bookmarkService from "../services/BookmarkService";

import NotificationList from "../components/NotificationList.vue";
import SearchBox from "../components/SearchBox.vue";
import PublicBookmarksTable from "../components/PublicBookmarksTable.vue";
import LoadingSpinner from '../components/LoadingSpinner.vue';

export default {
  components: { NotificationList, PublicBookmarksTable, SearchBox, LoadingSpinner },
  created() {
    this.getPublicBookmarks();
    // Clear any previous notifications
    this.$store.commit("CLEAR_ALL_NOTIFICATIONS");
  },
  data() {
    return {
      isLoading: true,
      filter: "",
      bookmarks: [],
    };
  },
  methods: {
    filterList(searchValue) {
      this.filter = searchValue;
      this.getPublicBookmarks();
    },

    getPublicBookmarks() {
      this.isLoading
      bookmarkService
        .searchPublicBookmarks(this.filter)
        .then((response) => {
          this.bookmarks = response.data;
          this.isLoading=false;
        })
        .catch((error) => {
          this.isLoading=false;
          // Something unexpected happened
          this.$store.commit("ADD_NOTIFICATION", {
            type: "error",
            message:
              "Sorry, something unexpected occurred. Please try again later.",
          });
          let response = error.response;
          console.error(
            "Getting public bookmarks was unsuccessful: ",
            response.message
          );
        });
    },
  },
};
</script>

<style scoped>
.view-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}
</style>
