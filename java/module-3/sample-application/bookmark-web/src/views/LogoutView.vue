<template>
  <h1>Logout</h1>
</template>

<script>
export default {
  created() {
    this.$store.commit("LOGOUT");
    this.$router.push({name: "login"});
    this.$store.commit('CLEAR_ALL_NOTIFICATIONS');
  }
};
</script>
