// The select options should be each animal's name
// When the animal is clicked, the emoji should display next to it

<template>
  <div>
      <select>
          <option>Please Select</option>
          <option></option>
      </select>
      <span></span>
  </div>
</template>

<script>
export default {
    data() {
        return {
            animals: [
                {name: 'Dog', emoji: '🐶'},
                {name: 'Cat', emoji: '🐱'},
                {name: 'Frog', emoji: '🐸'}
            ]
        }
    }

}
</script>

<style scoped>
span {
    font-size: 3em;
}
</style>
