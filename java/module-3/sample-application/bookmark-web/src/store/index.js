import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

/*
 * Vuex is used to store application wide data. 
 *
 * The main use of Vuex in this application is to hold information for the logged-in
 * user. This is required for view routing and is used across most application components. 
 * 
 * Vuex is also used for displaying application notifications.
 */
let store = new Vuex.Store({
  state: {
    // auth token for logged in user
    token: currentToken || '',
    // logged in user information
    user: currentUser || {},
    // notifications are used across views to display messages to the user
    notifications: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    ADD_NOTIFICATION(state, notification) {
      if (notification.type === "success") {
        // Setup timer to clear notification 
        let duration = notification.timeout || 4 * 1000;
        notification.timeout = setTimeout(function () {
          //On timeout mutate state to dismiss notification
          store.commit("CLEAR_NOTIFICATION", notification);
        }, duration);
      }
      state.notifications.push(notification)
    },
    CLEAR_NOTIFICATION(state, notification) {
      let index = -1;
      for (let i=0; i<state.notifications.length; i++) {
        if (state.notifications[i].message === notification.message && state.notifications[i].type === notification.type) {
          index = i;
          break;
        } 
      }
      if (index >=0) {
        if (state.notifications[index].timeout) {
          clearTimeout(state.notifications[index].timeout);
        }
        state.notifications.splice(index, 1);
      }
    },
    CLEAR_ERROR_NOTIFICATIONS(state) {
      let index = 0;
      // Using while loop for deleting items to control the incrementing of index
      while (index < state.notifications.length) {
        let notification = state.notifications[index];
        if (notification.type === "error") {
          // Remove item, and do not move index - next item is then at current index
          state.notifications.splice(index, 1);
        } else {
          // Increment index
          index++;
        }
      }
    },
    CLEAR_ALL_NOTIFICATIONS(state) {
      state.notifications.length = 0;
    }
  }
})

export default store;
