<template>
  <main tabindex="-1" id="main">
    <section>
      <h2>Derzeit populär</h2>
      <book-and-reviews v-if="popularBook !== null" v-bind:book="popularBook"></book-and-reviews>
    </section>
    <section >
      <h2>Derzeit kontrovers</h2>
      <book-and-reviews v-if="controversialBook !== null" v-bind:book="controversialBook"></book-and-reviews>
    </section>
  </main>
</template>

<script>
import axios from 'axios';
import BookAndReviews from '@/components/BookAndReviews.vue';

export default {
  name: 'Reviews',
  data() {
    return {
      searching: true,
      popularBook: null,
      controversialBook: null,
    };
  },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'reviews' });
    let requestsPending = 2;

    axios.get('api/reviews/popular')
      .then((response) => {
        // console.log('popular book', response.data);
        this.popularBook = response.data;
        this.popularBook.reviews.forEach((r) => {
          r.date = new Date(r.date);
          return r;
        });
      })
      .finally(() => {
        requestsPending -= 1;
        if (requestsPending === 0) {
          this.searching = false;
        }
      });

    axios.get('api/reviews/controversial')
      .then((response) => {
        // console.log('controversial book', response.data);
        this.controversialBook = response.data;
        this.controversialBook.reviews.forEach((r) => {
          r.date = new Date(r.date);
          return r;
        });
      })
      .finally(() => {
        requestsPending -= 1;
        if (requestsPending === 0) {
          this.searching = false;
        }
      });
  },
  components: { BookAndReviews },
};
</script>

<style scoped>
h2 {
  font-size: 1.8rem;
  margin-bottom: 10px;
}

main {
  display: block;
  margin: 1.5rem 4% 0 4%;
  padding: 10px;
}

main > section {
  margin-bottom: 30px;
}

@media all and (min-width: 769px) {
  main {
    margin: 1.5rem auto 0 auto;
    max-width: 1100px;
  }
}

@media all and (min-width: 1150px) {
  main {
    padding: 0;
  }
}
</style>
