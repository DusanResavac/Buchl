import Vue from 'vue';
import 'core-js/stable';
import 'regenerator-runtime/runtime';
import axios from 'axios';
import Fragment from 'vue-fragment';
import VueAnnouncer from '@vue-a11y/announcer';
import App from './App.vue';
import router from './router';

Vue.use(VueAnnouncer, { complementRoute: 'wurde geladen' }, router);
Vue.use(Fragment.Plugin);

Vue.config.productionTip = false;

// Axios setup
axios.defaults.withCredentials = false;
axios.defaults.baseURL = 'http://dusan-resavac.ddns.net:4444/';

if ('-ms-scroll-limit' in document.documentElement.style && '-ms-ime-align' in document.documentElement.style) { // detect it's IE11
  window.addEventListener('hashchange', () => {
    const currentPath = window.location.hash.slice(1);
    router.push(currentPath);
  }, false);
}

// Vue setup
new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
