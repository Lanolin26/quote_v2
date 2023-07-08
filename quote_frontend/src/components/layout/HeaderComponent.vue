<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "HeaderComponent",
  props: ['title'],
  emits: ['drawerClick'],
  methods: {
    toggleLeftDrawer() {
      this.$emit('drawerClick');
    }
  }
})
</script>

<template>
  <q-header elevated class="bg-primary text-white">
    <q-toolbar>
      <q-btn flat dense round icon="menu" aria-label="Menu" @click="toggleLeftDrawer"/>
      <q-toolbar-title>{{ title }}</q-toolbar-title>
    </q-toolbar>
  </q-header>
</template>

<style scoped>

</style>
