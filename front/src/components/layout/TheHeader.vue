<template>
  <header
    class="sticky h-20 top-0 z-40 w-full flex items-center border-b bg-yellow-400 px-14"
  >
    <nav class="max-w-8xl mx-auto w-full flex items-center justify-between">
      <div>
        <img src="@/assets/images/logo.png" alt="MoneyCookie" class="w-60" />
      </div>
      <div class="ml-32">
        <ul class="flex items-center space-x-8">
          <li class="items-center hidden xl:flex">
            <div class="w-7 h-7">
              <img src="@/assets/images/calendar.png" alt="오늘날짜" />
            </div>
            <p class="text-lg font-semibold text-black ml-3">
              {{ date }}
            </p>
          </li>
          <li class="flex items-center">
            <div
              :class="status ? 'bg-green-600' : 'bg-red-600'"
              class="w-7 h-7 border-2 border-gray-800 rounded-full"
            ></div>
            <p class="text-lg font-semibold text-black ml-3 hidden xl:block">
              {{ statusText }}
            </p>
          </li>
          <li class="flex items-center">
            <div class="w-7 h-7">
              <img src="@/assets/images/clock.png" alt="현재시간" />
            </div>
            <p class="text-lg font-semibold text-black ml-3">
              {{ time }}
            </p>
          </li>
          <li>
            <button
              type="button"
              class="w-32 flex justify-center border-yellow-600 active:bg-yellow-600 border-2 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer"
              @click="execLogout"
            >
              로그아웃
            </button>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script>
import moment from "moment";
import axios from "axios";

export default {
  data() {
    return {
      time: "",
      date: moment().locale("ko").format("LL"),
      status: this.$store.getters.getStatus,
    };
  },
  mounted() {
    this.updateTime();
    const url = "/api/closed";
    axios
      .get(url)
      .then((response) => {
        console.log(response.data);
        const day = response.data;
        if (typeof day !== "string") {
          this.dayName = day.name;
          this.isHoliday = true;
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
  computed: {
    statusText() {
      if (this.status) {
        return "장 시작";
      } else {
        return "장 종료";
      }
    },
  },
  watch: {
    time(newTime) {
      if (this.isOpenTime(newTime) && !this.isWeekend(this.date)) {
        this.$store.commit("setStatus", true);
      } else {
        this.$store.commit("setStatus", false);
      }
    },
  },
  methods: {
    updateTime() {
      this.time = moment().format("HH:mm:ss");
      this.$options = window.setTimeout(this.updateTime, 1000);

      if (this.isNextDay) {
        this.date = moment().locale("ko").format("LL");
      }
    },
    isNextDay() {
      return this.time === moment().startOf("day").format("HH:mm:ss");
    },
    isOpenTime(time) {
      const nowHour = moment(time, "HH:mm:ss").hour();
      const nowMinute = moment(time, "HH:mm:ss").minute();
      return (
        (nowHour >= 9 && nowHour <= 15) || (nowHour === 15 && nowMinute <= 30)
      );
    },
    isWeekend(date) {
      return moment(date).day() === 0 || moment(date).day() === 6;
    },
    execLogout() {
      const url = "/api/logout";
      axios
        .post(url)
        .then((response) => {
          console.log(response.data);
          this.$router.push("/");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
