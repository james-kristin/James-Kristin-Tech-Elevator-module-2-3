!<template>
  <div id="search-box">
    <input
      type="text"
      name="search-tb"
      id="search-tb"
      placeholder="Search..."
      @input="input"
      @keydown="checkEnter"
      v-model="searchString"
    />
    <button class="icon-button" id="search-button" @click="tryEmit">
      <font-awesome-icon icon="fa-solid fa-magnifying-glass" title="Search" />
    </button>
  </div>
</template>

<script>
export default {
  props: {
    delay: {
      type: Number,
      default: 1000,
    },
  },

  data() {
    return {
      searchString: "",
      timeoutHandle: 0,
      lastSearchString: "",
    };
  },

  methods: {
    input() {
      // If we're already waiting on a timeout, cancel that one
      this.clearTimeout();

      // Set a timer to wait "delay", and then issue the emit
      this.timeoutHandle = window.setTimeout(() => {
        this.tryEmit();
      }, this.delay);
    },

    checkEnter(e) {
      // User pressed a key. If ENTER, Emit. This way user can type a word and hit enter, and not wait for the delay
      if (e.key === "Enter") {
        this.clearTimeout();    // so we don't emit twice
        this.tryEmit();
      }
    },

    clearTimeout() {
      // If we're already waiting on a timeout, cancel that one
      if (this.timeoutHandle !== 0) {
        window.clearTimeout(this.timeoutHandle);
        this.timeoutHandle = 0;
      }
    },

    tryEmit() {
      // Only emit an event if the search string has changed
      if (this.searchString !== this.lastSearchString) {
        // Emit the event
        this.$emit("searchValueChange", this.searchString);
        // Save the last search string so we don't double up
        this.lastSearchString = this.searchString;
      }
    },
  },
};
</script>

<style scoped>
#search-box {
  display: inline-block;
  border: 1px solid darkgray;
  border-radius: 10px;
}

#search-tb {
  border: none;
  padding: 5px;
  min-width: 200px;
  background-color: transparent;
}
#search-tb:focus-visible {
  outline: none;
}

#search-button {
  color: gray;
}
</style>
