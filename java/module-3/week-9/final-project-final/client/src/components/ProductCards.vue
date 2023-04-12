<template>
  <section id="product-cards">
    <article
      class="product-card"
      v-for="product in products"
      v-bind:key="product.productId"
    >
      <div class="sku">{{ product.productSku }}</div>
      <div class="price">{{ currency(product.price) }}</div>
      <div class="product-name action" v-on:click="details(product.productId)">
        {{ product.name }}
      </div>
      <div class="product-image">
        <img v-bind:src="product.imageName" />
      </div>
      <div class="cart">
        <font-awesome-icon
          v-if="isLoggedIn"
          class="icon action"
          icon="fa-solid fa-cart-plus"
          v-on:click="addToCart(product)"
          title="Add item to cart"
        />
      </div>
    </article>
  </section>
</template>

<script>
import cartService from "../services/CartService";
export default {
  name: "ProductCards",
  props: {
    products: [],
  },

  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },

  methods: {
    currency(value) {
      return new Intl.NumberFormat(`en-US`, {
        currency: `USD`,
        style: "currency",
      }).format(value);
    },

    addToCart(product) {
      this.isLoading = true;
      cartService
        .addItem(product)
        .then(() => {
          // SUCCESS
          this.$store.commit("SET_SUCCESS", `Added '${product.name}' to cart`);
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

    details(id) {
      this.$router.push({ name: "productDetails", params: { id: id } });
    },
  },
};
</script>

<style scoped>
#product-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 12px 20px;
}

.product-card {
  display: grid;
  grid-template-rows: 25px 40px 165px 20px;
  grid-template-areas:
    "sku price"
    "name name"
    "img img"
    " . cart";
  grid-template-columns: 7fr 3fr;
  width: 250px;
  height: 250px;
  padding: 5px;
  background-color: rgba(255, 255, 255, 0.4);
  border: 1px solid rgb(91, 158, 247);
  border-radius: 10px;
  box-shadow: 5px 5px 4px rgba(91, 158, 247, 0.6);
}

div.sku {
  grid-area: sku;
}

div.price {
  grid-area: price;
  text-align: right;
  font-weight: 600;
}

div.product-name {
  grid-area: name;
  overflow: hidden;
}

div.product-image {
  grid-area: img;
  padding: 5px;
  text-align: center;
}

div.product-image > img {
  max-width: 100%;
  max-height: 100%;
  border-radius: 5px;
}

div.cart {
  grid-area: cart;
  text-align: right;
}

.action {
  cursor: pointer;
}

.icon.action {
  font-size: 1.2em;
  color: #444;
}

.action:hover {
  color: blue;
  background-color: rgba(0, 0, 0, 0.1);
}
</style>