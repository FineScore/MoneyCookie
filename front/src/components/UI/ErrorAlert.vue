<template>
  <div class="in-top w-full fixed top-0 z-50" v-if="isError" @click="close">
    <div
      class="cursor-pointer flex items-center justify-center w-full p-2 bg-red-500 shadow text-white"
    >
      <img src="@/assets/images/warning.png" class="w-6 h-6 mr-2" />
      {{ this.$store.getters.getErrorMessage.message }}
    </div>
  </div>
</template>

<script>
export default {
  props: ["isError"],
  methods: {
    close() {
      this.$store.commit("setIsError", false);
    },
  },
};
</script>

<style scoped>
.in-top {
  animation: slide-in-top 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
}

@keyframes slide-in-top {
  0% {
    -webkit-transform: translateY(-1000px);
    transform: translateY(-1000px);
    opacity: 0;
  }

  100% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
