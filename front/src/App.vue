<template>
  <RouterView />
  <ErrorAlert :isError="this.$store.getters.getIsError" />
  <LoadingSpinner :loading="this.$store.state.isLoading" />
</template>

<script>
import ErrorAlert from "./components/UI/ErrorAlert.vue";
import LoadingSpinner from "./components/UI/LoadingSpinner.vue";

export default {
  components: {
    LoadingSpinner,
    ErrorAlert,
  },
};
</script>
