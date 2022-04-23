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
import GenresAndTags from '@/views/GenresAndTags.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: {
      title: 'Startseite',
      announcer: {
        message: 'Startseite - Buchl',
      },
    },
  },
  {
    path: '/books',
    name: 'Books',
    component: Books,
    meta: {
      title: 'Bücher stöbern und filtern',
      announcer: {
        message: 'Bücher stöbern und filtern - Buchl',
      },
    },
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
        meta: {
          announcer: {
            skip: true,
          },
        },
      },
      {
        path: '',
        name: 'BookDetails',
        component: BookDetails,
        meta: {
          announcer: {
            skip: true,
          },
        },
      },
    ],
    meta: {
      announcer: {
        skip: true,
      },
    },
  },
  {
    path: '/favourites',
    name: 'Favourites',
    component: Favourites,
    meta: {
      title: 'Favoriten',
      announcer: {
        message: 'Favoriten - Buchl',
      },
    },
  },
  {
    path: '/discussions',
    name: 'Discussions',
    component: Discussions,
    meta: {
      title: 'Diskussionen',
      announcer: {
        message: 'Diskussionen - Buchl',
      },
    },
  },
  {
    path: '/reviews',
    name: 'Reviews',
    component: Reviews,
    meta: {
      title: 'Rezensionen',
      announcer: {
        message: 'Rezensionen - Buchl',
      },
    },
  },
  {
    path: '/discussion/:id',
    name: 'Discussion',
    component: Discussion,
    meta: {
      announcer: {
        skip: true,
      },
    },
  },
  {
    path: '/recommendations',
    name: 'Recommendations',
    component: Recommendations,
    meta: {
      title: 'Empfehlungen',
      announcer: {
        message: 'Empfehlungen - Buchl',
      },
    },
  },
  {
    path: '/tags',
    name: 'Tags',
    component: GenresAndTags,
    meta: {
      title: 'Themen und Genres',
      announcer: {
        message: 'Themen und Genres - Buchl',
      },
    },
  },
  {
    path: '*',
    name: '404',
    component: Home,
    meta: {
      title: 'Startseite',
      announcer: {
        message: 'Startseite - Buchl',
      },
    },
  },
];

const router = new VueRouter({
  routes,
  mode: 'history',
  scrollBehavior(to) {
    if (to.hash) {
      return {
        el: to.hash,
      };
    }
    return null;
  },
});

const DEFAULT_TITLE = 'Buchl';

router.beforeEach((to, from, next) => {
  // console.log('beforeEach', to, from);
  if (to.hash === '') {
    next();
  } else {
    return false;
  }
});

router.afterEach((to) => {
  // Use next tick to handle router history correctly
  // see: https://github.com/vuejs/vue-router/issues/914#issuecomment-384477609
  Vue.nextTick(() => {
    // console.log('next tick triggered');
    document.getElementById('title').focus();

    if (to.meta.title !== undefined) {
      const title = `${to.meta.title} - ${DEFAULT_TITLE}`;
      document.title = title;
      // this.$announcer.set(`Navigiere zu ${title}`);
    }
  });
});

export default router;
