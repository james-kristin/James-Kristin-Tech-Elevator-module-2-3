<template>
  <div class="notifications">
    <div role="alert" v-for="(alert, index) in alerts" v-bind:key="index" class="alert" v-bind:class="'alert-' + alert.type">
      <span class="message">{{alert.message}}</span>
      <button class="icon-button" v-on:click="close(alert)"><font-awesome-icon icon="fa-solid fa-xmark" title="Close" /></button>
    </div>
  </div>
</template>

<script>
export default {
  computed: {
    alerts() {
      //Get all notifications from the store
      return this.$store.state.notifications;
    },
  },
  methods: {
    close(alert) {
      this.$store.commit("CLEAR_NOTIFICATION", alert);
    }
  }
}
</script>

<style scoped>
.notifications {
  margin-bottom: 1rem;
  max-width: 750px;
}
.alert {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  border-width: 1px;
  border-style: solid;
  padding: 0.5rem;
}
.alert-info {
  color: #084298;
  background-color: #cfe2ff;
  border-color: #b6d4fe;
}
.alert-error {
  color: #842029;
  background-color: #f8d7da;
  border-color: #f5c2c7;
}
.alert-success {
  color: #0f5132;
  background-color: #d1e7dd;
  border-color: #badbcc;
}
.alert-warning {
  color: #664d03;
  background-color: #fff3cd;
  border-color: #ffecb5;
}
.message {
  margin-right: 1rem;
}
</style>