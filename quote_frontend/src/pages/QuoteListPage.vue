<script lang="ts">
import { defineComponent } from 'vue';
import QuoteSkeleton from 'components/QuoteSkeleton.vue';
import { Pageable, QuoteEntity } from 'components/models';
import QuoteCardComponent from 'components/QuoteCardView.vue';
import EditQuotePage from 'pages/EditQuotePage.vue';
import { errorNotify } from 'src/utils/notifyUtils';

type DataV = {
  loading: boolean;
  toEditQuote: Partial<QuoteEntity>;
  openEdit: boolean;
  totalPage: number;
  quoteEntity: QuoteEntity[];
};

export default defineComponent({
  name: 'QuoteListPage',
  components: {
    EditQuotePage,
    QuoteCardComponent,
    QuoteSkeleton,
  },
  computed: {
    page: {
      get(): number {
        const query = this.$route.query;
        const pageQuery = query.page;
        return pageQuery ? Number.parseInt(pageQuery as string) : 0;
      },
      set(value: number) {
        this.$router.push({
          path: this.$route.fullPath,
          query: {
            page: value,
            size: this.elementByPage,
          },
        });
      },
    },
    elementByPage: {
      get(): number {
        const query = this.$route.query;
        const sizeQuery = query.size;
        return sizeQuery ? Number.parseInt(sizeQuery as string) : 10;
      },
      set(value: number) {
        this.$router.push({
          path: this.$route.fullPath,
          query: {
            page: this.page,
            size: value
          },
        });
      },
    },
  },
  data(): DataV {
    return {
      loading: false,
      toEditQuote: {},
      openEdit: false,
      totalPage: 0,
      quoteEntity: [],
    };
  },
  watch: {
    page() {
      this.loadElements();
    },
  },
  methods: {
    loadElements() {
      const paging: Pageable = {
        size: this.elementByPage,
        page: this.page - 1,
      };
      this.loading = true;
      this.$api.quote
        .getAll(paging)
        .then((res) => {
          this.quoteEntity = res.content;
          this.totalPage = res.totalPages;
          this.page = res.number + 1;
        })
        .catch((err) =>
          this.$q.notify(errorNotify('Some error when load entities: ' + err))
        )
        .finally(() => (this.loading = false));
    },
    clickToEditQuote(quoteEntity: QuoteEntity) {
      this.toEditQuote = quoteEntity;
      this.openEdit = true;
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
        is-edit
        @clickToEdit="clickToEditQuote"
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
    <q-dialog v-model="openEdit" full-width>
      <edit-quote-page
        :quote-id="toEditQuote.id"
        is-close
        @close="openEdit = false"
      />
    </q-dialog>
  </q-page>
</template>

<style scoped></style>
