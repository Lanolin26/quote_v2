<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "OneQuotePage",
  props: {
    quoteId: { type: String }
  }
});
</script>

<template>
<div>
{{quoteId}}
</div>
</template>

<style scoped>

</style>
