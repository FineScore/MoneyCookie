<template>
  <div
    class="relative w-64 h-80 border-4 border-gray-400 border-solid rounded-xl cursor-pointer"
  >
    <DeleteCard v-if="deleteMode" @cancel-delete="cancelDeleteMode" />
    <div v-else>
      <button
        type="button"
        class="absolute top-0 right-0 w-7 m-3 origin-right opacity-20"
        @click="execDeleteMode"
      >
        <img src="@/assets/images/delete_icon.png" alt="삭제" />
      </button>
      <div class="mx-3 my-12 text-3xl font-bold text-center">
        <h3>{{ this.title }}</h3>
      </div>
    </div>
  </div>
</template>

<script>
import DeleteCard from "./DeleteCard.vue";

export default {
  data() {
    return {
      deleteMode: false,
    };
  },
  props: ["title"],
  components: {
    DeleteCard,
  },
  methods: {
    execDeleteMode() {
      this.deleteMode = true;
      this.$emit("delete-mode");
    },
    cancelDeleteMode() {
      this.deleteMode = false;
      this.$emit("cancel-delete");
    },
  },
};
</script>
