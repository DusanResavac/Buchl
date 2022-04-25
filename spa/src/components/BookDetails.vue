<template>
  <fragment>
    <h2 class="is-size-4">Beschreibung</h2>
    <p id="bookDescription" v-html="book.description"></p>
    <div class="discussions-wrapper">
      <h2 class="is-size-4">Diskussionen zum Buch</h2>
      <div class="discussions">
        <p v-if="book.discussions.length <= 0">Keine Diskussionen vorhanden</p>
        <article v-for="discussion in book.discussions" v-bind:key="discussion.id">
            <div class="user-and-content">
              <div class="user">
                <img loading="lazy"
                     v-bind:src="discussion.user.image == null ? require('../assets/imgs/user.png') : discussion.user.image"
                     alt="">
                <span class="is-size-4-mobile is-size-6">{{ discussion.user.nickname }}</span>
              </div>
              <div class="article-content">
                <h3 class="is-size-5-mobile is-size-6-tablet is-size-4" >
                  <router-link v-bind:to="`/discussion/${discussion.id}`">{{ discussion.title }}</router-link>
                </h3>
                <router-link class="commentQuantity" v-bind:to="`/discussion/${discussion.id}`">{{ discussion.comments.length + (discussion.comments.length === 1 ? ' Antwort' : ' Antworten') }}</router-link>
              </div>
            </div>
        </article>
      </div>
    </div>
  </fragment>
</template>

<script>
import { Fragment } from 'vue-fragment';

export default {
  name: 'BookDetails',
  components: { Fragment },
  props: ['book'],
  mounted() {
    const title = `${this.book.title} - Buchl`;
    document.title = title;
    this.$announcer.set(`${title} wurde geladen`, 'polite');
    // document.getElementById('skipNavigation').focus();
  },
};
</script>

<style scoped>
a:focus {
  outline: none;
  border: 2px dashed #485fc7 !important;
}

#bookDescription {
  margin-top: 10px;
}

.discussions-wrapper {
  margin-top: 15px;
}

.discussions-wrapper > h2 {
  margin-bottom: 10px;
}

.discussions-wrapper .user {
  display: flex;
  align-items: center;
}

.discussions-wrapper .user > img {
  border-radius: 50%;
}

.discussions-wrapper .user > span {
  margin: 5px;
}

@media all and (min-width: 769px) {
  .user-and-content {
    display: flex;
    padding: 10px;
    background-color: #fafafa;
  }

  .user-and-content > .user {
    flex-direction: column;
    margin-right: 20px;
    width: 110px;
  }

  .user-and-content > .article-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .user-and-content > .article-content > .commentQuantity {
    align-self: end;
  }

  .discussions > p {
    max-width: 600px;
    font-size: 1.1rem;
  }
}
</style>
