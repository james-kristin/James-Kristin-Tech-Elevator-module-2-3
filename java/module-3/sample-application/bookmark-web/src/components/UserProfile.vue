<template>
  <div id="user-profile">
    <img v-if="user.profileImageUrl" class="img-profile" v-bind:src="user.profileImageUrl" alt="profile image" />
    <font-awesome-icon v-else class="img-profile" icon="fa-solid fa-user" />
    <div>
      <div class="user-name">{{user.displayName ? user.displayName : user.username}}</div>
      <p>{{user.shortBio}}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: ['user'],
}
</script>

<style>

</style>