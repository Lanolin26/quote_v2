<script lang="ts">
import { defineComponent } from 'vue';
import { QuoteEntity } from 'components/models';
import QuoteCardComponent from 'components/QuoteCardView.vue';
import { errorNotify } from "src/utils/notifyUtils";

export default defineComponent({
  name: 'OneQuotePage',
  components: { QuoteCardComponent },
  props: {
    quoteId: { type: String, required: true },
  },
  data(): { quoteItem: Partial<QuoteEntity>; loading: boolean } {
    return {
      quoteItem: {},
      loading: true,
    };
  },
  methods: {
    loadEntity() {
      this.loading = true;
      this.$api.quote
          .getOne(this.quoteId)
          .then(val => this.quoteItem = val)
          .catch(err => this.$q.notify(errorNotify(`Cannot loading: ${err}`)))
          .finally(() => this.loading = false)
    },
  },
  beforeMount() {
    this.loadEntity();
  },
});
</script>

<template>
  <div>
    <transition appear
                enter-active-class="animated fadeIn"
                leave-active-class="animated fadeOut">
      <quote-card-component :quote="quoteItem" :loading="loading" />
    </transition>
  </div>
</template>

<style scoped></style>
