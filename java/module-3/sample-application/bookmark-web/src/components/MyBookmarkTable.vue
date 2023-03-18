<template>
  <p v-if="myBookmarks.length === 0">
    No bookmarks to show. To get started, add a new bookmark.
  </p>
  <div v-else>
    <add-edit-bookmark-form v-if="showAddEditForm" v-on:close="resetAddEditForm"
            v-bind:bookmark="selectedBookmark" v-bind:title="'Edit Bookmark'" />
    <confirmation-dialog v-if="showDeleteConfirmation" 
            v-on:ok="deleteBookmark" v-on:cancel="cancelDelete"
            okButton="Delete" cancelButton="Cancel"
            v-bind:message="'Are you sure you want to delete this bookmark?'" />
    <div id="bookmark-counts">{{ bookmarkCounts }}</div>
    <table id="table-my-bookmarks">
      <thead>
        <tr><th>Visibility</th><th>Link</th><th>Description</th><th>Tags</th><th>Actions</th></tr>
      </thead>
      <tbody>
        <tr v-for="bookmark in myBookmarks" v-bind:key="bookmark.bookmarkId">
          <td>
            <button class="icon-button" v-bind:class="bookmark.public ? '' : 'icon-button-inactive'" title="Toggle Public/Private" v-on:click="togglePublic(bookmark)">
              <font-awesome-icon icon="fa-solid fa-users" />
            </button>
          </td>
          <td><a v-bind:href="bookmark.url" target="_blank">{{bookmark.title}}</a></td>
          <td>{{bookmark.description}}</td>
          <td>{{bookmark.allTags}}</td>
          <td>
            <button class="icon-button" v-on:click="editBookmark(bookmark)"><font-awesome-icon icon="fa-solid fa-pencil" title="Edit" /></button>&nbsp;
            <button class="icon-button" v-on:click="promptDeleteBookmark(bookmark)"><font-awesome-icon icon="fa-solid fa-trash-can" title="Delete" /></button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import bookmarkService from "../services/BookmarkService";

import ConfirmationDialog from './ConfirmationDialog.vue';
import AddEditBookmarkForm from './AddEditBookmarkForm.vue';

export default {
  name: "MyBookmarkTable",
  components: {ConfirmationDialog, AddEditBookmarkForm},
  props: ['myBookmarks'],
  data() {
    return {
      selectedBookmark: {},
      showAddEditForm: false,
      showDeleteConfirmation: false,
    }
  },
  methods: {
    editBookmark(bookmark) {
      this.selectedBookmark = bookmark;
      this.showAddEditForm = true;
    },
    togglePublic(bookmark) {
      // Make a copy of the bookmark to modify and send to server.
      let modified = {...bookmark};
      modified.public = !modified.public;
      bookmarkService.updateBookmark(modified)
        .then(() => {
          // Tell the parent component to refresh myBookmarks
          this.$emit('refresh:myBookmarks');
          // Post success message
          this.$store.commit("ADD_NOTIFICATION", {type: 'success', message: 'Bookmark updated.'});
        })
        .catch(error => {
          let response = error.response;
          // Something unexpected happened
          this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
          console.error('Something unexpected happened when toggling bookmark public/private', response.status, response.message);
        });
    },
    promptDeleteBookmark(bookmark) {
      this.selectedBookmark = bookmark;
      this.showDeleteConfirmation = true;
    },
    cancelDelete() {
      this.selectedBookmark={};
      this.showDeleteConfirmation=false;
    },
    deleteBookmark() {
      bookmarkService.deleteBookmarkById(this.selectedBookmark.bookmarkId)
        .then(() => {
          // Tell the parent component to refresh myBookmarks
          this.$emit('refresh:myBookmarks');
          // Post success message
          this.$store.commit("ADD_NOTIFICATION", {type: 'success', message: 'Bookmark deleted.'});
        })
        .catch(error => {
          let response = error.response;
          // Something unexpected happened
          this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
          console.error('Something unexpected happened when deleting bookmark', response.status, response.message);
        });
    },
    resetAddEditForm() {
      this.selectedBookmark={};
      this.showAddEditForm=false;
      // Tell the parent component to refresh myBookmarks
      this.$emit('refresh:myBookmarks');
    }
  },
  computed: {
    bookmarkCounts() {
      const publicCount = this.myBookmarks.filter(b => b.public).length;
      const privateCount = this.myBookmarks.filter(b => !b.public).length;
      const publicString = `${publicCount} public ${publicCount == 1 ? "bookmark" : "bookmarks"}`;
      const privateString = `${privateCount} private ${privateCount == 1 ? "bookmark" : "bookmarks"}`;
      return `You have ${publicString} and ${privateString}`;
    }
  }
}
</script>

<style scoped>
#table-my-bookmarks {
  width: 100%;
}
#bookmark-counts {
  font-size: 0.8rem;
  margin-bottom: 10px;
}
</style>