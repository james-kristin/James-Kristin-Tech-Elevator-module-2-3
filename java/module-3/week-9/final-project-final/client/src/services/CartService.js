import axios from 'axios';

/**
 * This service class is used to interact with the Product API.
 * All methods return a Promise so that the calling code can handle both success and 
 * error responses appropriately. 
 */
export default {

  getCart() {
    return axios.get('/cart');
  },

  addItem(product) {
    const item = {
      productId: product.productId,
      quantity: 1,
    };
    return axios.post('/cart/items', item);
  },

  clearCart() {
    return axios.delete(`/cart`);
  },

  deleteItem(itemId) {
    return axios.delete(`/cart/items/${itemId}`);
  },

}
