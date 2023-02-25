<template>
  <div class="h-full flex flex-col space-y-6 px-10 justify-center">
    <button
      type="button"
      class="w-full py-3 text-sm text-center text-white bg-red-600 border border-red-600 rounded-lg"
      @click="deleteSection(id)"
    >
      삭제
    </button>
    <button
      type="button"
      class="w-full py-3 text-sm text-center text-gray-500 bg-white border border-gray-200 rounded-lg"
      @click.stop="cancel"
    >
      취소
    </button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["id"],
  methods: {
    cancel() {
      this.$emit("cancel");
    },
    deleteSection(id) {
      const url = "/api/section";

      axios
        .delete(url, {
          params: {
            sectionId: id,
          },
        })
        .then((response) => {
          console.log(response);
          this.$router.go(0);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
