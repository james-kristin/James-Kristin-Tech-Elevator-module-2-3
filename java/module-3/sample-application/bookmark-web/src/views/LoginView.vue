<template>
  <div id="view-login">
    <h2>Log-in</h2>
    <notification-list />
    <login-form />
  </div>
</template>

<script>
import LoginForm from '../components/LoginForm.vue';
import NotificationList from '../components/NotificationList.vue';

export default {
  components: {LoginForm, NotificationList},
};
</script>

<style scoped>
#view-login {
  max-width: 750px;
}
</style>
