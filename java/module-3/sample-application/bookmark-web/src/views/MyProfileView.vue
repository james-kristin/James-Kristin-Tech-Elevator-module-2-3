<template>
  <div>
    <header class="view-header">
      <h2>My Profile</h2>
    </header>
    <div id="view-content">
      <notification-list />
      <edit-profile-form />
    </div>
  </div>
</template>

<script>
import NotificationList from '../components/NotificationList.vue';
import EditProfileForm from "../components/EditProfileForm.vue"

export default {
  components: { EditProfileForm, NotificationList },
};
</script>

<style scoped>
#view-content {
  width: 90%;
}
</style>
