<template>
  <div
    class="relative w-64 h-80 border-4 border-gray-400 border-solid rounded-xl cursor-pointer"
  >
    <button
      type="button"
      class="absolute top-0 right-0 w-7 m-3 origin-right opacity-20"
      @click.stop="beDelete"
    >
      <img src="@/assets/images/delete_icon.png" alt="삭제" />
    </button>
    <div class="mx-3 my-12 text-3xl font-bold text-center">
      <h3>포트폴리오 1123123</h3>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    beDelete() {
      console.log("호출");
      this.$emit("be-delete");
    },
  },
};
</script>
