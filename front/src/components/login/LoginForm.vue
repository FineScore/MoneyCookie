<template>
  <div class="flex justify-center self-center z-10">
    <div class="p-12 bg-white mx-auto rounded-3xl w-96">
      <form
        action="/login"
        method="post"
        class="flex justify-center items-center flex-col space-y-6"
      >
        <input
          type="text"
          name="username"
          id="username"
          placeholder="아이디"
          autocomplete="off"
          class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
          v-model="username"
        />
        <strong class="text-sm text-red-600 font-semibold" v-if="isNonUsername"
          >아이디를 입력해주세요.</strong
        >
        <input
          type="password"
          name="password"
          id="password"
          placeholder="비밀번호"
          class="text-sm px-4 py-3 rounded-lg w-full bg-gray-200 focus:bg-gray-100 border border-gray-200 focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
          v-model="password"
        />
        <strong class="text-sm text-red-600 font-semibold" v-if="isNonPassword"
          >비밀번호를 입력해주세요.</strong
        >
        <button
          type="button"
          id="login"
          class="w-full flex justify-center bg-yellow-600 hover:bg-yellow-500 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200"
          @click="checkLoginForm"
        >
          로그인
        </button>
      </form>
      <div class="h-px my-6 bg-gray-200"></div>
      <router-link to="/register">
        <button
          type="button"
          class="w-full flex justify-center bg-yellow-600 hover:bg-yellow-500 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200"
        >
          회원 가입
        </button>
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      username: "",
      password: "",
      isNonUsername: false,
      isNonPassword: false,
    };
  },
  methods: {
    checkLoginForm() {
      const url = "/login";
      const data = {
        username: this.username,
        password: this.password,
      };
      if (this.username === "") {
        this.isNonUsername = true;
      } else {
        this.isNonUsername = false;
      }
      if (this.password === "") {
        this.isNonPassword = true;
      } else {
        this.isNonPassword = false;
      }

      if (!this.isNonUsername && !this.isNonPassword) {
        axios
          .post(url, data)
          .then((response) => {
            console.log(response);
            this.$router.push("/section");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>
