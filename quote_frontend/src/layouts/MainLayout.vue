<template>
  <q-layout view="hhh lpr fff">
    <header-component :title="title" @drawerClick="toggleLeftDrawer"/>

    <main-left-drawer-component v-model:left-drawer-open="leftDrawerOpen" />

    <q-page-container>
      <q-scroll-area style="height: 85vh; max-width: 100vw;">
        <router-view/>
      </q-scroll-area>
    </q-page-container>

    <footer-component :title="title"/>

  </q-layout>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import FooterComponent from "components/layout/FooterComponent.vue";
import HeaderComponent from "components/layout/HeaderComponent.vue";
import MainLeftDrawerComponent from "components/layout/MainLeftDrawerComponent.vue";

export default defineComponent({
  name: 'MainLayout',
  components: {MainLeftDrawerComponent, HeaderComponent, FooterComponent},

  data() {
    return {
      leftDrawerOpen: false,
      title: 'Цитатник',
    }
  },

  methods: {
    toggleLeftDrawer() {
      this.leftDrawerOpen = !this.leftDrawerOpen
    }
  }
});
</script>
