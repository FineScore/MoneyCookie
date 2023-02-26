<template>
  <div class="absolute w-2/3 left-1/2 top-2/4 -translate-x-1/2 -translate-y-80">
    <div class="flex flex-wrap gap-x-32 gap-y-20 justify-around">
      <ContentCard
        v-for="section in sectionList"
        :key="section.id"
        :id="section.id"
        :title="section.title"
        @delete-mode="blockRouter"
        @cancel="activeRouter"
        @click="goToSection(section.sequence)"
      />
      <div v-if="!isMaxSection">
        <router-link to="/section/create">
          <BlankCard />
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import BlankCard from "./BlankCard.vue";
import ContentCard from "./ContentCard.vue";
import axios from "axios";

export default {
  data() {
    return {
      isDeleteMode: false,
      isMaxSection: false,
      sectionList: [],
    };
  },
  components: {
    BlankCard,
    ContentCard,
  },
  mounted() {
    this.getSection();
  },
  methods: {
    blockRouter() {
      this.isDeleteMode = true;
    },
    activeRouter() {
      this.isDeleteMode = false;
    },
    goToSection(sequence) {
      this.$store.commit("setIndex", sequence);
      console.log("sequence : " + sequence);

      if (!this.isDeleteMode) {
        this.$router.push({ name: "sequence", params: { sequence } });
      }
    },
    checkMaxSection(sectionList) {
      if (sectionList.length >= 6) {
        this.isMaxSection = true;
      }
    },
    getSection() {
      const url = "/api/section";

      axios
        .get(url)
        .then((response) => {
          console.log(response.data);
          this.sectionList = response.data.data;
          for (let i = 0; i < this.sectionList.length; i++) {
            this.sectionList[i].sequence = i + 1;
          }
          this.checkMaxSection(this.sectionList);
          this.$store.commit("setSectionList", this.sectionList);
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
  },
};
</script>
