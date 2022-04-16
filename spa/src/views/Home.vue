<template>
  <main tabindex="-1" id="main" class="columns">
    <section class="column is-desktop">
      <h1 class="is-size-2">Was ist Buchl?</h1>
      <p class="content">Buchl ist eine Plattform, auf der sich Gleichgesinnte treffen und über ihre Lieblingsbücher unterhalten können.
        Außerdem kann die eigene Buchsammlung digital in einer Liste gesammelt werden, auf deren Basis dann Vorschläge
        zur Erweiterung des Leserepertoires bereitgestellt werden. Nehmen Sie an hitzigen Debatten teil, um Ihren Standpunkt zu vertreten
        oder informieren Sie sich über beliebte Klassiker.
      </p>
    </section>
    <section class="column">
      <h2 class="is-size-2">Heutige Favoriten</h2>
      <div class="todays-favourites">
        <article v-for="book in books" v-bind:key="book.id">
          <div class="book-image-wrapper">
            <figure class="image">
              <book-image v-bind:title="book.title" v-bind:image-alt="book.imageAlt" v-bind:image-link="book.imageLink"></book-image>
            </figure>
          </div>
          <div class="article-book-content">
            <h3 class="mb-4">
              <router-link v-bind:title="book.title" v-bind:to="`/book/${book.id}`">{{ book.title }}</router-link></h3>
            <div class="book-infos">
              <p>
                <span>{{getYearFromDateString(book.releaseDate)}}</span>
                <span v-bind:aria-label="`Geschrieben von ${book.author.firstName} ${book.author.lastName}`"
                      v-bind:title="`${book.author.firstName} ${book.author.lastName}`">
                    {{book.author.lastName === null || book.author.lastName === '' ? book.author.firstName : book.author.lastName}}
                  </span>
              </p>
            </div>
          </div>
        </article>
      </div>
    </section>
  </main>
</template>

<script>
import axios from 'axios';
import BookImage from '@/components/BookImage.vue';

export default {
  name: 'Home',
  components: { BookImage },
  data() {
    return {
      books: [],
    };
  },
  methods: {
    getYearFromDateString(dateString) {
      const date = new Date(dateString);
      return date.getFullYear();
    },
  },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'home' });
    axios.get('api/books/top3')
      .then((response) => {
        this.books = response.data;
        // console.log(response);
      });
  },
};
</script>

<style scoped>
main.columns {
  margin: 1rem 6% 0 6%;
}

/*main {
    display: flex;
}

main > section {
    flex: 1 1 100%;
}

main > section:first-child {
    padding-right: 15px;
}

main > section:last-child {
    padding-left: 15px;
}*/

.todays-favourites {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.todays-favourites > article {
  margin-top: 15px;
  width: 45%;
  display: flex;
  flex-direction: column;
}

.book-image-wrapper {
  height: 300px;
  display: flex;
  align-items: center;
}

.book-image-wrapper > figure {
  margin: 0 auto;
}

.book-image-wrapper img {
  max-height: 300px;
}

.article-book-content {
  min-height: 130px;
  display: flex;
  flex-direction: column;
  flex: 1 1 0%;
  justify-content: space-between;
  padding: .8rem;
  background-color: #f5f5f5;
}

.article-book-content > h3 {
  display: -webkit-box;
  font-size: 1.07em;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.book-infos > p {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

@media all and (min-width: 500px) {
  main.columns {
    margin: 1.5rem 15% 0 15%;
  }
}

@media all and (min-width: 769px) {
  .todays-favourites > article {
    width: 30%;
  }
}
</style>
