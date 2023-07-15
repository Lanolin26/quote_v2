<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "HeaderComponent",
  props: ['title'],
  emits: ['drawerClick'],
  computed: {
    darkMode: {
      get: function(): boolean {
        return this.$q.dark.isActive;
      },
      set: function(value: boolean) {
        this.$q.dark.set(value);
        this.$q.sessionStorage.set('dark-mode', value);
      }
    }
  },
  methods: {
    toggleLeftDrawer() {
      this.$emit('drawerClick');
    },
  }
})
</script>

<template>
  <q-header elevated class="bg-primary text-white">
    <q-toolbar>
      <q-btn flat dense round icon="menu" aria-label="Menu" @click="toggleLeftDrawer"/>
      <q-toolbar-title>{{ title }}</q-toolbar-title>
      <q-space/>
      <q-toggle color="black" v-model="darkMode" label="Dark mode"/>
    </q-toolbar>
  </q-header>
</template>

<style scoped>

</style>
