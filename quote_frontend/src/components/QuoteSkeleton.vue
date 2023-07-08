<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: 'QuoteSkeleton'
})
</script>

<template>
  <q-card style="max-width: 640px;" class="fixed-top fixed-center">
    <q-skeleton height="60px" type="text" />

    <q-skeleton height="200px" square />

    <q-card-actions align="right" class="q-gutter-md">
      <q-skeleton type="QBtn" />
      <q-skeleton type="QBtn" />
    </q-card-actions>
  </q-card>
</template>

<style scoped>

</style>
