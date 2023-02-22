<template>
  <div class="absolute w-2/3 left-1/2 top-2/3 -translate-x-1/2 -translate-y-80">
    <div class="flex flex-wrap gap-x-32 gap-y-20 justify-around">
      <FilledCard
        v-for="section in sectionList"
        :key="section.id"
        :title="section.title"
        @delete-mode="blockRoute"
        @cancel-delete="activeRoute"
        @click="goToPortfolio(section.sequence)"
      />
      <div v-if="!isFullSection">
        <router-link to="/section/add">
          <BlankCard />
        </router-link>
      </div>
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
      isFullSection: false,
      sectionList: [],
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
        console.log(response.data);
        this.sectionList = response.data;
        for (let i = 0; i < this.sectionList.length; i++) {
          this.sectionList[i].sequence = i + 1;
        }
        this.checkFullSection(this.sectionList);
        this.$store.commit("setSectionList", this.sectionList);
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
    goToPortfolio(sequence) {
      this.$store.commit("setIndex", sequence);
      console.log("sequence : " + sequence);

      if (!this.deleteMode) {
        this.$router.push({ name: "sequence", params: { sequence } });
      }
    },
    checkFullSection(sectionList) {
      if (sectionList.length >= 6) {
        this.isFullSection = true;
      }
    },
  },
};
</script>
