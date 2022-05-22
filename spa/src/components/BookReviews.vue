<template>
  <fragment>
    <router-link v-bind:to="`/book/${book.id}`">Zur Buchseite wechseln</router-link>
    <div role="region" aria-label="Rezensionen und Bewertungstexte">
      <p v-if="book.reviews.length <= 0">Es sind keine Bewertungstexte vorhanden</p>
      <ul v-if="book.reviews.length > 0">
        <li v-for="review in book.reviews"
            v-bind:key="review.id"
            class="review-li">
          <article>
            <div class="review-user-and-date">
              <div>
                <img alt="" loading="lazy"
                     v-bind:src="review.user.image === null ? require('../assets/imgs/user.png') : review.user.image">
                <span>{{ review.user.nickname }}</span>
              </div>
              <p>
                am {{ review.date.toLocaleDateString('de-DE', { year: 'numeric', month: 'long', day: 'numeric' }) }} veröffentlicht
              </p>
            </div>
            <div class="review-rating-and-text">
              <img v-bind:alt="`${review.rating} von 5 Sterne`" loading="lazy"
                   v-bind:src="require(`../assets/imgs/SVG/rating-${review.rating}.svg`)">
              <div>
                <h2 class="mb-2 is-size-5">{{ review.title }}</h2>
                <p v-html="review.text"></p>
              </div>
            </div>
          </article>
        </li>
      </ul>
    </div>
  </fragment>
</template>

<script>

export default {
  name: 'BookReviews',
  created() {
    // console.log('Book Reviews', this.book);
    this.book.reviews.forEach((r) => {
      r.date = new Date(r.date);
      return r;
    });
  },
  mounted() {
    const title = `Rezensionen zu ${this.book.title} - Buchl`;
    document.title = title;
    this.$announcer.set(`${title} wurde geladen`, 'polite');
    // document.getElementById('skipNavigation').focus();
  },
  props: ['book'],
};
</script>

<style scoped>
.review-li {
  margin-top: 20px;
}

.review-li > article {
  display: flex;
  flex-direction: column;
}

article > .review-user-and-date {
  display: flex;
  flex-direction: column;
}

article > .review-user-and-date > div {
  display: flex;
  align-items: center;
}

.review-user-and-date > div > img {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  margin-right: 15px;
}

.review-rating-and-text {
  display: flex;
  flex-direction: column;
}

.review-rating-and-text > img {
  width: 100%;
  max-width: 100px;
}

@media all and (min-width: 769px) {
  .review-user-and-date > div > span{
    margin-right: 15px;
  }

  article > .review-user-and-date {
    align-items: center;
    flex-direction: row;
  }

  .review-rating-and-text {
    align-items: start;
    max-width: 600px;
    flex-direction: row;
  }

  .review-rating-and-text > img {
    margin-right: 15px;
    width: 100px;
  }
}
</style>
