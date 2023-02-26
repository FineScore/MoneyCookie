<template>
  <button
    type="button"
    class="w-32 flex justify-center border-yellow-600 active:bg-yellow-600 border-2 text-black text-opacity-70 p-3 rounded-full tracking-wide font-semibold cursor-pointer"
    @click="logout"
  >
    로그아웃
  </button>
</template>

<script>
import axios from "axios";

export default {
  methods: {
    logout() {
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
