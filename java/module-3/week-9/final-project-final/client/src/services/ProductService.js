import axios from 'axios';

/**
 * This service class is used to interact with the Product API.
 * All methods return a Promise so that the calling code can handle both success and 
 * error responses appropriately. 
 */
export default {

  getProducts() {
    return axios.get('/products');
  },

  searchProducts(name) {
    let querystring = "";
    if (name) {
        querystring += `name=${name}`;
    }
    if (querystring) {
        querystring = "?" + querystring;
    }
    return axios.get('/products' + querystring);
  },

  getProductById(productId) {
    return axios.get(`/products/${productId}`);
  },
  
}
