<template>
  <div class="w-7 h-7">
    <img src="@/assets/images/calendar.png" alt="오늘날짜" />
  </div>
  <p class="text-lg font-semibold text-black ml-3">
    {{ todayDate }}
  </p>
</template>

<script>
import moment from "moment";

export default {
  computed: {
    todayDate() {
      const currentTime = this.currentTime;
      const currentHour = moment(currentTime, "HH:mm:ss").hour();
      const currentMinute = moment(currentTime, "HH:mm:ss").minute();
      const currentSecond = moment(currentTime, "HH:mm:ss").second();

      if (currentHour === 0 && currentMinute === 0 && currentSecond === 0) {
        this.$store.commit("setTodayDate", moment().locale("ko").format("LL"));
      }

      return this.$store.state.todayDate;
    },
    currentTime() {
      return this.$store.state.currentTime;
    },
  },
  mounted() {
    this.$store.commit("setTodayDate", moment().locale("ko").format("LL"));
  },
};
</script>
