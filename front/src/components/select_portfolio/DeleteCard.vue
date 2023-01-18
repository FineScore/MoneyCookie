<template>
  <div class="h-full flex flex-col space-y-6 px-10 justify-center">
    <button
      type="button"
      class="w-full py-3 text-sm text-center text-white bg-red-600 border border-red-600 rounded-lg"
    >
      삭제
    </button>
    <button
      type="button"
      class="w-full py-3 text-sm text-center text-gray-500 bg-white border border-gray-200 rounded-lg"
      @click.stop="cancelDelete"
    >
      취소
    </button>
  </div>
</template>

<script>
export default {
  methods: {
    cancelDelete() {
      this.$emit("cancel-delete");
    },
  },
};
</script>
