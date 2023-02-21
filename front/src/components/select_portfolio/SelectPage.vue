<template>
  <div class="absolute w-2/3 left-1/2 top-2/3 -translate-x-1/2 -translate-y-80">
    <div class="flex flex-wrap gap-x-32 gap-y-20 justify-around">
      <FilledCard
        @delete-mode="blockRoute"
        @cancel-delete="activeRoute"
        @click="goToPortfolio"
      />
      <router-link to="/section/add">
        <BlankCard />
      </router-link>
    </div>
  </div>
</template>

<script>
import BlankCard from "./BlankCard.vue";
import FilledCard from "./FilledCard.vue";
import axios from "axios";

export default {
  data() {
    return {
      deleteMode: false,
    };
  },
  components: {
    BlankCard,
    FilledCard,
  },
  mounted() {
    const url = "/section";
    axios
      .get(url)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    blockRoute() {
      this.deleteMode = true;
    },
    activeRoute() {
      this.deleteMode = false;
    },
    goToPortfolio() {
      if (!this.deleteMode) {
        this.$router.push("/section/1");
      }
    },
  },
};
</script>
