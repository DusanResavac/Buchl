<template>
  <div class="book">
    <article>
      <book-image v-bind:title="book.title" v-bind:image-link="book.imageLink" v-bind:image-alt="book.imageAlt"></book-image>
      <div class="book-title-and-author">
        <h3>
          <router-link v-bind:to="`/book/${book.id}`">{{ book.title }}</router-link>
        </h3>
        <div class="author-info">
          <author-image v-bind:image-link="book.author.imageLink" v-bind:image-alt="book.author.imageAlt"></author-image>
          <p>
            <router-link v-bind:to="`/books?author=${book.author.id}`">{{ book.author.firstName }} {{ book.author.lastName }}</router-link>
          </p>
        </div>
        <div class="book-average-rating">
                        <span v-if="book.averageRating != null"
                              v-bind:aria-label="`Die durchschnittliche Bewertung ist ${book.averageRating} von 5`">
                          Ø {{ book.averageRating }}
                        </span>
          <img v-if="book.averageRating != null"
               v-bind:src="require(`../assets/imgs/SVG/rating-${Math.round(book.averageRating)}.svg`)"
               v-bind:alt="`${Math.round(book.averageRating)} von 5 Sterne`">
          <span v-if="book.averageRating == null">Keine Bewertungen</span>
        </div>
      </div>
    </article>
    <div class="reviews-and-button">
      <ul class="reviews">
        <li v-for="review in book.reviews" v-bind:key="review.id" class="review">
          <article>
            <div class="review-user-and-date">
              <div>
                <img alt="" loading="lazy"
                     v-bind:src="review.user.image === null ? require('../assets/imgs/user.png') : review.user.image">
                <span>{{ review.user.nickname }}</span>
              </div>
              <p>am {{ review.date.toLocaleDateString('de-DE', { year: 'numeric', month: 'long', day: 'numeric' }) }} veröffentlicht</p>
            </div>
            <div class="review-rating-and-text">
              <img  loading="lazy"
                    v-bind:alt="`${review.rating} von 5 Sterne`"
                    v-bind:src="require(`../assets/imgs/SVG/rating-${review.rating}.svg`)">
              <div>
                <h4 class="mb-2">{{ review.title }}</h4>
              </div>
            </div>
          </article>
        </li>
      </ul>
      <router-link v-bind:to="`/book/${book.id}/reviews`" class="button is-normal is-responsive">Mehr Rezensionen zum Buch</router-link>
    </div>
  </div>
</template>

<script>
import BookImage from '@/components/BookImage.vue';
import AuthorImage from '@/components/AuthorImage.vue';

export default {
  name: 'BookAndReviews',
  props: ['book'],
  components: { BookImage, AuthorImage },
};
</script>

<style scoped>

h3 {
  font-size: 1.2rem;
  margin-bottom: 10px;
}

h4 {
  font-size: 1.15rem;
}

.book {
  display: flex;
  flex-direction: column;
}

.book > article {
  flex: 1 1 0%;
  display: flex;
  margin-bottom: 20px;
}

.book > article > img {
  width: 100px;
  object-fit: contain;
  margin-right: 20px;
}

.book-title-and-author {
  flex: 1 1 0%;
}

.book-title-and-author > .author-info {
  display: flex;
  align-items: center;
}

.author-info > img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 50%;
  margin-right: 12px;
}

.book-average-rating {
  display: flex;
  align-items: center;
}

.book-average-rating > span {
  margin-right: 12px;
  font-size: 1.05rem;
  white-space: nowrap;
}

.book-average-rating > img {
  width: 100%;
  max-width: 200px;
}

.book > .reviews-and-button {
  flex: 1 1 0%;
}

.reviews-and-button > ul > li {
  margin-bottom: 20px;
}

.review-user-and-date {
  display: flex;
  flex-direction: column;
}

.review-user-and-date > div {
  display: flex;
  align-items: center;
}

.review-user-and-date > div > img {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  margin-right: 15px;
}

.review-user-and-date > div > span{
  margin-right: 15px;
}

.review-rating-and-text {
  display: flex;
  flex-direction: column;
  align-items: start;
  max-width: 600px;
}

.review-rating-and-text > img {
  width: 100%;
  max-width: 100px;
}

.review-rating-and-text > img {
  margin-right: 15px;
  width: 100px;
  max-width: 100px;
}

@media all and (min-width: 769px) {
  .book > article > img {
    width: 200px;
  }

  .review-user-and-date {
    align-items: center;
    flex-direction: row;
  }

  .review-rating-and-text {
    flex-direction: row;
  }
}

@media all and (min-width: 1216px) {
  .book {
    flex-direction: row;
    align-items: start;
  }

  .book > article {
    padding-right: 25px;
    margin-right: 25px;
    margin-bottom: 0;
    border-right: 1px solid #dddddd;
  }
}
</style>
