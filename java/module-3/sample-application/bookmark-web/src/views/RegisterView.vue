<template>  
  <div id="view-register">  
    <h2>Register</h2>
    <notification-list />
    <register-user-form />
  </div>
</template>

<script>
import RegisterUserForm from '../components/RegisterUserForm.vue';
import NotificationList from '../components/NotificationList.vue';

export default {
  components: {RegisterUserForm, NotificationList},
};
</script>

<style scoped>
#view-register {
  max-width: 750px;
}
</style>
