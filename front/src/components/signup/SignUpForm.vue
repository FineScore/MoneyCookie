<template>
  <form class="space-y-6">
    <div class="space-y-2">
      <label for="username">ID *</label>
      <div class="flex justify-between">
        <input
          type="text"
          name="username"
          id="username"
          placeholder="아이디를 입력해 주세요."
          autocomplete="off"
          class="w-[70%] w- text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
          v-model="v$.username.$model"
        />
        <button
          type="button"
          class="w-[25%] flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer"
          @click="checkUnique"
        >
          중복 확인
        </button>
      </div>
      <p v-for="error of v$.username.$errors" :key="error.$uid">
        <strong class="text-xs text-red-600 font-semibold">{{
          error.$message
        }}</strong>
      </p>
      <div>
        <strong class="text-xs text-red-600 font-semibold" v-if="isDuplicate"
          >이미 존재하는 아이디입니다.</strong
        >
        <strong class="text-xs text-blue-600 font-semibold" v-if="isUnique"
          >사용 가능한 아이디입니다.</strong
        >
      </div>
    </div>
    <div class="space-y-2">
      <label for="password">비밀번호 *</label>
      <input
        type="password"
        name="password"
        id="password"
        placeholder="비밀번호를 입력해 주세요."
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="v$.password.$model"
      />
      <p v-for="error of v$.password.$errors" :key="error.$uid">
        <strong class="text-xs text-red-600 font-semibold">{{
          error.$message
        }}</strong>
      </p>
    </div>
    <div class="space-y-2">
      <label for="password-confirm">비밀번호 확인 *</label>
      <input
        type="password"
        id="password-confirm"
        placeholder="비밀번호를 한 번 더 입력해 주세요."
        class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
        v-model="v$.passwordConfirm.$model"
      />
      <p v-for="error of v$.passwordConfirm.$errors" :key="error.$uid">
        <strong class="text-xs text-red-600 font-semibold">{{
          error.$message
        }}</strong>
      </p>
    </div>
    <div class="h-px bg-gray-200"></div>
    <div>
      <button
        type="button"
        @click="register"
        class="w-full flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
      >
        회원 가입
      </button>
    </div>
  </form>
</template>

<script>
import axios from "axios";
import { useVuelidate } from "@vuelidate/core";
import {
  required,
  minLength,
  maxLength,
  alphaNum,
  helpers,
  sameAs,
} from "@vuelidate/validators";

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
      username: "",
      password: "",
      passwordConfirm: "",
      isDuplicate: false,
      isUnique: false,
    };
  },
  validations() {
    return {
      username: {
        required: helpers.withMessage(
          "아이디를 입력하지 않았습니다.",
          required
        ),
        alphaNum: helpers.withMessage(
          "영어, 숫자만 입력 가능합니다.",
          alphaNum
        ),
        maxLengthValue: helpers.withMessage(
          "아이디는 10글자까지 입력 가능합니다.",
          maxLength(10)
        ),
        $autoDirty: true,
      },
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
    async checkUnique() {
      const isUsernameValidate = await this.v$.username.$validate();

      if (isUsernameValidate) {
        const url = "/api/check";
        axios
          .get(url, {
            params: {
              username: this.username,
            },
          })
          .then((response) => {
            console.log(response);
            this.isDuplicate = false;
            this.isUnique = true;
          })
          .catch((error) => {
            console.log(error);
            this.isDuplicate = true;
            this.isUnique = false;
          });
      }
    },
    async register() {
      const isValidate = await this.v$.$validate();

      if (isValidate && this.isUnique) {
        const url = "/api/register";
        const data = {
          username: this.username,
          password: this.password,
        };

        console.log(this.username + " " + this.password);
        axios
          .post(url, data)
          .then((response) => {
            console.log(response);
            this.$router.push("/");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>
