import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'

// resets browser defaults
import '../public/css/reset.css';  
// sets global styles for both static & shared Vue components such as the app header
import '../public/css/global.css'; 

/* import fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faUser, faUsers, faUserCircle, faTrashCan, faPencil, faCirclePlus, faTriangleExclamation, faXmark, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'

/* add icons to the library */
library.add(faUserCircle)
library.add(faUser)
library.add(faUsers)
library.add(faTrashCan)
library.add(faPencil)
library.add(faCirclePlus)
library.add(faTriangleExclamation)
library.add(faXmark)
library.add(faMagnifyingGlass)

/* add font awesome icon component */
Vue.component('font-awesome-icon', FontAwesomeIcon)

/* prevent the display of production mode messages */
Vue.config.productionTip = false

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
