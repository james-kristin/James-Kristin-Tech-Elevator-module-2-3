<template>
  <main>
    <best-seller-list />
    <new-releases-list />
  </main>
</template>

<script>
import BestSellerList from '../components/BestSellerList.vue';
import NewReleasesList from '../components/NewReleasesList.vue';

export default {
  components: {
    BestSellerList,
    NewReleasesList
  }
}
</script>

<style>

</style>