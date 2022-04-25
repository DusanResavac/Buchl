<template>
  <main tabindex="-1" id="main" v-bind:aria-busy="searching">
    <div class="loader" v-show="searching" id="loadingCircle" aria-label="Lade Ergebnisse"></div>
    <aside role="region" aria-label="Nebensächliche Buchinformationen" v-if="book !== null">
      <book-image v-bind:title="book.title" v-bind:image-link="book.imageLink" v-bind:image-alt="book.imageAlt"></book-image>
      <div class="book-side">
        <div class="book-tags">
          <router-link v-for="tag in book.tags" v-bind:key="tag.id" v-bind:to="`/books?tag=${tag.id}`">
            {{ tag.parent.name === 'Lesealter' ? 'Lesealter ' + tag.name : tag.name }}
          </router-link>
        </div>
        <div class="book-info">
          <p>Seitenanzahl der Print-Ausgabe: {{ book.pages }}</p>
          <p>Sprache: {{ book.language.name }}</p>
          <p>Erscheinungsdatum: <span>{{ book.releaseDate.toLocaleDateString('de-DE', { year: 'numeric', month: 'long', day: 'numeric' }) }}</span></p>
          <p>ISBN: {{ book.isbn }}</p>
        </div>

      </div>
    </aside>
    <section role="region" aria-label="Buchinformationen" v-if="book !== null">
      <h1 class="is-size-4 is-size-3-desktop">
        <span>{{ book.title }}</span><span class="subtitle"> - {{ book.releaseDate.toLocaleDateString('de-DE', { year: 'numeric', month: 'long', day: 'numeric' }) }}</span>
      </h1>
      <div class="author-info">
        <author-image v-bind:image-link="book.author.imageLink" v-bind:image-alt="book.author.imageAlt"></author-image>
        <router-link v-bind:to="`/books?author=${book.author.id}`">
          {{ book.author.firstName }} {{ book.author.lastName }}
        </router-link>
      </div>
      <div class="book-content">
        <div class="book-rating-and-favourite">
          <router-link  v-bind:to="`/book/${book.id}/reviews`" class="rating">
            <span v-if="book.averageRating != null">{{ book.averageRating }}</span>
            <img v-if="book.averageRating != null"
                 v-bind:src="require(`../assets/imgs/SVG/rating-${Math.round(book.averageRating)}.svg`)"
                 v-bind:alt="`${Math.round(book.averageRating)} von 5 Sterne`">
            <span v-if="book.averageRating == null">Keine Bewertungen</span>
          </router-link>
          <div class="favourite">
            <button id="favouriteButton" class="button is-ghost" v-on:click="addOrRemoveFromFavourites()">{{ isFavourite ? 'Favorit entfernen' : 'Als Favorit hinzufügen' }}</button>
            <img v-bind:src="require('../assets/imgs/SVG/heart.svg')" alt="">
          </div>
        </div>
        <router-view v-bind:book="book"></router-view>
      </div>
    </section>
  </main>
</template>

<script>
import BookImage from '@/components/BookImage.vue';
import AuthorImage from '@/components/AuthorImage.vue';
import axios from 'axios';

export default {
  name: 'Book',
  data() {
    return {
      book: null,
      isFavourite: false,
      bookId: null,
      searching: true,
    };
  },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'books' });
    this.bookId = parseInt(this.$route.params.id, 10);
    if (!Number.isNaN(this.bookId)) {
      axios.get(`api/book/${this.bookId}`)
        .then((response) => {
          // console.log(response);
          this.book = response.data;
          this.book.releaseDate = new Date(this.book.releaseDate);
        })
        .catch((error) => {
          console.error(error);
        })
        .finally(() => {
          this.searching = false;
        });

      const bookFavourites = JSON.parse(localStorage.getItem('bookFavourites'));
      if (bookFavourites !== null) {
        this.isFavourite = bookFavourites.indexOf(this.bookId) > -1;
      } else {
        localStorage.setItem('bookFavourites', '[]');
      }
    }
  },
  mounted() {
    // console.log('mounted Book');
    document.getElementById('title').focus();
  },
  methods: {
    addOrRemoveFromFavourites() {
      let bookFavourites = JSON.parse(localStorage.getItem('bookFavourites'));
      if (bookFavourites === null) {
        bookFavourites = [];
        localStorage.setItem('bookFavourites', '[]');
      }

      if (this.isFavourite) {
        bookFavourites.splice(bookFavourites.indexOf(this.bookId), 1);
      } else {
        bookFavourites.push(this.bookId);
      }

      localStorage.setItem('bookFavourites', JSON.stringify(bookFavourites));

      this.isFavourite = !this.isFavourite;
    },
  },
  components: { BookImage, AuthorImage },
};
</script>

<style scoped>
a:focus {
  outline: none;
  border: 2px dashed #485fc7 !important;
}

main {
  display: flex;
  flex-direction: column;
  margin: 1.5rem 4% 0 4%;
}

main > aside {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f2f2f2;
  padding: 15px 10px 15px 10px;
  margin: 0;
}

main > aside > img {
  max-width: 300px;
  width: 100%;
}

.book-side {
  display: flex;
  flex-direction: column;
}

.book-tags {
  display: flex;
  flex-wrap: wrap;
  margin: 20px 0 10px 0;
}

.book-tags > a {
  border: 1px solid #485fc7;
  border-radius: 20px;
  padding: 5px 10px 5px 10px;
  margin: 5px;
}

.book-info > p {
  margin: 5px 0 5px 0;
}

.book-info > p > span {
  white-space: nowrap;
}

.author-info {
  margin-top: 15px;
  display: flex;
  align-items: center;
}

.author-info > img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 100%;
}

.author-info > a {
  margin-left:  15px;
}

.book-rating-and-favourite {
  display: flex;
  justify-content: space-between;
  padding: 10px 0 10px 0;
}

.book-rating-and-favourite > .rating {
  display: flex;
  align-items: center;
  width: auto;
}

.book-rating-and-favourite > .rating > span {
  font-size: 1em;
  margin-right: 10px;
}

.book-rating-and-favourite > .rating > img {
  min-width: 80px;
}

.book-rating-and-favourite > .favourite {
  display: flex;
  flex-grow: 1;
  align-items: center;
  justify-content: end;
}

.book-rating-and-favourite > .favourite > button {
  margin-right: 10px;
  white-space: nowrap;
}

.book-rating-and-favourite > .favourite > img {
  height: 100%;
  max-height: 50px;
}

.discussions > article > a:focus {
  border: none;
  outline: none;
}

.discussions > article > a:focus > div {
  border: 2px dashed #485fc7;
}

@media all and (min-width: 769px) {
  main {
    flex-direction: row;
    margin: 1.5rem auto 0 auto;
    max-width: 1100px;
  }

  main > aside {
    padding: 25px 20px 25px 20px;
    margin-right: 30px;
    width: 30%;
  }

  main > section {
    width: 70%;
  }

  h1 {
    color: #232323;
  }

  h1 .subtitle {
    font-size: 1.7rem;
    color: #565959;
  }

  .book-rating-and-favourite > .rating > span {
    font-size: 1.2rem;
  }

  .book-rating-and-favourite > .rating > img {
    min-width: 150px;
  }

  .book-content > p {
    max-width: 600px;
    font-size: 1.1rem;
  }

  .book-rating-and-favourite > .favourite > button {
    margin-right: 10px;
    white-space: initial;
  }

  .book-side {
    max-width: 100%;
    min-width: 200px;
  }
}
</style>
