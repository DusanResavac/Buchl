<template>
  <main tabindex="-1" id="main" aria-label="Inhalt">
    <h1>Diskussionen</h1>
    <div class="loader" v-show="searching" id="loadingCircle" aria-label="Lade Ergebnisse"></div>
    <div id="discussions" v-bind:aria-busy="searching">
      <section>
        <h2 class="is-size-4">Neue Diskussionen</h2>
        <ul>
          <li v-for="(newDiscussion, index) in newDiscussions" v-bind:key="newDiscussion.id">
            <hr v-if="index !==  0">
            <article>
              <div class="user-and-content">
                <div class="user-and-heading">
                  <div class="user">
                    <img loading="lazy"
                         v-bind:src="newDiscussion.user.image === null ? require('../assets/imgs/user.png') : newDiscussion.user.image"
                         alt="">
                    <span class="is-size-4-mobile is-size-6">{{ newDiscussion.user.nickname }}</span>
                  </div>
                  <div class="article-heading">
                    <h3 class="is-size-5 mt-3">
                      <router-link v-bind:to="`/discussion/${newDiscussion.id}`">{{ newDiscussion.title }}</router-link>
                    </h3>
                  </div>
                </div>
                <div class="article-end">
                  <div class="book-wrapper">
                    <book-image v-bind:title="newDiscussion.book.title" v-bind:image-link="newDiscussion.book.imageLink" v-bind:image-alt="newDiscussion.book.imageAlt"></book-image>
                    <h4 v-if="newDiscussion.book !== null">
                      <router-link v-bind:to="`/book/${newDiscussion.book.id}`">{{ newDiscussion.book.title }}</router-link>
                    </h4>
                  </div>
                  <router-link v-bind:to="`/discussion/${newDiscussion.id}`">{{ newDiscussion.comments.length === 1 ? '1 Antwort' : newDiscussion.comments.length + ' Antworten'}}</router-link>
                </div>
              </div>
            </article>
          </li>
        </ul>
      </section>

      <article v-for="(bookWithDiscussions, index) in booksWithDiscussions" v-bind:key="bookWithDiscussions.id">
        <h2 v-if="index === 0" class="is-size-4 mb-5">Diskussionen zu deinen Favoriten</h2>
        <div class="div-book">
          <book-image v-bind:title="bookWithDiscussions.title" v-bind:image-link="bookWithDiscussions.imageLink" v-bind:image-alt="bookWithDiscussions.imageAlt"></book-image>
          <div class="book-info-div">
            <h3 class="is-size-5">
              <router-link v-bind:to="`/book/${bookWithDiscussions.id}`">{{ bookWithDiscussions.title }}</router-link>
            </h3>
            <router-link v-bind:to="`/books?author=${bookWithDiscussions.author.id}`">
              <div class="author-div">
                <author-image v-bind:image-link="bookWithDiscussions.author.imageLink" v-bind:image-alt="bookWithDiscussions.author.imageAlt"></author-image>
                <p>{{ bookWithDiscussions.author.firstName }} {{ bookWithDiscussions.author.lastName }}</p>
              </div>
            </router-link>
          </div>
        </div>

        <div class="book-discussions">
          <ul>
            <li v-for="discussion in bookWithDiscussions.discussions" v-bind:key="discussion.id">
              <article class="book-discussion">
                <div class="book-discussion-user">
                  <img loading="lazy" v-bind:src="discussion.user.image == null ? require('../assets/imgs/user.png') : discussion.user.image" alt="">
                  <span>{{ discussion.user.nickname }}</span>
                </div>
                <div class="book-discussion-content">
                  <h4>
                    <router-link v-bind:to="`/discussion/${discussion.id}`">{{ discussion.title }}</router-link>
                  </h4>
                  <router-link v-bind:to="`/discussion/${discussion.id}`">{{ discussion.comments.length + (discussion.comments.length === 1 ? ' Antwort' : ' Antworten') }}</router-link>
                </div>
              </article>
            </li>
          </ul>
        </div>
      </article>
    </div>
  </main>
</template>

<script>
import BookImage from '@/components/BookImage.vue';
import axios from 'axios';
import AuthorImage from '@/components/AuthorImage.vue';

export default {
  name: 'Discussions',
  components: { AuthorImage, BookImage },
  data() {
    return {
      searching: true,
      newDiscussions: [],
      booksWithDiscussions: [],
    };
  },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'discussions' });
    let requestsPending = 2;

    axios.get('api/discussions/recent')
      .then((response) => {
        // console.log('Recent discussions', response.data);
        this.newDiscussions = response.data;
      })
      .finally(() => {
        requestsPending -= 1;
        if (requestsPending === 0) {
          this.searching = false;
        }
      });

    let bookFavourites = JSON.parse(localStorage.getItem('bookFavourites'));
    // console.log(bookFavourites);
    if (bookFavourites === null) {
      localStorage.setItem('bookFavourites', '[]');
      bookFavourites = [];
    }

    axios.post('api/books/with-discussions', bookFavourites, {
      'content-type': 'text/json',
    })
      .then((response) => {
        // console.log('Books with discussions', response.data);
        this.booksWithDiscussions = response.data;
      })
      .finally(() => {
        requestsPending -= 1;
        if (requestsPending === 0) {
          this.searching = false;
        }
      });
  },
  mounted() {
    // console.log('mounted Discussions');
    document.getElementById('title').focus();
  },
};
</script>

<style scoped>
hr {
  background-color: #ccc;
}

h1 {
  font-size: 1.7rem;
  margin-bottom: 15px;
}

main a {
  color: #142987 !important;
}

main {
  max-width: 1100px;
  margin: 20px auto 20px auto;
  padding: 10px;
}

#discussions {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
}

.loader {
  margin: 40px auto 40px auto;
  border: 5px solid #dbdbdb;
  border-right-color: transparent;
  border-top-color: transparent;
  border-radius: 100%;
  width: 80px;
  height: 80px;
  -webkit-animation: spin 0.7s linear infinite; /* Safari */
  animation: spin 0.7s linear infinite;
}

/* Safari */
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

#discussions > section {
  width: 100%;
  padding: 20px;
  background-color: #f5f5f5;
}

#discussions > article {
  margin-top: 40px;

}

.div-book {
  display: flex;
  flex-direction: column;
}

.div-book > img {
  height: 100%;
  max-height: 250px;
  object-fit: contain;
}

.div-book > .book-info-div {
  display: flex;
  flex: 1 1 0%;
  flex-direction: column;
  padding: 20px 0 0 0;
}

.div-book > .book-info-div > h3 {
  margin-bottom: 10px;
}

.div-book > .book-info-div > a {
  margin-top: 10px;
  margin-bottom: 10px;
}

.author-div {
  display: flex;
  align-items: center;
}

.author-div > img {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 50%;
}

.author-div > p {
  margin: 8px;
  flex: 1 1 0%;
}

.book-discussions {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  padding: 10px 0 10px 0;
  border-left: 10px solid #c6c6c6;
}

.book-discussion {
  padding: 10px 0 10px 30px;
  background-color: #dee7ed;
}

#discussions > section article {
  margin: 10px 0 10px 0;
}

.book-discussion > a:focus {
  border: none;
  outline: none;
}

.book-discussion > a:focus > div {
  border: 2px dashed #142987;
}

.user-and-content {
  display: flex;
}

.user-and-heading {
  flex: 1 1 0%;
  display: flex;
  flex-direction: column;
}

.book-discussion-user, .user {
  display: flex;
  align-items: center;
}

.book-discussion-user > img, .user > img {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 50%;
}

.book-discussion-user > span, .user > span {
  margin: 5px;
  color: #323232;
}

.article-heading {
  flex: 1 1 0%;
}

.book-wrapper {
  width: 100px;
  text-align: center;
}

.book-wrapper h4 {
  white-space: break-spaces;
}

.book-wrapper > img {
  width: 100px;
  object-fit: contain;
}

@media all and (min-width: 769px) {
  #discussions {
    flex-direction: row;
  }

  #discussions > section {
    margin-right: 20px;
    width: calc(50% - 20px);
  }

  #discussions > article:nth-child(even) {
    margin-left: 20px;
    width: calc(50% - 20px);
  }

  #discussions > article:nth-child(odd) {
    margin-right: 20px;
    width: calc(50% - 20px);
  }

  #discussions > section {
    margin-bottom: 40px;
  }

  #discussions > article {
    margin-top: 0;
    margin-bottom: 40px;
    padding: 20px;
    width: 50%;
  }

  .book-wrapper > img, .book-wrapper {
    width: 80px;
  }

  .div-book {
    flex-direction: row;
  }

  .div-book > .book-info-div {
    padding: 0 0 0 20px;
  }

  .div-book > img {
    width: 160px;
  }

  .book-discussion {
    display: flex;
    padding: 10px;
  }

  .book-discussions {
    margin-top: 0;
    padding: 10px 0 10px 0;
    border-left: 3px solid #c6c6c6;
  }

  .book-discussion-user {
    flex-direction: column;
    margin-right: 10px;
    width: 120px;
  }

  .book-discussion-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .book-discussion-content > a {
    align-self: end;
  }
}

@media all and (min-width: 1150px) {
  main {
    padding: 0;
  }
}
</style>
