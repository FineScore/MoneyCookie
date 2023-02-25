<template>
  <HeaderContainer />
  <RouterView />
</template>

<script>
import HeaderContainer from "@/components/header/HeaderContainer.vue";

export default {
  components: {
    HeaderContainer,
  },
};
</script>
