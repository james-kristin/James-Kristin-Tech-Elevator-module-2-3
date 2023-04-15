<template>
  <div id="card-container">
    {{JSON.stringify(movieList)}}
  </div>
</template>

<script>
export default {
  props: ['movieList']
}
</script>

<style scoped>
#card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  justify-content: space-around;
}
</style>