<template>
  <div class="modal-backdrop">
    <div class="modal" role="dialog">
      <header class="modal-header">
        <h3>{{$props.title}}</h3>
        <button class="icon-button" v-on:click="close"><font-awesome-icon icon="fa-solid fa-xmark" title="Close" /></button>
      </header>

      <section class="modal-body">
        <form id="form-bookmark-add-edit" v-on:submit.prevent="submit">
          <div class="form-control">
            <label for="visibility">Visibility:</label>
            <select id="visibility" v-model="newBookmark.public">
              <option v-bind:value="false">Private</option>
              <option v-bind:value="true">Public</option>
            </select>
          </div>
          <div class="form-control">
            <label for="title">Title:</label>
            <input type="text" id="title" size=80 v-model="newBookmark.title" required />
          </div>
          <div class="form-control">
            <label for="title">Url:</label>
            <input type="text" id="url" size=80 v-model="newBookmark.url" required />
          </div>
          <div class="form-control">
            <label for="description">Description:</label>
            <textarea id="description" rows="3" cols="80" v-model="newBookmark.description" />
          </div>
          <div class="form-control">
            <label for="tags">Tags:</label>
            <select v-model="newBookmark.tags" multiple>
              <option v-for="tag in tags" v-bind:value="tag" v-bind:key="tag.id">
                {{ tag.name }}
              </option>
            </select>
          </div>
          <div class="button-group">
            <button type="submit" class="btn-primary">Save</button>
            <button v-on:click="close">Cancel</button>
          </div>
        </form>
       </section>
    </div>
  </div>
</template>

<script>
import bookmarkService from '../services/BookmarkService';

export default {
  props: ['title', 'bookmark'],
  data() {
    return {
      tags: [],
      newBookmark: {
        tags: []
      }
    }
  },
  created() {
    // Get the Tags for the multi-select list
    bookmarkService.getAllTags()
        .then( response => {
          this.tags = response.data;
        })
    //Initialize form
    this.init();
  },
  methods: {
    close() {
      // This generates a custom 'close' event for the parent to handle
      this.$emit('close');
    },
    init() {
      if (this.$props.bookmark.bookmarkId) {
        // This is an edit. Make a copy for editing, so old values are not changed in case of cancel.
        // (Also note that props are read-only)
        this.newBookmark = {...this.$props.bookmark, tags: []}
        
        // We shouldn't rely on the string AllTags to handle tags updates, so get Tag objects with ids
        bookmarkService.getTagsForBookmark(this.newBookmark)
            .then(response => this.newBookmark.tags = response.data)
            .catch(error => console.error('Error getting bookmark tags.', error.response.message))
      } else {
        // Not doing an edit, so setup clean default values. This is important to clear any previous values.
        this.newBookmark = {
          visibility: false,
          title: '',
          url: '',
          description: '',
          tags: []
        }
      }
    },
    submit() {
      // Clear any existing error notifications
      this.$store.commit("CLEAR_ERROR_NOTIFICATIONS");
      
      if (this.newBookmark.bookmarkId) {
        // Updating existing bookmark
        bookmarkService.updateBookmark(this.newBookmark)
            .then(() => {
              // Post success message & close modal
              this.$store.commit("ADD_NOTIFICATION", {type: 'success', message: 'Bookmark updated!'});
              this.close();
            })
            .catch(error => {
              let response = error.response;
              console.error('Could not update bookmark.', this.newBookmark, response.message);
              this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, the bookmark could not be updated at this time.'});
            })
      } else {
        bookmarkService.addBookmark(this.newBookmark)
            .then(response => {
              // Need to add the tags separately
              let bookmark = response.data;
              bookmark.tags = this.newBookmark.tags;
              bookmarkService.updateBookmark(bookmark)
                  .then(() => {
                    // Post success message & close modal
                    this.$store.commit("ADD_NOTIFICATION", {type: 'success', message: 'Bookmark added!'});
                    this.close();
                  })
                  .catch(error => {
                    let response = error.response;
                    console.error('Could not add new bookmark tags.', this.bookmark, response.message);
                    this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, the bookmark was created but tags could not be added at this time.'});
                  })
            })
            .catch(error => {
              let response = error.response;
              console.error('Could not add new bookmark.', this.newBookmark, response.message);
              this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, the bookmark could not be added at this time.'});
            })
      }
    }
  }
  };
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99;
}

.modal {
  background: #FFFFFF;
  box-shadow: 2px 2px 20px 1px;
  overflow-x: auto;
  display: flex;
  flex-direction: column;
  z-index: 999;
}

.modal-header {
  position: relative;
  background-color: rgba(136, 193, 231, 0.4);
  font-size: 20px;
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  padding: 0.5rem;
}

.modal-header h3 {
  margin-bottom: 0;
}

.modal-body {
  position: relative;
  padding: 20px 10px;
}

form {
  margin-bottom: 1rem;
}

label {
  text-align: right;
  display: inline-block;
  margin-right: 15px;
  width: 150px;
}

input {
  display: inline-block;
}


button {
  border: 1px solid black;
  border-radius: 5px;
  padding: 0.25rem 0.5rem;
}

.button-group {
  margin-left: 165px;
}

.button-group button:not(:last-child) {
  margin-right: 15px;
}

.btn-primary {
  background-color: rgba(136, 193, 231, 0.4);
}

.form-control {
  display: flex;
  margin-bottom: 1rem;
  align-items: baseline;
}
</style>