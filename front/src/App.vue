<template>
  <RouterView />
  <LoadingSpinner :loading="this.$store.state.isLoading" />
</template>

<script>
import LoadingSpinner from "./components/UI/LoadingSpinner.vue";

export default {
  components: {
    LoadingSpinner,
  },
};
</script>
