<template>
  <fragment>
    <app-header active-link="favourites" main-id="#main"></app-header>
    <main id="main" v-bind:aria-busy="searching">
      <h1>Deine Favoriten</h1>
      <section id="allFavourites">
        <div class="loader" v-show="searching" v-bind:aria-hidden="!searching" id="loadingCircle"
             aria-label="Lade Ergebnisse"
             v-bind:aria-busy="searching"></div>
        <p v-if="!searching && books.length === 0">Du hast noch keine Favoriten. Füge Bücher zu deinen Favoriten hinzu, um Buchvorschläge zu erhalten! Du kannst ein Buch auf der Buchseite als Favorit markieren.</p>
        <article v-for="book in books" v-bind:key="book.id">
          <button class="heartButton" v-bind:title="`${book.title} aus den Favoriten entfernen`" v-on:click="removeBookFromFavourites(book)">
            <img v-bind:src="require('../assets/imgs/SVG/heart-red.svg')" alt="">
          </button>
          <book-image v-bind:title="book.title" v-bind:image-link="book.imageLink" v-bind:image-alt="book.imageAlt"></book-image>
          <h2>
            <router-link v-bind:to="`/book/${book.id}`">
              {{ book.title }}
            </router-link>
          </h2>
        </article>
      </section>
    </main>
  </fragment>
</template>

<script>
import axios from 'axios';
import AppHeader from '@/components/AppHeader.vue';
import { Fragment } from 'vue-fragment';
import BookImage from '@/components/BookImage.vue';

export default {
  name: 'Favourites',
  data() {
    return {
      searching: true,
      books: [],
    };
  },
  created() {
    let bookFavourites = JSON.parse(localStorage.getItem('bookFavourites'));
    console.log(bookFavourites);
    if (bookFavourites === null) {
      localStorage.setItem('bookFavourites', '[]');
      bookFavourites = [];
    }
    axios.post('api/books-short', bookFavourites, {})
      .then((response) => {
        console.log(response);
        this.books = response.data;
        this.searching = false;
      });
  },
  methods: {
    removeBookFromFavourites(book) {
      let bookFavourites = JSON.parse(localStorage.getItem('bookFavourites'));
      if (bookFavourites === null) {
        bookFavourites = [];
        localStorage.setItem('bookFavourites', '[]');
      }
      bookFavourites.splice(bookFavourites.indexOf(book.id), 1);
      localStorage.setItem('bookFavourites', JSON.stringify(bookFavourites));
      for (let i = 0; i < this.books.length; i += 1) {
        if (this.books[i].id === book.id) {
          this.books.splice(i, 1);
          break;
        }
      }
    },
  },
  components: { BookImage, AppHeader, Fragment },
};
</script>

<style scoped>
h1 {
  font-size: 1.5rem;
  margin-bottom: 25px;
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

#allFavourites {
  display: flex;
  flex-wrap: wrap;
}

#allFavourites > article {
  display: flex;
  align-items: center;
  flex-direction: column;
  position: relative;
  width: 250px;
  margin: 0 30px 30px 0;
}

#allFavourites > article > button {
  padding: 0;
  background: none;
  border: none;
  cursor: pointer;
  position: absolute;
  right: 0;
}

#allFavourites > article > img {
  object-fit: contain;
  height: 200px;
  margin: 0 40px 20px 40px;
}

#allFavourites > article > h2 {
  text-align: center;
}

@media all and (min-width: 769px) {
  main {
    margin: 1.5rem auto 0 auto;
    max-width: 1100px;
  }
}
</style>
