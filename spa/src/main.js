import Vue from 'vue';
import axios from 'axios';
import Fragment from 'vue-fragment';
import App from './App.vue';
import router from './router';

Vue.use(Fragment.Plugin);

Vue.config.productionTip = false;

// Axios setup
axios.defaults.withCredentials = false;
axios.defaults.baseURL = 'http://localhost:8080/';

// Vue setup
new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
