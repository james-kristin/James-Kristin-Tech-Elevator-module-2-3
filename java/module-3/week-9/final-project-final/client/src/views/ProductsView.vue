<template>
  <div class="home">
    <div id="heading-line">
      <h1>
        Products
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
      <div>
        <font-awesome-icon
          v-bind:class="{ 'view-icon': true, active: cardView }"
          v-on:click="cardView = true"
          icon="fa-solid fa-grip"
          title="View tiles"
        />
        <font-awesome-icon
          v-bind:class="{ 'view-icon': true, active: !cardView }"
          v-on:click="cardView = false"
          icon="fa-solid fa-table"
          title="View table"
        />
        <div id="search-box">
          <input
            type="text"
            name="search-tb"
            id="search-tb"
            placeholder="Search..."
            v-on:keydown="checkSearchEnter"
            v-model="filter"
          />
          <button
            class="icon-button"
            id="search-button"
            v-on:click="getProducts"
            tabindex="-1"
          >
            <font-awesome-icon
              icon="fa-solid fa-magnifying-glass"
              title="Search"
            />
          </button>
        </div>
      </div>
    </div>
    <p id="login-message" v-if="!isLoggedIn">
      Welcome! You may browse anonymously as much as you wish,<br />
      but you must
      <router-link v-bind:to="{ name: 'login' }">Login</router-link> to add
      items to your shopping cart.
    </p>
    <product-cards v-bind:products="products" v-if="cardView" />
    <product-table v-bind:products="products" v-else />
  </div>
</template>

<script>
import productService from "../services/ProductService";
import LoadingSpinner from "../components/LoadingSpinner.vue";
import ProductTable from "../components/ProductTable.vue";
import ProductCards from "../components/ProductCards.vue";
export default {
  name: "ProductsView",
  components: {
    LoadingSpinner,
    ProductTable,
    ProductCards,
  },
  data() {
    return {
      isLoading: false,
      products: [],
      filter: "",
      cardView: true,
    };
  },

  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },

  methods: {
    getProducts() {
      if (this.filter) {
        this.searchProducts();
        return;
      }

      this.isLoading = true;
      productService
        .getProducts()
        .then((response) => {
          this.products = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          // Something unexpected happened
          const response = error.response;
          const message =
            "Getting products was unsuccessful: " +
            (response ? response.message : "Could not reach server");
          this.$store.commit("SET_ERROR", message);
          console.error(message);
        });
    },
    searchProducts() {
      this.isLoading = true;
      productService
        .searchProducts(this.filter)
        .then((response) => {
          this.products = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          // Something unexpected happened
          const response = error.response;
          const message =
            "Getting products was unsuccessful: " +
            (response ? response.message : "Could not reach server");
          this.$store.commit("SET_ERROR", message);
          console.error(message);
        });
    },

    checkSearchEnter(e) {
      // User pressed a key. If ENTER, perform the search.
      if (e.key === "Enter") {
        this.getProducts();
      }
    },
  },

  created() {
    this.getProducts();
  },
};
</script>

<style scoped>
#heading-line {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-start;
}

#spinner {
  color: green;
}

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #444;
  border-radius: 3px;
}

.view-icon.active {
  background-color: lightgreen;
}

.view-icon:not(.active) {
  font-size: 1.2rem;
  margin-right: 7px;
  cursor: pointer;
}

.view-icon:not(.active):hover {
  color: blue;
  background-color: rgba(255, 255, 255, 0.7);
}

/* Search box styling */
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
  cursor: pointer;
  background-color: transparent;
  border: none;
}
</style>