<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { ICON_DEFAULT, QuoteEntity } from 'components/models';

export default defineComponent({
  name: 'QuoteCardComponent',
  props: {
    quote: {
      required: true,
      type: Object as PropType<QuoteEntity>,
    },
    isEdit: {
      required: false,
      default: false,
      type: Boolean,
    },
    loading: {
      required: false,
      default: false,
      type: Boolean,
    },
  },
  emits: ['clickToEdit'],
  computed: {
    id() {
      return this.quote.id;
    },
    sourceName() {
      return this.quote.sourceName;
    },
    sourceType() {
      return this.quote.sourceTypeName;
    },
    authorName() {
      return this.quote.authorName;
    },
    text() {
      return this.quote.text;
    },
    icon() {
      return this.quote.authorIcon ? this.quote.authorId : ICON_DEFAULT;
    },
  },
  methods: {
    async copyToClipboards(text?: string) {
      try {
        if (text) await navigator.clipboard.writeText(text);
        this.$q.notify({
          progress: true,
          color: 'green',
          message: 'Successful copied to Clipboard!',
          timeout: 2000,
          actions: [{ label: 'Dismiss', color: 'white' }],
        });
      } catch ($e) {
        this.$q.notify({
          progress: true,
          message: 'Some error to copy url to Clipboard!',
          color: 'red',
          timeout: 2000,
          actions: [{ label: 'Dismiss', color: 'white' }],
        });
      }
    },
    clickToEdit() {
      this.$emit('clickToEdit', this.quote)
      // this.$router.push({
      //   name: 'EditQuotePage',
      //   params: { quoteId: this.id },
      // });
    },
    clickToCopyText() {
      this.copyToClipboards(this.text);
    },
    clickToCopyUrl() {
      let route = this.$router.resolve({
        name: 'OneQuotePage',
        params: { quoteId: this.id },
      });
      this.copyToClipboards(`${window.location.origin}/${route.href}`);
    },
  },
});
</script>

<template>
  <q-card bordered class="q-pa-sm q-ma-sm" style="min-width: 640px">
    <q-card-section class="flex">
      <div class="text-h6">Цитата</div>
      <q-space />
      <q-chip :clickable="false" outline :label="sourceType">
        <q-tooltip>Тип источника</q-tooltip>
      </q-chip>
      <q-chip :clickable="false" outline :label="sourceName">
        <q-tooltip>Источник цитаты</q-tooltip>
      </q-chip>
    </q-card-section>

    <q-card-section>
      <div class="row">
        <div class="col-1 self-center">Автор:</div>
        <div class="col-5">
          <q-chip outline :clickable="false">
            <q-avatar size="30px">
              <img :alt="authorName" :src="`/api/avatar/${icon}`" />
            </q-avatar>
            {{ authorName }}
          </q-chip>
        </div>
      </div>
    </q-card-section>

    <q-card-section>
      <p><b>Текст:</b></p>
      <p style="white-space: pre-line">{{ text }}</p>
    </q-card-section>

    <q-inner-loading :showing="loading">
      <q-spinner-gears size="50px" color="primary" />
    </q-inner-loading>

    <q-card-actions>
      <q-space />
      <q-btn round icon="edit" outline v-if="isEdit" @click="clickToEdit">
        <q-tooltip>Edit Quote</q-tooltip>
      </q-btn>
      <q-btn round icon="content_copy" outline @click="clickToCopyText">
        <q-tooltip>Copy text from Quote</q-tooltip>
      </q-btn>
      <q-btn round icon="link" outline @click="clickToCopyUrl">
        <q-tooltip>Copy link to this quote</q-tooltip>
      </q-btn>
    </q-card-actions>
  </q-card>
</template>

<style scoped></style>
