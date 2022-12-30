<template>
  <div class="bg-gradient-to-t from-stone-400 via-yellow-400 to-yellow-600">
    <div
      class="relative min-h-screen sm:flex sm:flex-row justify-center bg-transparent rounded-3xl shadow-xl"
    >
      <BasicLogo
        class="flex-col flex self-center items-center mx-auto sm:mx-0 z-10 w-96"
      />
      <LoginForm />
    </div>
  </div>
</template>

<script>
import LoginForm from "./LoginForm.vue";
import BasicLogo from "@/components/basic/BasicLogo.vue";

export default {
  components: {
    BasicLogo,
    LoginForm,
  },
};
</script>
