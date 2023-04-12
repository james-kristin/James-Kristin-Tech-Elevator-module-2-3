<template>
  <div class="home">
    <div id="heading-line">
      <h1>
        Shopping Cart <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
      <button id="clear-cart" v-on:click="clearCart">
        <font-awesome-icon icon="fa-solid fa-trash-can" /> Clear Cart
      </button>
    </div>
    <table id="cart-table">
      <thead>
        <tr>
          <th class="right">Qty</th>
          <th>Product</th>
          <th class="right">Price</th>
          <th class="right">Amount</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in cart.items" v-bind:key="item.cartItemId">
          <td class="right">
            {{ item.quantity }}
          </td>

          <td class="action">
            <router-link
              v-bind:to="{
                name: 'productDetails',
                params: { id: item.productId },
              }"
            >
              {{ item.product.name }}
            </router-link>
          </td>
          <td class="right">
            {{ currency(item.product.price) }}
          </td>
          <td class="right">
            {{ currency(item.product.price * item.quantity) }}
          </td>
          <td class="actions">
            <font-awesome-icon
              class="icon delete-action"
              icon="fa-solid fa-xmark"
              title="Remove from cart"
              v-on:click="removeItem(item.cartItemId)"
            />
          </td>
        </tr>

        <!-- Sub-total and total lines -->
        <tr class="begin-summary summary">
          <td></td>
          <td style="border-top: 1px solid black">Item subtotal:</td>
          <td></td>
          <td class="right">{{ currency(cart.itemSubtotal) }}</td>
          <td></td>
        </tr>
        <tr class="summary">
          <td></td>
          <td>Tax:</td>
          <td></td>
          <td class="right">{{ currency(cart.tax) }}</td>
          <td></td>
        </tr>
        <tr class="summary">
          <td></td>
          <td>Total:</td>
          <td></td>
          <td class="right">{{ currency(cart.total) }}</td>
          <td></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import cartService from "../services/CartService";
export default {
  components: { LoadingSpinner },
  name: "CartView",
  data() {
    return {
      isLoading: false,
      cart: {},
    };
  },

  methods: {
    getCart() {
      this.isLoading = true;
      cartService
        .getCart()
        .then((response) => {
          this.cart = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          // Something unexpected happened
          const response = error.response;
          const message =
            "Getting cart was unsuccessful: " +
            (response ? response.message : "Could not reach server");
          this.$store.commit("SET_ERROR", message);
          console.error(message);
        });
    },
    removeItem(itemId) {
      this.isLoading = true;
      cartService
        .deleteItem(itemId)
        .then(() => {
          this.isLoading = false;
          this.getCart();
        })
        .catch((error) => {
          this.isLoading = false;
          // Something unexpected happened
          const response = error.response;
          const message =
            "Removing item from cart was unsuccessful: " +
            (response ? response.message : "Could not reach server");
          this.$store.commit("SET_ERROR", message);
          console.error(message);
        });
    },
    clearCart() {
      this.isLoading = true;
      cartService
        .clearCart()
        .then(() => {
          this.isLoading = false;
          this.getCart();
        })
        .catch((error) => {
          this.isLoading = false;
          // Something unexpected happened
          const response = error.response;
          const message =
            "Clear cart was unsuccessful: " +
            (response ? response.message : "Could not reach server");
          this.$store.commit("SET_ERROR", message);
          console.error(message);
        });
    },
    currency(value) {
      if (isNaN(value)) return "";
      return new Intl.NumberFormat(`en-US`, {
        currency: `USD`,
        style: "currency",
      }).format(value);
    },
  },

  created() {
    this.getCart();
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
table#cart-table {
  min-width: 500px;
  border-collapse: collapse;
}
th,
td {
  padding: 2px 7px;
  text-align: left;
}
tr.summary {
  font-weight: 600;
}
tr.begin-summary > td {
  border-top: 1px solid black;
}

.right {
  text-align: right;
}
.action,
.delete-action {
  cursor: pointer;
}
.action > a {
  text-decoration: none;
  color: inherit;
}
.icon.delete-action {
  font-size: 1.2em;
  color: #444;
}
.delete-action:hover,
#clear-cart:hover {
  color: red;
  background-color: rgba(0, 0, 0, 0.1);
}
.action:hover {
  color: blue;
  background-color: rgba(0, 0, 0, 0.1);
}

.actions {
  padding-left: 20px;
}
</style>