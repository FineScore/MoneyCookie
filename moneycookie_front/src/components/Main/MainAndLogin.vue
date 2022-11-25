<template>
  <div
    class="relative min-h-screen sm:flex sm:flex-row justify-center bg-transparent rounded-3xl shadow-xl"
  >
    <the-logo
      class="flex-col flex self-center items-center mx-auto sm:mx-0 z-10 w-96"
    ></the-logo>
    <login-form></login-form>
  </div>
</template>

<script>
import LoginForm from "./LoginForm.vue";
import TheLogo from "./TheLogo.vue";

export default {
  components: {
    TheLogo,
    LoginForm,
  },
};
</script>
