import Vue from 'vue'
import Router from 'vue-router'
import MyBookmarksView from '../views/MyBookmarksView.vue'
import MyProfileView from '../views/MyProfileView.vue'
import UserProfileView from '../views/UserProfileView.vue'
import PublicBookmarksView from '../views/PublicBookmarksView.vue'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import store from '../store/index'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: {name: 'myBookmarks'},
    },
    {
      path: '/profile',
      name: 'myProfile',
      component: MyProfileView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/bookmarks',
      name: 'myBookmarks',
      component: MyBookmarksView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/bookmarks/public',
      name: 'publicBookmarks',
      component: PublicBookmarksView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/profile/:userId',
      name: 'userProfile',
      component: UserProfileView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: LogoutView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView,
      meta: {
        requiresAuth: false
      }
    }
  ]
})

/*
 * The beforeEach method runs before each route. It is a feature of Vue Router 
 * called a Navigation Guard (https://router.vuejs.org/guide/advanced/navigation-guards.html). 
 * 
 * This method checks to see if the route requires authentication as defined in the routes 
 * setup using the meta object key `requiresAuth`. If auth is required and the user is not 
 * logged in, it will redirect to the login view. Otherwise it allows them to go through to
 * their destination route. 
 * 
 * It will also clear error notifications, but not success notifications. 
 */
router.beforeEach((to, from, next) => {

  // Clear error notifications from previous view
  store.commit("CLEAR_ERROR_NOTIFICATIONS");
  
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next({name: "login"});
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
