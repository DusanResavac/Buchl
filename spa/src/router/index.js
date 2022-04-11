import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import Books from '@/views/Books.vue';
import Book from '@/views/Book.vue';
import Favourites from '@/views/Favourites.vue';
import Discussions from '@/views/Discussions.vue';
import Reviews from '@/views/Reviews.vue';
import BookReviews from '@/components/BookReviews.vue';
import BookDetails from '@/components/BookDetails.vue';
import Discussion from '@/views/Discussion.vue';
import Recommendations from '@/views/Recommendations.vue';
import GenreAndTags from '@/views/GenreAndTags.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { title: 'Startseite' },
  },
  {
    path: '/books',
    name: 'Books',
    component: Books,
    meta: { title: 'Bücher stöbern und filtern' },
  },
  {
    path: '/book/:id',
    name: 'Book',
    component: Book,
    children: [
      {
        path: 'reviews',
        name: 'BookReviews',
        component: BookReviews,
      },
      {
        path: '',
        name: 'BookDetails',
        component: BookDetails,
      },
    ],
    meta: { },
  },
  {
    path: '/favourites',
    name: 'Favourites',
    component: Favourites,
    meta: { title: 'Favoriten' },
  },
  {
    path: '/discussions',
    name: 'Discussions',
    component: Discussions,
    meta: { title: 'Diskussionen' },
  },
  {
    path: '/reviews',
    name: 'Reviews',
    component: Reviews,
    meta: { title: 'Rezensionen' },
  },
  {
    path: '/discussion/:id',
    name: 'Discussion',
    component: Discussion,
    meta: { },
  },
  {
    path: '/recommendations',
    name: 'Recommendations',
    component: Recommendations,
    meta: { title: 'Empfehlungen' },
  },
  {
    path: '/tags',
    name: 'Tags',
    component: GenreAndTags,
    meta: { title: 'Themen und Genre' },
  },
  {
    path: '*',
    name: '404',
    component: Home,
    meta: { title: 'Startseite' },
  },
  /* {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { hasNavigation: true },
  },
  {
    path: '/portfolio/overview',
    name: 'Portfolio-Overview',
    component: Overview,
    props: true,
    meta: { hasNavigation: true },
  },
  {
    path: '/transactions',
    name: 'Transactions',
    component: Transactions,
    meta: { hasNavigation: true },
  },
  {
    path: '/users',
    name: 'Users',
    component: Users,
    meta: { hasNavigation: true },
  },
  {
    path: '/users/new',
    name: 'NewUser',
    component: NewUser,
    meta: { hasNavigation: true },
  },
  {
    path: '/users/:id',
    name: 'SingleUser',
    component: SingleUser,
    meta: { hasNavigation: true },
  },
  {
    path: '/accounts',
    name: 'Accounts',
    component: Accounts,
    meta: { hasNavigation: true },
  },
  {
    path: '/accounts/new',
    name: 'NewAccount',
    component: NewAccount,
    meta: { hasNavigation: true },
  },
  {
    path: '/accounts/:id',
    name: 'SingleAccount',
    component: SingleAccount,
    meta: { hasNavigation: true },
  },
  {
    path: '/stock-exchanges',
    name: 'StockExchanges',
    component: StockExchanges,
    meta: { hasNavigation: true },
  },
  {
    path: '/stocks',
    name: 'Stocks',
    component: Stocks,
    meta: { hasNavigation: true },
  },
  {
    path: '/stocks/new',
    name: 'NewStock',
    component: NewStock,
    meta: { hasNavigation: true },
  },
  {
    path: '/stocks/:id',
    name: 'SingleStock',
    component: SingleStock,
    meta: { hasNavigation: true },
  },
  {
    path: '/sign-in',
    name: 'SignIn',
    component: SignIn,
    meta: { isPublic: true },
  },
  {
    path: '/sign-up',
    name: 'SignUp',
    component: SignUp,
    meta: { isPublic: true },
  },
  {
    path: '/legal',
    name: 'Legal',
    component: Legal,
    meta: { isPublic: true },
  },*/
];

const router = new VueRouter({
  routes,
  mode: 'history',
});

const DEFAULT_TITLE = 'Buchl';

router.afterEach((to) => {
  // Use next tick to handle router history correctly
  // see: https://github.com/vuejs/vue-router/issues/914#issuecomment-384477609
  Vue.nextTick(() => {
    document.title = to.meta.title === undefined ? DEFAULT_TITLE : `${to.meta.title} - ${DEFAULT_TITLE}`;
  });
});

export default router;
