<template>
  <div class="bg-gradient-to-t from-stone-400 via-yellow-400 to-yellow-600">
    <div
      class="relative min-h-screen sm:flex sm:flex-row justify-center bg-transparent rounded-3xl shadow-xl"
    >
      <div class="flex justify-center self-center z-10">
        <div class="p-12 bg-white mx-auto rounded-3xl w-[30rem]">
          <SignUpForm />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SignUpForm from "./SignUpForm.vue";

export default {
  components: {
    SignUpForm,
  },
};
</script>
