<template>
  <fragment>
    <app-header main-id="#main" active-link="books"></app-header>
    <main id="main" aria-busy="true">
      <h1>Nach Genre und Themen filtern</h1>
      <div>
        <section class="genre"
                 v-for="rootTag in rootTags"
                 v-bind:key="rootTag.id">
          <h2>{{ rootTag.name }}</h2>
          <ul>
            <li v-for="tag in rootTag.tags" v-bind:key="tag.id">
              <router-link v-bind:to="`/books?tag=${tag.id}`">{{ tag.name }}</router-link>
            </li>
          </ul>
        </section>
      </div>
    </main>
  </fragment>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';
import { Fragment } from 'vue-fragment';
import axios from 'axios';

export default {
  name: 'GenreAndTags',
  data() {
    return {
      rootTags: [],
    };
  },
  created() {
    axios.get('api/tags')
      .then((response) => {
        console.log('Tags', response.data);
        this.rootTags = response.data;
      });
  },
  components: { AppHeader, Fragment },
};
</script>

<style scoped>
h1 {
  font-size: 1.5rem;
  margin-bottom: 15px;
}

h2 {
  font-size: 1.3rem;
  margin-bottom: 15px;
}

h3 {
  font-size: 1.1rem;
  margin-bottom: 10px;
}

main {
  display: block;
  margin: 1.5rem 4% 0 4%;
}

main > div {
  columns: 2;
}

.genre {
  margin-bottom: 15px;
}

@media all and (min-width: 769px) {
  main {
    padding: 0 10px 0 10px;
    margin: 1.5rem auto 0 auto;
    max-width: 1100px;
  }

  main > div {
    max-height: 600px;
    columns: 4;
  }
}
</style>
