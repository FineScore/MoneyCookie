<template>
  <form class="w-1/6 mx-auto mt-32 space-y-6">
    <div class="space-y-2">
      <label for="password">새로운 비밀번호</label>
      <input
        type="password"
        name="password"
        id="password"
        autocomplete="off"
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="password"
      />
      <strong class="text-sm text-red-600 font-semibold" v-if="!isPassword"
        >비밀번호를 입력해주세요.</strong
      >
    </div>
    <div class="space-y-2">
      <label for="password-confirm">비밀번호 확인</label>
      <input
        type="password"
        name="password-confirm"
        id="password-confirm"
        autocomplete="off"
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="passwordConfirm"
      />
    </div>
    <div class="h-px bg-gray-200"></div>
    <div>
      <strong
        class="text-sm text-red-600 font-semibold"
        v-if="!isCorrectPassword"
        >비밀번호가 일치하지 않습니다.</strong
      >
    </div>
    <div>
      <button
        type="button"
        @click="updatePassword"
        class="w-full flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
      >
        비밀번호 변경
      </button>
    </div>
    <div>
      <button
        type="button"
        @click="deleteUser"
        class="w-full flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
      >
        회원 탈퇴
      </button>
    </div>
  </form>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      password: "",
      passwordConfirm: "",
      isPassword: true,
      isCorrectPassword: true,
    };
  },
  methods: {
    updatePassword() {
      const url = "/api/password";
      if (this.password === "") {
        this.isPassword = false;
      } else {
        this.isPassword = true;
      }

      if (this.password !== this.passwordConfirm) {
        this.isCorrectPassword = false;
      } else {
        this.isCorrectPassword = true;
      }

      if (this.isPassword && this.isCorrectPassword) {
        const data = {
          password: this.password,
        };
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
    deleteUser() {
      const url = "/api/user";
      axios
        .delete(url)
        .then((response) => {
          console.log(response);
          this.$router.push("/");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
