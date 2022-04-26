<template>
  <main tabindex="-1" id="main" aria-label="Inhalt">
    <article v-if="discussion !== null">
      <h1>Diskussion zu
        <router-link v-bind:to="`/book/${discussion.book.id}`">{{ discussion.book.title }}</router-link>
      </h1>
      <div class="discussion">
        <div class="discussion-user">
          <img loading="lazy"
               v-bind:src="discussion.user.image == null ? require('../assets/imgs/user.png') : discussion.user.image"
               alt="">
          <span>{{ discussion.user.nickname }}</span>
        </div>
        <div class="discussion-time-and-text">
          <h2>{{ discussion.title }}</h2>
          <p class="mb-4">{{ discussion.timePassed }} veröffentlicht</p>
          <p class="discussion-text" v-html="discussion.text"></p>
        </div>
      </div>
    </article>
    <hr>
    <p v-if="discussion !== null">
      {{ discussion.comments.length === 1 ? '1 Kommentar' : discussion.comments.length + ' Kommentare' }}
    </p>
    <form class="write-comment" aria-label="Kommentar verfassen">
      <img loading="lazy" v-bind:src="require('../assets/imgs/user.png')" alt="">
      <div class="comment-input-and-button">
        <div class="control">
          <label for="comment">Kommentar verfassen</label>
          <textarea id="comment" placeholder="Einen Kommentar schreiben..." type="text"></textarea>
        </div>
        <button class="button is-info">Veröffentlichen</button>
      </div>
    </form>
    <section v-if="discussion !== null">
      <ul>
        <li v-for="comment in discussion.comments" v-bind:key="comment.id">
          <article class="comment">
            <img loading="lazy"
                 v-bind:src="comment.user.image == null ? require('../assets/imgs/user.png') : comment.user.image"
                 alt="">
            <div>
              <p class="comment-info">
                {{ comment.user.nickname }} - {{ comment.timePassed }} veröffentlicht
              </p>
              <p class="comment-text">{{ comment.text }}</p>
            </div>
          </article>
        </li>
      </ul>
    </section>
  </main>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Discussion',
  data() {
    return {
      discussion: null,
      searching: true,
    };
  },
  created() {
    this.$emit('loaded', { mainId: '#main', activeLink: 'discussion' });
    const discussionId = parseInt(this.$route.params.id, 10);
    if (!Number.isNaN(discussionId)) {
      const url = `api/discussion/${discussionId}`;
      axios.get(url)
        .then((response) => {
          // console.log('Discussion', response.data);
          this.discussion = response.data;
          const title = `Diskussion zu ${this.discussion.book.title}: ${this.discussion.title} - Buchl`;
          document.title = title;
          this.$announcer.set(`${title} wurde geladen`, 'polite');
          document.getElementById('title').focus();
        })
        .finally(() => {
          this.searching = false;
        });
    }
  },
  mounted() {
    // console.log('mounted Discussion');
    document.getElementById('title').focus();
  },
};
</script>

<style scoped>
main {
  display: flex;
  flex-direction: column;
  max-width: 1100px;
  margin: 20px auto 20px auto;
  padding: 10px;
}

h1 {
  font-size: 1.5rem;
  margin-bottom: 15px;
}

h2 {
  font-size: 1.3rem;
}

h3 {
  font-size: 1.1rem;
  margin-bottom: 10px;
}

main > article > .discussion {
  display: flex;
  flex-direction: column;
}

main > p {
  margin-bottom: 20px;
}

.discussion > .discussion-user {
  display: flex;
  align-items: center;
}

.discussion > .discussion-user > img {
  width: 60px;
  height: 60px;
  object-fit: contain;
  border-radius: 50%;
  margin: 0 20px 0 0;
}

.discussion > .discussion-time-and-text > .discussion-text {
  max-width: 600px;
}

.write-comment {
  display: flex;
  margin-bottom: 30px;
}

.write-comment > img {
  width: 40px;
  height: 40px;
  object-fit: contain;
  margin-right: 15px;
  border-radius: 50%;
}

.write-comment > .comment-input-and-button {
  display: flex;
  flex-direction: column;
  align-items: end;
}

.comment-input-and-button > div {
  display: flex;
  flex-direction: column;
}

.comment-input-and-button > div > textarea {
  width: 100%;
  padding: 10px;

}

.comment-input-and-button > button {
  max-width: 170px;
}

li > .comment {
  display: flex;
  margin-bottom: 20px;
}

li > .comment > img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: contain;
  margin: 3px 15px 0 3px;
}

li > .comment > div {
  display: flex;
  flex-direction: column;
}

.comment > div > .comment-info {
  margin-bottom: 5px;
}

.comment > div > .comment-text {
  max-width: 500px;
}

@media all and (min-width: 769px) {
  main > article > .discussion {
    flex-direction: row;
  }

  .discussion > .discussion-user {
    flex-direction: column;
    align-items: center;
    margin-right: 20px;
  }

  .discussion > .discussion-user > img {
    margin: 0 0 10px 0;
  }

  .write-comment > .comment-input-and-button {
    flex-direction: row;
  }

  .comment-input-and-button > button {
    margin-left: 15px;
  }
}

@media all and (min-width: 1150px) {
  main {
    padding: 0;
  }
}
</style>
