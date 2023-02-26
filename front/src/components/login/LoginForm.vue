<template>
  <form class="flex justify-center flex-col space-y-6">
    <div class="space-y-2">
      <input
        type="text"
        name="username"
        id="username"
        placeholder="아이디"
        autocomplete="off"
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="v$.username.$model"
      />
      <p v-for="error of v$.username.$errors" :key="error.$uid">
        <strong class="text-xs text-red-600 font-semibold">{{
          error.$message
        }}</strong>
      </p>
    </div>
    <div class="space-y-2">
      <input
        type="password"
        name="password"
        id="password"
        placeholder="비밀번호"
        class="text-sm px-4 py-3 rounded-lg w-full bg-gray-200 focus:bg-gray-100 border border-gray-200 focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="v$.password.$model"
      />
      <p v-for="error of v$.password.$errors" :key="error.$uid">
        <strong class="text-xs text-red-600 font-semibold">{{
          error.$message
        }}</strong>
      </p>
    </div>
    <button
      type="button"
      id="login"
      class="w-full flex justify-center bg-yellow-600 hover:bg-yellow-500 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200"
      @click="login"
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
</template>

<script>
import axios from "axios";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers } from "@vuelidate/validators";

export default {
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    return {
      username: "",
      password: "",
    };
  },
  validations() {
    return {
      username: {
        required: helpers.withMessage(
          "아이디를 입력하지 않았습니다.",
          required
        ),
        $autoDirty: true,
      },
      password: {
        required: helpers.withMessage(
          "비밀번호를 입력하지 않았습니다.",
          required
        ),
        $autoDirty: true,
      },
    };
  },
  methods: {
    async login() {
      const isValidate = await this.v$.$validate();

      if (isValidate) {
        const url = "/api/login";

        const data = {
          username: this.username,
          password: this.password,
        };
        console.log(this.v$.$validate());
        axios
          .post(url, data)
          .then((response) => {
            console.log(response.data);
            this.$store.commit("setIsLogin", true);
            this.$router.push("/section");
          })
          .catch((error) => {
            console.log(error.response.data);
          });
      }
    },
  },
};
</script>
