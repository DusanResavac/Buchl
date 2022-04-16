<template>
  <img loading="lazy"
       v-bind:src="require(`../assets/imgs/${imageLink == null ? 'image-preview.png' : imageLink}`)"
       v-bind:alt="imageAlt == null ? '' : imageAlt">
</template>

<script>
export default {
  name: 'BookImage',
  props: ['imageLink', 'imageAlt', 'title'],
};
</script>

<style scoped>

</style>
