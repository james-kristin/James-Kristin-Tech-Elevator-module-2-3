<template>
  <form id="form-login" v-on:submit.prevent="register">
      <div class="form-control">
        <label for="username">Username:</label>
        <input type="text" id="username" size=50 v-model="user.username" required autofocus />
      </div>
      <div class="form-control">
        <label for="password">Password:</label>
        <input type="password" id="password" size=50 v-model="user.password" required />
      </div>
      <div class="form-control">
        <label for="confirm-password">Confirm Password:</label>
        <input type="password" id="confirm-password" size=50 v-model="user.confirmPassword" required />
      </div>
      <button type="submit" class="btn-primary">Register</button>
      <router-link on:click="clearNotifications" :to="{ name: 'login' }">Have an account? Log-in</router-link>
    </form>
</template>

<script>
import userService from "../services/UserService";

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user'
      }
    };
  },
  methods: {
    register() {
      // Clear any previous errors
      this.$store.commit('CLEAR_ERROR_NOTIFICATIONS');

      if (this.user.password != this.user.confirmPassword) {
        this.$store.commit('ADD_NOTIFICATION', {type: 'error', message: 'Password & Confirm Password do not match.'});
      } else {
        userService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$store.commit('ADD_NOTIFICATION', {type: 'success', message: 'Registration successful. Please log-in.'});
              this.$router.push({
                name: 'login'
              });
            }
          })
          .catch((error) => {
            const response = error.response.data;
            if (response.status === 400) {
              // Validation failed, use message from response
              this.$store.commit('ADD_NOTIFICATION', {type: 'error', message: response.message});
            } else {
              // Something else unexpected happened
              this.$store.commit('ADD_NOTIFICATION', {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
            }
          });
      }
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