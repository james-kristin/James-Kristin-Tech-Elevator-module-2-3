import Vue from 'vue';
import Router from 'vue-router';
import MovieDetailsView from '../views/MovieDetailsView.vue';
import AllMoviesView from '../views/AllMoviesView.vue';

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: {name: 'allMoviesView'},
    },
    {
      path: '/movies/details',
      name: 'movieDetails',
      component: MovieDetailsView,
    },
    {
      path: '/movies',
      name: 'allMoviesView',
      component: AllMoviesView,
    }
  ]
})

export default router;
