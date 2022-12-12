<template>
  <div
    class="absolute w-2/3 h-4/5 left-1/2 top-1/2 -translate-x-1/2 -translate-y-80"
  >
    <div class="flex flex-wrap gap-x-32 gap-y-20 justify-around">
      <router-link to="/portfolio/1"><FilledCard /></router-link>
      <router-link to="/portfolio/add">
        <BlankCard />
      </router-link>
    </div>
  </div>
</template>

<script>
import BlankCard from "../UI/BlankCard.vue";
import FilledCard from "../UI/FilledCard.vue";

export default {
  components: {
    BlankCard,
    FilledCard,
  },
};
</script>
