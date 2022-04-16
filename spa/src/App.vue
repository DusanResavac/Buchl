<template>
  <fragment>
    <VueAnnouncer />
    <app-header v-bind:main-id="mainId" v-bind:active-link="activeLink"></app-header>
    <router-view @loaded="onPageLoaded"/>
    <footer class="mt-6"><p style="text-align: center;">Copyright 2022 Buchl</p></footer>
  </fragment>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';

export default {
  components: { AppHeader },
  data() {
    return {
      mainId: '',
      activeLink: '',
    };
  },
  methods: {
    onPageLoaded(obj) {
      this.mainId = obj.mainId;
      this.activeLink = obj.activeLink;
    },
  },
};
</script>
