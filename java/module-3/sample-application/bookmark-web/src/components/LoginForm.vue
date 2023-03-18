<template>
  <form id="form-login" v-on:submit.prevent="login">
    <div class="form-control">
      <label for="username">Username:</label>
      <input type="text" id="username" size=50 v-model="user.username" required autofocus />
    </div>
    <div class="form-control">
      <label for="password">Password:</label>
      <input type="password" id="password" size=50 v-model="user.password" required />
    </div>
    <button type="submit" class="btn-primary">Sign in</button>
    <router-link :to="{ name: 'register' }">New? Register here!</router-link>
  </form>
</template>

<script>
import userService from "../services/UserService";

export default {
  data() {
    return {
      user: {
        username: '',
        password: ''
      }
    };
  },
  methods: {
    login() {
      // Clear any existing error notifications
      this.$store.commit("CLEAR_ERROR_NOTIFICATIONS");

      userService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit('SET_AUTH_TOKEN', response.data.token);
            this.$store.commit('SET_USER', response.data.user);
            this.$router.push({name:'myBookmarks'});
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            // Login failed
            this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Username or password are incorrect.'});
          } else {
            // Something else unexpected happened
            this.$store.commit("ADD_NOTIFICATION", {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
          }
        });
    }
  }
}
</script>

<style scoped>
form {
  margin-bottom: 1rem;
  max-width: 40rem;
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
  margin-left: 165px;
  margin-right: 15px;
  border: 1px solid black;
  border-radius: 5px;
  padding: 0.25rem 0.5rem;
}

.btn-primary {
  background-color: rgba(136, 193, 231, 0.4);
}

.form-control {
  margin-bottom: 1rem;
}
</style>