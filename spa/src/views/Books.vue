<template>
  <main tabindex="-1" id="main">
    <form id="form" method="get" action="/books" v-on:submit="getBooksFromApi">
      <h1 class="is-size-3 is-size-4-mobile mb-3">Suchen und Filtern</h1>
      <a href="#search" class="visually-hidden">Filter überspringen</a>
      <div role="search" class="form-content">
        <div class="form-combobox-wrapper">
          <label class="label" for="tag">Thema oder Lesealter</label>
          <div class="select">
            <select class="select form-data" id="tag" name="tag" v-model="tag">
              <option value="">Kein Thema</option>
              <optgroup v-for="parentTag in tags" v-bind:key="parentTag.id" v-bind:label="parentTag.name">
                <option v-for="childTag in parentTag.tags" v-bind:key="childTag.id"
                        v-bind:value="childTag.id">
                  {{ childTag.name }}
                </option>
              </optgroup>
            </select>
          </div>
        </div>
        <div class="form-combobox-wrapper">
          <label class="label" for="rating">Mindestbewertung</label>
          <div class="select">
            <select class="select form-data" v-model="rating" id="rating" name="rating">
              <option value="" v-bind:selected="rating === undefined || rating === null">Keine Mindestbewertung</option>
              <option v-for="star in [1, 2, 3, 4, 5]" v-bind:key="star"
                      v-bind:value="star"
                      v-bind:selected="rating === star">
                {{ star === 1 ? '1 Stern' : star + ' Sterne' }}
              </option>
            </select>
          </div>
        </div>
        <div class="form-combobox-wrapper">
          <label class="label" for="author">Autor</label>
          <div class="select">
            <select id="author" v-model="author" class="form-data" name="author">
              <option value="" v-bind:selected="author === undefined || author === null">Kein(e) bestimmte(r) Autor(in)</option>
              <option v-for="a in authors" v-bind:key="a.id"
                      v-bind:value="a.id"
                      v-bind:selected="author === a.id">
                {{ a.firstName }} {{ a.lastName }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <p class="label" id="releaseYear">Erscheinungsjahr</p>
          <div class="release-year-wrapper">
            <div>
              <label class="label" for="releaseYearFrom">Ab</label>
              <div class="control">
                <input id="releaseYearFrom"
                       aria-label="Erscheinungsjahr ab"
                       class="input form-data"
                       v-model="releaseYearFrom" type="number"
                       min="1900" max="2022" name="releaseYearFrom">
              </div>
            </div>
            <div>
              <label class="label" for="releaseYearUntil">Bis</label>
              <div class="control">
                <input id="releaseYearUntil"
                       aria-label="Erscheinungsjahr bis"
                       class="input form-data"
                       v-model="releaseYearUntil" type="number"
                       min="1900" max="2022" name="releaseYearUntil">
              </div>
            </div>
          </div>
        </div>
        <div id="searchWrapper">
          <div>
            <label class="label" for="search">Suchen Sie nach Buchtitel, ISBN oder der Buchbeschreibung</label>
            <div class="control">
              <input type="search"
                     class="input form-data"
                     id="search"
                     placeholder="Nach Buchtitel, ISBN oder der Buchbeschreibung suchen ..."
                     name="q"
                     v-model="search">
            </div>
          </div>
          <div class="control">
            <button class="button is-link" id="submitButton" type="submit" v-bind:aria-busy="searching">Suchen</button>
          </div>
        </div>
      </div>
    </form>
    <section>
      <h2 class="is-size-4">Suchergebnisse</h2>
      <div id="searchResults" ref="searchResults" role="region" aria-label="Suchergebnisse" v-bind:aria-busy="searching">
        <div class="loader" v-show="searching" id="loadingCircle"
             aria-label="Lade Ergebnisse"
             v-bind:aria-busy="searching"></div>
        <p v-show="books.length > 0">{{ books.length === 1 ? '1 Suchergebnis' : `${books.length} Suchergebnisse` }}</p>
        <p v-show="books.length === 0 && !searching" id="noSearchResults">Es sind keine Suchergebnisse vorhanden.</p>
        <ul class="article-wrapper">
          <li v-for="book in books" v-bind:key="book.id">
            <book-overview v-bind:book="book"></book-overview>
          </li>
        </ul>
      </div>
    </section>
  </main>
</template>

<script>
import axios from 'axios';
import BookOverview from '@/components/BookOverview.vue';

export default {
  name: 'Books',
  data() {
    return {
      tag: null,
      rating: null,
      author: null,
      releaseYearFrom: null,
      releaseYearUntil: null,
      search: '',
      tags: [],
      authors: [],
      books: [],
      searching: false,
    };
  },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'books' });
    this.tag = this.isUndefined(this.$route.query.tag, true) ? '' : parseInt(this.$route.query.tag, 10);
    this.rating = this.isUndefined(this.$route.query.rating, true) ? '' : parseInt(this.$route.query.rating, 10);
    this.author = this.isUndefined(this.$route.query.author, true) ? '' : parseInt(this.$route.query.author, 10);
    this.releaseYearFrom = this.isUndefined(this.$route.query.releaseYearFrom, true) ? '' : parseInt(this.$route.query.releaseYearFrom, 10);
    this.releaseYearUntil = this.isUndefined(this.$route.query.releaseYearUntil, true) ? '' : parseInt(this.$route.query.releaseYearUntil, 10);
    this.search = this.isUndefined(this.$route.query.q, false) ? '' : this.$route.query.q;

    /* if (this.tag || this.rating || this.author || this.releaseYearFrom || this.releaseYearUntil || this.search) {
    } */
    this.getBooksFromApi(null);

    axios.get('api/tags')
      .then((response) => {
        // console.log('tags response', response);
        this.tags = response.data;
      });
    axios.get('api/authors')
      .then((response) => {
        // console.log('authors response', response);
        this.authors = response.data;
      });
  },
  mounted() {
    // console.log('mounted Books');
    document.getElementById('title').focus();
  },
  methods: {
    isUndefined(variable, andIsNumber) {
      return variable === undefined || variable === null || (andIsNumber ? Number.isNaN(parseInt(variable, 10)) : false);
    },
    getBooksFromApi(ev) {
      if (ev !== null) {
        ev.preventDefault();
      }
      this.searching = true;
      axios.get('api/books/search', {
        params: {
          tag: this.tag,
          rating: this.rating,
          author: this.author,
          releaseYearFrom: this.releaseYearFrom,
          releaseYearUntil: this.releaseYearUntil,
          q: this.search,
        },
      }).then((response) => {
        // console.log('books response', response);
        const url = new URL(window.location);
        url.searchParams.set('tag', this.tag);
        url.searchParams.set('rating', this.rating);
        url.searchParams.set('author', this.author);
        url.searchParams.set('releaseYearFrom', this.releaseYearFrom);
        url.searchParams.set('releaseYearUntil', this.releaseYearUntil);
        url.searchParams.set('q', this.search);
        window.history.replaceState({}, '', url.href);
        this.books = response.data;
        this.books = this.books.map((obj) => {
          const releaseDate = new Date(obj.releaseDate);
          releaseDate.setTime(releaseDate.getTime() + releaseDate.getTimezoneOffset() * 60 * 1000);
          return {
            ...obj,
            releaseDate,
          };
        });
        // Don't change the focus on page load
        if (ev !== null) {
          this.$refs.searchResults.focus();
        }
        this.searching = false;
      });
    },
  },
  components: { BookOverview },
};
</script>

<style scoped>

main > form {
  margin: 1rem 6% 0 6%;
  padding: 10px 30px 17px 30px;
  background-color: #f6f6f6;
  border: 1px solid #e8e8e8;
}

main > section {
  margin: 1.5rem 6% 0 6%;
}

#searchResults {
  display: flex;
  flex-direction: column;
  margin: 1em 0 1em 0;
}

#searchResults > .article-wrapper {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
}

.article-wrapper > li > article {
  height: 100%;
}

.article-wrapper > li {
  margin: 15px 0 15px 0;
  width: 100%;
}

a.visually-hidden {
  position: static;
}

.form-combobox-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: end;
}

.form-combobox-wrapper select {
  width: 100%;
  padding: 0.5em;
  margin-top: auto;
  height: 100%;
}

.release-year-wrapper {
  display: flex;

}

#searchWrapper {
  display: grid;
  grid-template-columns: 1fr;
  grid-gap: 1em;
  align-items: end;
}

#searchWrapper > div:first-child {
  flex: 1 1 0%;
}

#searchWrapper > div:first-child input {
  display: block;
  font-size: 0.9em;
  width: 100%;
  padding: 0 0.7em 0 0.7em;
  height: 40px;
}

.input::placeholder {
  color: initial !important;
}

.select select:not([multiple]) {
  padding-right: 2em !important;
}

.select select {
  font-size: 0.9em;
}

.select:not(.is-multiple):not(.is-loading)::after {
  right: 1em !important;
}

#searchWrapper button[type="submit"] {
  height: 40px;
  padding: 0 0.7em 0 0.7em;
  background-color: #f3c15e;
  border: 1px #b48a37 solid;
  color: black;
  font-weight: 600;
}

.form-content {
  display: grid;
  grid-template-columns: 1fr;
  -ms-grid-template-columns: 1fr;
}

@media all and (min-width: 500px) {
  main > form {
    margin: 1.5rem auto 0 auto;
    max-width: 1100px;
  }

  #searchResults > .article-wrapper {
    flex-direction: row;
  }

}

@media all and (min-width: 769px) {
  main > section {
    margin: 3rem 10% 0 10%;
  }

  #searchWrapper {
    grid-template-columns: 1fr auto;
  }

  .form-content {
    grid-gap: 1em;
    grid-template-columns: 1fr 1fr;
    -ms-grid-gap: 1em;
    -ms-grid-template-columns: 1fr 1fr;
  }

  .form-content #searchWrapper {
    grid-column: 1 / 3;
  }

  #searchResults > .article-wrapper > li {
    width: calc(50% - 30px);
    margin: 15px;
  }
}

@media all and (min-width: 1024px) {
  .form-content {
    grid-gap: 1em;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    -ms-grid-gap: 1em;
    -ms-grid-template-columns: 1fr 1fr 1fr 1fr;
  }

  .form-content #searchWrapper {
    grid-column: 3 / 5;
    grid-row: 1 / 3;
  }

  #searchResults > .article-wrapper > li {
    width: calc(33% - 30px);
    margin: 15px;
  }
}

@media all and (min-width: 1408px) {
  #searchResults > .article-wrapper > li {
    width: calc(25% - 30px);
    margin: 15px;
  }
}
</style>
