<template>
    <div>
        <best-seller-list/>
        <new-releases-list/>
    </div>
</template>

<script>
import BestSellerList from '../components/BestSellerList.vue';
import NewReleasesList from '../components/NewReleasesList.vue';

export default {
    name: 'Home',
    components: {
        BestSellerList,
        NewReleasesList,
    }
}
</script>

<style>

</style>