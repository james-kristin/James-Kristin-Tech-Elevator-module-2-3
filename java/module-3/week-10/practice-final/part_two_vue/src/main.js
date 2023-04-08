import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import axios from 'axios'

/* prevent the display of production mode messages */
Vue.config.productionTip = false

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
