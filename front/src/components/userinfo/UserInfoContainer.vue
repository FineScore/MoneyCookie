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
        v-model="v$.password.$model"
      />
      <p v-for="error of v$.password.$errors" :key="error.$uid">
        <strong class="text-xs text-red-600 font-semibold">{{
          error.$message
        }}</strong>
      </p>
    </div>
    <div class="space-y-2">
      <label for="password-confirm">비밀번호 확인</label>
      <input
        type="password"
        name="password-confirm"
        id="password-confirm"
        autocomplete="off"
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="v$.passwordConfirm.$model"
      />
    </div>
    <p v-for="error of v$.passwordConfirm.$errors" :key="error.$uid">
      <strong class="text-xs text-red-600 font-semibold">{{
        error.$message
      }}</strong>
    </p>
    <div class="h-px bg-gray-200"></div>
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
import { useVuelidate } from "@vuelidate/core";
import { required, minLength, helpers, sameAs } from "@vuelidate/validators";

const passwordPattern = helpers.regex(
  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/
);

export default {
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    return {
      password: "",
      passwordConfirm: "",
      isPassword: true,
      isCorrectPassword: true,
    };
  },
  validations() {
    return {
      password: {
        required: helpers.withMessage(
          "비밀번호를 입력하지 않았습니다.",
          required
        ),
        minLengthValue: helpers.withMessage(
          "최소 8자 이상 입력해야 합니다.",
          minLength(8)
        ),
        passwordPattern: helpers.withMessage(
          "영어 대소문자, 숫자, 특수 문자가 각각 1개 이상 포함되어야 합니다.",
          passwordPattern
        ),
        $autoDirty: true,
      },
      passwordConfirm: {
        required: helpers.withMessage(
          "비밀번호를 한 번 더 입력해 주세요.",
          required
        ),
        sameAsPassword: helpers.withMessage(
          "비밀번호가 일치하지 않습니다.",
          sameAs(this.password)
        ),
        $autoDirty: true,
      },
    };
  },
  methods: {
    updatePassword() {
      const url = "/api/password";
      if (this.isPassword && this.isCorrectPassword) {
        const data = {
          password: this.password,
        };
        axios
          .put(url, data)
          .then((response) => {
            console.log(response);
            this.$router.push("/section");
          })
          .catch((error) => {
            console.log(error.response.data);
            this.$store.commit("setError", error.response.data);
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
          this.$store.commit("setError", error.response.data);
        });
    },
  },
};
</script>
