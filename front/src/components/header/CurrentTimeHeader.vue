<template>
  <div class="w-7 h-7">
    <img src="@/assets/images/clock.png" alt="현재시간" />
  </div>
  <p class="text-lg font-semibold text-black ml-3">
    {{ currentTime }}
  </p>
</template>

<script>
import moment from "moment";

export default {
  computed: {
    currentTime() {
      return this.$store.state.currentTime;
    },
  },
  mounted() {
    setInterval(() => {
      this.$store.commit("setCurrentTime", moment().format("HH:mm:ss"));
    }, 1000);
  },
  unmounted() {
    clearInterval(this.time);
  },
};
</script>
