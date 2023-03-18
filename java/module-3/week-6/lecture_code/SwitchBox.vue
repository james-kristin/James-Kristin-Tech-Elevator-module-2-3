<!-- This one is more fun if you add a few hundred instances of it with a v-for -->
// Add multiple boxes
// When an input is checked for that box, only that box color should be red

<template>
  <div :class="{red: isRed}">
      <input type="checkbox" v-model="isRed" />
  </div>
</template>

<script>
export default {
    data() {
        return {
            isRed: false
        }
    }

}
</script>

<style scoped>

* {
    box-sizing: border-box;
}

div {
    width: 50px;
    height: 50px;
    background-color: skyblue;
    padding-top: 15px;
    display: inline-block;
    text-align: center;
}

.red {
    background-color: firebrick;
}

</style>
