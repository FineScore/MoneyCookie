<template>
  <div class="bg-gradient-to-t from-stone-400 via-yellow-400 to-yellow-600">
    <div
      class="relative min-h-screen sm:flex sm:flex-row justify-center bg-transparent rounded-3xl shadow-xl"
    >
      <GlobalLogo
        class="flex-col flex self-center items-center mx-auto sm:mx-0 z-10 w-96"
      />
      <div class="flex justify-center self-center z-10">
        <div class="p-12 bg-white mx-auto rounded-3xl w-96">
          <LoginForm />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LoginForm from "./LoginForm.vue";
import GlobalLogo from "../global/GlobalLogo.vue";

export default {
  components: {
    GlobalLogo,
    LoginForm,
  },
};
</script>
