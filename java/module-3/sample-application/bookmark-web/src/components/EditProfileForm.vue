<template>
  <form id="form-my-profile" v-on:submit.prevent="submit">
      <div class="form-control">
        <label for="username">Username:</label>
        <div id="username">{{userProfile.username}}</div>
      </div>
      <div class="form-control">
        <label for="displayName">Display Name:</label>
        <input type="text" id="displayName" size=40 v-model="userProfile.displayName" autofocus />
      </div>
      <div class="form-control">
        <label for="imageUrl">Profile Image Url:</label>
        <input type="text" id="imageUrl" size=82 v-model="userProfile.profileImageUrl" />
      </div>
      <div class="form-control">
        <label for="shortBio">Short Bio:</label>
        <textarea id="shortBio" rows="3" cols="80" v-model="userProfile.shortBio" />
      </div>
      <div class="button-group">
        <button type="submit">Save</button>
        <button v-on:click="cancel">Cancel</button>
      </div>
    </form>
</template>

<script>
import userService from "../services/UserService";

export default {
  data() {
    return {
      userProfile: {}
    };
  },
  created() {
    // Copy over current user profile values from Vuex Store
    this.userProfile = {...this.$store.state.user};
    // Remove any role information
    delete this.userProfile.authorities;
  },
  methods: {
    submit() {
      // Clear any existing error notifications
      this.$store.commit("CLEAR_ERROR_NOTIFICATIONS");
      
      userService.updateUserProfile(this.userProfile)
        .then((response) => {
          this.$store.commit('ADD_NOTIFICATION', {type: 'success', message: 'Profile updated!'});
          this.$store.commit('SET_USER', response.data);
        })
        .catch((error) => {
          const response = error.response.data;
          // Something else unexpected happened
          console.error('Could not update user profile.', this.userProfile, response.message);
          this.$store.commit('ADD_NOTIFICATION', {type: 'error', message: 'Sorry, something unexpected occurred. Please try again later.'});
        });
    },
    cancel(){
      // Go back to the home/landing page, using "/" incase what that is changes.
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>
form {
  margin-bottom: 1rem;
}

label {
  text-align: right;
  display: inline-block;
  margin-right: 15px;
  width: 110px;
}

input {
  display: inline-block;
}

button {
  margin-left: 130px;
  margin-right: 15px;
}

.form-control {
  display: flex;
  margin-bottom: 1rem;
  align-items: baseline;
  gap: 0.5rem;
}
</style>