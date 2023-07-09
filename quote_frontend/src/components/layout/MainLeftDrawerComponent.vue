<script lang="ts">
import {defineComponent} from 'vue'

interface ItemsProps {
  label: string;
  route: {
    name: string;
  };
  icon: string;
}

interface DataProps {
  items: Array<ItemsProps>;
}

export default defineComponent({
  name: 'MainLeftDrawerComponent',
  props: [ 'leftDrawerOpen' ],
  emits: [ 'update:leftDrawerOpen' ],
  computed: {
    leftDrawerOpenLocal: {
      get(): boolean {
        return this.leftDrawerOpen
      },
      set(newValue: boolean) {
        this.$emit('update:leftDrawerOpen', newValue)
      },
    },
  },
  data(): DataProps {
    return {
      items: [
        { label: 'View Quote\'s', icon: 'inbox', route: { name: 'QuoteListPage' } },
        { label: 'Create Quote', icon: 'inbox', route: { name: 'CreateQuotePage' } },
        { label: 'Admin Quote\'s', icon: 'inbox', route: { name: 'AdminIndexPage' } },
      ]
    }
  }
})
</script>

<template>
  <q-drawer show-if-above v-model="leftDrawerOpenLocal"
            side="left"
            elevated>
    <q-list>
      <q-item v-for="(item, i) in items" :key="i" clickable v-ripple :to="item.route">
        <q-item-section avatar>
          <q-icon :name="item.icon" />
        </q-item-section>
        <q-item-section>{{ item.label }}</q-item-section>
      </q-item>
    </q-list>
  </q-drawer>
</template>

<style scoped>

</style>
