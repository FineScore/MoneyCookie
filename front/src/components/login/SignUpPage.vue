<template>
  <div class="bg-gradient-to-t from-stone-400 via-yellow-400 to-yellow-600">
    <div
      class="relative min-h-screen sm:flex sm:flex-row justify-center bg-transparent rounded-3xl shadow-xl"
    >
      <div class="flex justify-center self-center z-10">
        <div class="p-12 bg-white mx-auto rounded-3xl w-[30rem]">
          <form class="space-y-6">
            <div class="space-y-2">
              <label for="username">ID *</label>
              <div class="flex justify-between">
                <input
                  type="text"
                  name="username"
                  id="username"
                  placeholder="ID를 입력해주세요."
                  autocomplete="off"
                  class="w-[70%] w- text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                  v-model="username"
                />
                <button
                  type="button"
                  class="w-[25%] flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer"
                  @click="checkDuplicate"
                >
                  중복 확인
                </button>
              </div>
              <div>
                <strong
                  class="text-sm text-red-600 font-semibold"
                  v-if="!isUsername"
                  >아이디를 입력해주세요.</strong
                >
                <strong
                  class="text-sm text-red-600 font-semibold"
                  v-if="isDuplicate"
                  >이미 존재하는 아이디입니다.</strong
                >
                <strong
                  class="text-sm text-blue-600 font-semibold"
                  v-if="isPassCheck"
                  >사용 가능한 아이디입니다.</strong
                >
                <strong
                  class="text-sm text-red-600 font-semibold"
                  v-if="isInvaildUsername"
                  >영어 숫자 조합만 가능합니다.</strong
                >
              </div>
            </div>
            <div class="space-y-2">
              <label for="password-current">비밀번호 *</label>
              <input
                type="password"
                name="password"
                id="password-current"
                placeholder="비밀번호를 입력해주세요."
                class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                v-model="password"
              />
              <strong
                class="text-sm text-red-600 font-semibold"
                v-if="!isPassword"
                >비밀번호를 입력해주세요.</strong
              >
            </div>
            <div class="space-y-2">
              <label for="password-confirm">비밀번호 확인 *</label>
              <input
                type="password"
                id="password-confirm"
                placeholder="비밀번호를 한 번 더 입력해주세요."
                class="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-yellow-400 transition ease-in duration-200"
                v-model="passwordConfirm"
              />
              <strong
                class="text-sm text-red-600 font-semibold"
                v-if="!isCorrectPassword"
                >비밀번호가 일치하지 않습니다.</strong
              >
            </div>
            <div class="h-px bg-gray-200"></div>
            <div>
              <button
                type="button"
                @click="checkRegisterForm"
                class="w-full flex justify-center bg-yellow-600 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-200 disabled:bg-opacity-50"
              >
                회원 가입
              </button>
            </div>
          </form>
        </div>
      </div>
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
      passwordConfirm: "",
      isUsername: true,
      isPassword: true,
      isCorrectPassword: true,
      isDuplicate: false,
      isPassCheck: false,
      isInvaildUsername: false,
    };
  },
  methods: {
    checkDuplicate() {
      const url = "http://localhost:8080/check";
      const regex = /^[a-z|0-9]+$/;

      if (regex.test(this.username)) {
        this.isInvaildUsername = false;
      } else {
        this.isInvaildUsername = true;
      }

      if (this.username === "") {
        this.isUsername = false;
      } else {
        this.isUsername = true;
      }

      if (this.isUsername && !this.isInvaildUsername) {
        axios
          .get(url, {
            params: {
              username: this.username,
            },
          })
          .then((response) => {
            console.log(response);
            this.isDuplicate = false;
            this.isPassCheck = true;
          })
          .catch((error) => {
            console.log(error);
            this.isDuplicate = true;
            this.isPassCheck = false;
          });
      }
    },
    checkRegisterForm() {
      const url = "http://localhost:8080/register";
      const data = {
        username: this.username,
        password: this.password,
      };

      if (this.username === "") {
        this.isUsername = false;
      } else {
        this.isUsername = true;
      }

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

      if (
        this.isUsername &&
        this.isPassword &&
        this.isCorrectPassword &&
        this.isPassCheck
      ) {
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
