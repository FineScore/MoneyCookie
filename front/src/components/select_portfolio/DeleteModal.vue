<template>
  <BasicModal>
    <div class="p-6 text-center">
      <h1 class="text-2xl font-bold">정말로 삭제하시겠습니까?</h1>
      <p class="text-sm text-gray-500">등록된 보유 종목이 모두 삭제됩니다.</p>
    </div>
    <div class="flex flex-row mt-6 space-x-2 justify-evenly">
      <a
        href="#"
        class="w-full py-3 text-sm font-medium text-center text-white bg-red-600 border border-red-600 rounded-lg"
        >삭제</a
      >
      <button
        type="button"
        class="w-full py-3 text-sm text-center text-gray-500 bg-white border border-gray-200 rounded-lg"
        @click="cancelDelete"
      >
        취소
      </button>
    </div>
  </BasicModal>
</template>

<script>
import BasicModal from "../basic/BasicModal.vue";

export default {
  components: {
    BasicModal,
  },
  methods: {
    cancelDelete() {
      this.$emit("cancel-delete");
    },
  },
};
</script>
