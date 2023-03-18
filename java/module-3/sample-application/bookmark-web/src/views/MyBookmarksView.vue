<template>
  <div>
    <header class="view-header">
      <h2>My Bookmarks</h2>
      <button class="icon-button" v-on:click="addBookmark">
        Add Bookmark&nbsp;
        <font-awesome-icon icon="fa-solid fa-circle-plus" title="Add Bookmark" />
      </button>
    </header>
    
    <loading-spinner v-if="isLoading" />
    <div v-else id="view-content">
      <add-edit-bookmark-form v-show="showAddEditForm" v-on:close="resetAddEditForm" 
          v-bind:title="'Add Bookmark'" v-bind:bookmark="{}" />
      <notification-list />
      <my-bookmark-table v-bind:myBookmarks="myBookmarks" v-on:refresh:myBookmarks="getMyBookmarks" />
    </div>
  </div>
</template>

<script>
import bookmarkService from "../services/BookmarkService";

import MyBookmarkTable from '../components/MyBookmarkTable.vue';
import NotificationList from '../components/NotificationList.vue';
import AddEditBookmarkForm from '../components/AddEditBookmarkForm.vue';
import LoadingSpinner from '../components/LoadingSpinner.vue';

export default {
  components: { MyBookmarkTable, NotificationList, AddEditBookmarkForm, LoadingSpinner },
  created() {
    this.getMyBookmarks();
  },
  data() {
    return {
      isLoading: true,
      myBookmarks: [],
      showAddEditForm: false
    }
  },
  methods: {
    addBookmark() {
      this.showAddEditForm = true;
    },
    getMyBookmarks() {
      this.isLoading=true;
      bookmarkService.getMyBookmarks()
          .then(response => {
            this.myBookmarks = response.data
            this.isLoading=false;
          })
          .catch(error => {
            this.isLoading=false;
            // Something unexpected happened
            this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
            let response = error.response;
            console.error('Getting my bookmarks was unsuccessful: ', response.message);
          });
    },
    resetAddEditForm() {
      this.showAddEditForm=false;
      // Refresh myBookmarks to get new bookmark into list
      this.getMyBookmarks()
    }
  }
};
</script>

<style scoped>
.view-header {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  flex-wrap: wrap;
}
</style>
