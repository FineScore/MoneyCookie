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
        !this.isTodayIsHoliday
      ) {
        return true;
      } else {
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
      return todayDayOfWeek === 0 || todayDayOfWeek === 6;
    },
    isTodayIsHoliday() {
      const url = "/api/holiday";
      axios
        .get(url)
        .then((response) => {
          console.log(response.data);
          const responseData = response.data.data;

          if (responseData === "공휴일 아님") {
            return false;
          } else {
            return true;
          }
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
  },
};
</script>
