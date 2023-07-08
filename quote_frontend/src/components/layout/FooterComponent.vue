<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "FooterComponent",
  props: [ 'title' ]
})
</script>

<template>
  <q-footer elevated class="bg-grey-8 text-white">
    <q-toolbar>
      <q-toolbar-title>
        <q-toolbar-title>{{ title }}</q-toolbar-title>
      </q-toolbar-title>
      <div>Quasar v{{ $q.version }}</div>
    </q-toolbar>
  </q-footer>
</template>

<style scoped>

</style>
