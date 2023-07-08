<script lang="ts">
import { defineComponent } from 'vue';
import QuoteSkeleton from 'components/QuoteSkeleton.vue';
import { Pageable } from 'components/models';
import { mapActions, mapState } from 'pinia';
import { useQuoteStore } from 'stores/quote';
import QuoteCardComponent from 'components/QuoteCardView.vue';

export default defineComponent({
  name: 'QuoteListPage',
  components: { QuoteCardComponent, QuoteSkeleton },
  computed: {
    ...mapState(useQuoteStore, [
      'quoteEntity',
      'totalPage',
      'page',
      'elementByPage',
    ]),
  },
  data() {
    return {
      loading: false,
    };
  },
  watch: {
    page() {
      this.loadElements();
    },
  },
  methods: {
    ...mapActions(useQuoteStore, ['getQuoteEntity']),
    loadElements() {
      const paging: Pageable = {
        size: this.elementByPage,
        page: this.page - 1,
      };
      this.loading = true;
      this.getQuoteEntity(paging, () => (this.loading = false));
    },
  },
  beforeMount() {
    this.loadElements();
  },
});
</script>

<template>
  <q-page>
    <quote-skeleton v-if="loading" />
    <div v-else>
      <quote-card-component
        v-for="item in quoteEntity"
        :quote="item"
        :key="item.id"
      />
      <q-pagination
        v-model="page"
        :max="totalPage"
        :max-pages="6"
        boundary-numbers
        direction-links
        class="q-pa-lg relative-position vertical-middle flex-center"
        color="grey"
        active-color="primary"
      />
    </div>
  </q-page>
</template>

<style scoped></style>
