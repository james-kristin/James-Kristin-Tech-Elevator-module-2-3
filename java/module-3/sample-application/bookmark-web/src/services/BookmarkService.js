import axios from 'axios';

/**
 * This service class is used to interact with the Bookmark Manager server API.
 * All methods return a Promise so that the calling code can handle both success and 
 * error responses appropriately. 
 */

const http = axios.create({
  baseURL: "http://localhost:9000/bookmarks"
})

export default {

  getMyBookmarks() {
    return http.get('');
  },

  getPublicBookmarks() {
    return axios.get('/bookmarks/public');
  },

  getPublicBookmarksForUser(userId) {
    return axios.get(`/bookmarks/users/${userId}`);
  },

  searchPublicBookmarks(searchString) {
    return axios.get('/bookmarks/public?search='+searchString);
  },

  addBookmark(bookmark) {
      return axios.post('/bookmarks', bookmark);
  },

  updateBookmark(bookmark) {
    if (bookmark.tags) {
      return Promise.all([
        axios.put(`/bookmarks/${bookmark.bookmarkId}`, bookmark),
        axios.put(`/bookmarks/${bookmark.bookmarkId}/tags`, bookmark.tags)
    ]);
    } else {
      return axios.put(`/bookmarks/${bookmark.bookmarkId}`, bookmark);
    }
  },

  deleteBookmarkById(bookmarkId) {
    return axios.delete(`/bookmarks/${bookmarkId}`);
  },

  getTagsForBookmark(bookmark){
    return axios.get(`/bookmarks/${bookmark.bookmarkId}/tags`);
  },

  getAllTags() {
    return axios.get('/tags');
  }

}
