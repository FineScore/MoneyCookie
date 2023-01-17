<template>
  <Transition
    enter-from-class="opacity-0"
    enter-active-class="transition duration-300"
    leave-to-class="opacity-0"
    leave-active-class="transition duration-300"
  >
    <DeleteModal v-show="wantDelete" @cancel-delete="cancelDelete" />
  </Transition>
  <div class="absolute w-2/3 left-1/2 top-2/3 -translate-x-1/2 -translate-y-80">
    <div class="flex flex-wrap gap-x-32 gap-y-20 justify-around">
      <div @click="goToPortfolio">
        <FilledCard @be-delete="beDelete" />
      </div>
      <router-link to="/portfolio/add">
        <BlankCard />
      </router-link>
    </div>
  </div>
</template>

<script>
import BlankCard from "../UI/BlankCard.vue";
import DeleteModal from "./DeleteModal.vue";
import FilledCard from "../UI/FilledCard.vue";

export default {
  data() {
    return {
      wantDelete: false,
    };
  },
  components: {
    BlankCard,
    FilledCard,
    DeleteModal,
  },
  methods: {
    beDelete() {
      this.wantDelete = true;
    },
    cancelDelete() {
      this.wantDelete = false;
    },
    goToPortfolio() {
      if (!this.wantDelete) {
        this.$router.push("/portfolio/1");
      }
    },
  },
};
</script>
