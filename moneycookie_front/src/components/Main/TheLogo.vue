<template>
  <div>
    <inline-svg :src="require('@/assets/images/logo.svg')" />
  </div>
</template>

<script>
import InlineSvg from "vue-inline-svg";

export default {
  components: {
    InlineSvg,
  },
};
</script>
