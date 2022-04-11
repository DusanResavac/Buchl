<template>
  <article class="book-overview">
    <router-link v-bind:to="`/book/${book.id}`">
      <div class="article-link-wrapper">
        <book-image v-bind:title="book.title" v-bind:image-link="book.imageLink" v-bind:image-alt="book.imageAlt"></book-image>
        <div class="article-content"><h3>{{ book.title }}</h3>
          <div class="article-footer">
            <p>{{ book.releaseDate.getFullYear() }}</p>
            <img loading="lazy"
                 v-if="book.averageRating !== null"
                 v-bind:src="require(`../assets/imgs/SVG/rating-${Math.round(book.averageRating)}.svg`)"
                 v-bind:alt="`${Math.round(book.averageRating)} von 5 Sterne`">
            <p v-if="book.averageRating === null">Noch nicht erschienen</p>
            <p>
              {{ book.author.lastName === null || book.author.lastName === '' ? book.author.firstName : book.author.lastName }}
            </p>
          </div>
        </div>
      </div>
    </router-link>
  </article>
</template>

<script>
import BookImage from '@/components/BookImage.vue';

export default {
  name: 'BookOverview',
  props: ['book'],
  components: { BookImage },
};
</script>

<style scoped>
.book-overview > a {
  height: 100%;
}

.book-overview > a:focus {
  border: none;
  outline: none;
}

.book-overview > a:focus > .article-link-wrapper {
  border: 2px dashed #485fc7;
  outline: none;
}

.book-overview .article-link-wrapper {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px 25px 15px 25px;
  background-color: #f2f2f2;
}

.article-link-wrapper h3 {
  text-align: center;
  font-size: 1.05em;
  margin-bottom: 0.5em;
}

.article-link-wrapper > img {
  height: 300px;
  object-fit: contain;
}

.article-content {
  display: flex;
  margin-top: 15px;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  flex: 1 1 0;
}

.article-content > .article-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.article-content > .article-footer > img {
  width: 50%;
  margin-left: 5px;
  margin-right: 5px;
}

.article-content > .article-footer > p {
  text-align: center;
}
</style>
