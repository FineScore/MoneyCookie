<template>
  <div
    :class="status ? 'bg-green-600' : 'bg-red-600'"
    class="w-7 h-7 border-2 border-gray-800 rounded-full"
  ></div>
  <p class="text-lg font-semibold text-black ml-3 hidden xl:block">
    {{ statusString }}
  </p>
</template>

<script>
import moment from "moment";
import axios from "axios";

export default {
  computed: {
    status() {
      if (
        this.isOpenTimeNow(this.currentTime) &&
        !this.isTodayIsWeekend(this.todayDate) &&
        !this.isTodayIsHoliday()
      ) {
        this.$store.commit("setStatus", true);
        return true;
      } else {
        this.$store.commit("setStatus", false);
        return false;
      }
    },
    statusString() {
      return this.status ? "장 시작" : "장 종료";
    },
    currentTime() {
      return this.$store.state.currentTime;
    },
    todayDate() {
      return this.$store.state.todayDate;
    },
  },
  methods: {
    isOpenTimeNow(currentTime) {
      const currentHour = moment(currentTime, "HH:mm:ss").hour();
      const currentMinute = moment(currentTime, "HH:mm:ss").minute();

      return (
        currentHour >= 9 &&
        (currentHour < 15 || (currentHour === 15 && currentMinute <= 30))
      );
    },
    isTodayIsWeekend(todayDate) {
      const todayDayOfWeek = moment(todayDate, "YYYY년 M월 D일").format("d");
      return todayDayOfWeek == 0 || todayDayOfWeek == 6;
    },
    isTodayIsHoliday(currentTime) {
      if (moment(currentTime, "HH:mm:ss") === "00:00:00") {
        const url = "/api/holiday";
        axios
          .get(url)
          .then((response) => {
            const status = response.status;

            if (status === 204) {
              return true;
            } else {
              return false;
            }
          })
          .catch((error) => {
            console.log(error.response.data);
            this.$store.commit("setError", error.response.data);
          });
      }
    },
  },
};
</script>
