<template>
  <main tabindex="-1" id="main" v-bind:aria-busy="searching">
    <h1>Deine personalisierten Vorschläge</h1>
    <div class="loader" v-show="searching" id="loadingCircle" aria-label="Lade Ergebnisse"></div>
    <section id="recommendations">
      <p v-if="books.length === 0">Du hast noch keine Favoriten. Füge Bücher zu deinen Favoriten hinzu, um Buchvorschläge zu erhalten! Du kannst ein Buch auf der Buchseite als Favorit markieren.</p>
      <div class="recommendation-wrapper"
           v-for="book in books"
           v-bind:key="book.bookId">
        <h2>Andere, die <router-link v-bind:to="`/book/${book.bookId}`">{{ book.bookTitle }}</router-link> mochten, mögen auch: </h2>
        <div class="recommendation">
          <div class="book"
               v-for="recommendation in book.recommendations"
               v-bind:key="recommendation.id">
            <book-image
                v-bind:title="recommendation.title"
                v-bind:image-link="recommendation.imageLink"
                v-bind:image-alt="recommendation.imageAlt"></book-image>
            <div class="book-info">
              <h3>
                <router-link v-bind:to="`/book/${recommendation.id}`">{{ recommendation.title }}</router-link>
              </h3>
              <div>
                <span v-if="recommendation.averageRating != null" style="white-space: nowrap;">Ø {{ recommendation.averageRating }}</span>
                <img v-if="recommendation.averageRating != null"
                     v-bind:src="require(`../assets/imgs/SVG/rating-${Math.round(recommendation.averageRating)}.svg`)"
                     v-bind:alt="`${Math.round(recommendation.averageRating)} von 5 Sterne`">
                <span v-if="recommendation.averageRating == null">Noch keine Bewertungen</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script>
import axios from 'axios';
import BookImage from '@/components/BookImage.vue';

export default {
  name: 'Recommendations',
  data() {
    return {
      books: [],
      searching: true,
    };
  },
  components: { BookImage },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'recommendations' });
    const favourites = localStorage.getItem('bookFavourites') === null ? [] : JSON.parse(localStorage.getItem('bookFavourites'));
    if (favourites === []) {
      localStorage.setItem('bookFavourites', '[]');
    }
    axios.post('api/recommendations', favourites, {
      'content-type': 'text/json',
    })
      .then((response) => {
        // console.log('Recommendations', response.data);
        this.books = response.data;
      })
      .finally(() => {
        this.searching = false;
      });
  },
};
</script>

<style scoped>
h1 {
  font-size: 1.6rem;
  margin-bottom: 15px;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 15px;
}

h3 {
  font-size: 1.3rem;
  margin-bottom: 10px;
}

main {
  display: block;
  margin: 1.5rem 4% 0 4%;
}

main > #recommendations {
  display: flex;
  flex-direction: column;
}

.recommendation-wrapper > .recommendation {
  display: flex;
  flex-wrap: wrap;
}

.recommendation > .book {
  display: flex;
  flex-direction: column;
  width: 150px;
  margin: 0 30px 30px 0;
}

.book > img {
  height: 200px;
  padding: 0 10px 5px 10px;
  object-fit: contain;
}

.book > .book-info {
  flex: 1 1 0%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.book-info > h3 {
  text-align: center;
}

.book > .book-info > div {
  display: flex;
  align-items: center;
}

.book-info > div > span {
  margin-right: 10px;
}

.book-info > div > img {
  min-width: 0;
}

@media all and (min-width: 769px) {
  main {
    margin: 1.5rem 10px 0 10px;
    max-width: 1100px;
  }
}

@media all and (min-width: 1150px) {
  main {
    margin: 1.5rem auto 0 auto;
  }
}
</style>
