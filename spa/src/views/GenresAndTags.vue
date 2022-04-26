<template>
  <main tabindex="-1" id="main" aria-label="Inhalt">
    <h1>Nach Themen und Genres filtern</h1>
    <div class="loader" v-show="searching" id="loadingCircle" aria-label="Lade Ergebnisse"></div>
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
</template>

<script>
import axios from 'axios';

export default {
  name: 'GenresAndTags',
  data() {
    return {
      rootTags: [],
      searching: true,
    };
  },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'books' });
    axios.get('api/tags')
      .then((response) => {
        // console.log('Tags', response.data);
        this.rootTags = response.data;
      })
      .finally(() => {
        this.searching = false;
      });
  },
  mounted() {
    // console.log('mounted GenresAndTags');
    document.getElementById('title').focus();
  },
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
