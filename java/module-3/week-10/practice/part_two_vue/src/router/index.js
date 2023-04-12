import Vue from 'vue';
import Router from 'vue-router';
import AuthorDetailsView from '../views/AuthorDetailsView.vue';
import BookDetailsView from '../views/BookDetailsView.vue';
import AllBooksView from '../views/AllBooksView.vue';

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
      redirect: {name: 'allBooksView'},
    },
    {
      path: '/authors/:authorId',
      name: 'authorDetails',
      component: AuthorDetailsView,
    },
    {
      path: '/books/details',
      name: 'bookDetails',
      component: BookDetailsView,
    },
    {
      path: '/books',
      name: 'allBooksView',
      component: AllBooksView,
    }
  ]
})

export default router;
