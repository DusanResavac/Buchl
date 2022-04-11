<template>
  <img loading="lazy"
       v-bind:src="require(`../assets/imgs/${imageLink === null ? 'user.png' : imageLink}`)"
       v-bind:alt="imageAlt === null ? '' : imageAlt">
</template>

<script>
export default {
  name: 'AuthorImage',
  props: ['imageLink', 'imageAlt'],
};
</script>

<style scoped>

</style>
