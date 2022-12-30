<template>
  <TheHeader />
  <RouterView />
</template>

<script>
import TheHeader from "../layout/TheHeader.vue";

export default {
  components: {
    TheHeader,
  },
};
</script>
