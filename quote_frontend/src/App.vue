<template>
  <router-view />
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'App',
  beforeMount() {
    this.$q.dark.set(this.$q.sessionStorage.getItem<boolean>('dark-mode') || false);
  },
});
</script>
