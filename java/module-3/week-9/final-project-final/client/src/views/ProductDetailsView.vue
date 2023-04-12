<template>
  <div>
    <div id="heading-line">
      <h1>
        {{ product.name }}
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
      <button
        id="add-cart"
        title="Add to shopping cart"
        v-if="isLoggedIn && product"
        v-on:click="addToCart"
      >
        <font-awesome-icon class="icon action" icon="fa-solid fa-cart-plus" />
        Add to Cart
      </button>
    </div>
    <h2>Details</h2>
    <p>{{ product.productSku }} {{ product.price }}</p>
    <p>{{ product.description }}</p>
    <img v-bind:src="product.imageName" alt="Product photo" />
    <p></p>
  </div>
</template>

<script>
import productService from "../services/ProductService";
import cartService from "../services/CartService";
import LoadingSpinner from "../components/LoadingSpinner.vue";
export default {
  components: { LoadingSpinner },
  name: "ProductDetailsView",
  data() {
    return {
      product: {},
      isLoading: false,
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },
  methods: {
    getProduct(id) {
      this.isLoading = true;
      productService
        .getProductById(id)
        .then((response) => {
          this.product = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          const response = error.response;
          const message =
            "Getting product was unsuccessful: " +
            (response ? response.message : "Could not reach server");
          this.$store.commit("SET_ERROR", message);
          console.error(message);
        });
    },

    addToCart() {
      this.isLoading = true;
      cartService
        .addItem(this.product)
        .then(() => {
          // SUCCESS
          this.$store.commit(
            "SET_SUCCESS",
            `Added '${this.product.name}' to cart`
          );
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          const response = error.response;
          const message =
            "Add item was unsuccessful: " +
            (response ? response.message : "Could not reach server");
          this.$store.commit("SET_ERROR", message);
          console.error(message);
        });
    },
  },
  created() {
    this.getProduct(this.$route.params.id);
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

#add-cart:hover {
  color: green;
  background-color: rgba(0, 0, 0, 0.1);
}
</style>