<template>
  <q-layout view="hhh lpr fff">
    <header-component :title="title" @drawerClick="toggleLeftDrawer" />

    <admin-left-drawer-component v-model="leftDrawerOpen" />

    <q-page-container>
      <router-view />
    </q-page-container>

    <footer-component :title="title" />

  </q-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import AdminLeftDrawerComponent from 'components/layout/AdminLeftDrawerComponent.vue';
import HeaderComponent from 'components/layout/HeaderComponent.vue';
import FooterComponent from 'components/layout/FooterComponent.vue';

export default defineComponent({
  name: 'AdminLayout',
  components: { FooterComponent, HeaderComponent, AdminLeftDrawerComponent },

  data() {
    return {
      leftDrawerOpen: false,
      title: 'Admin Цитатник',
    };
  },

  methods: {
    toggleLeftDrawer() {
      this.leftDrawerOpen = !this.leftDrawerOpen;
    },
  },
});
</script>
