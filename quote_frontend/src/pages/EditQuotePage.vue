<script lang="ts">
import { defineComponent } from 'vue';
import {
  QuoteEntity,
  SourceEntity,
  SourceTypeEntity,
  UserEntity,
} from 'components/models';
import { errorNotify, infoNotify } from "src/utils/notifyUtils";
import QuoteCardEdit from 'components/cardEdit/QuoteCardEdit.vue';

type DataV = {
  quoteEntity: Partial<QuoteEntity>;
  sourceEntity: SourceEntity[];
  sourceEntityTypes: SourceTypeEntity[];
  userEntities: UserEntity[];
};

export default defineComponent({
  name: 'EditQuotePage',
  components: { QuoteCardEdit },
  props: {
    quoteId: { type: String, required: true },
    isClose: { type: Boolean, required: false, default: false}
  },
  emits: ['close'],
  data(): DataV {
    return {
      quoteEntity: {},
      sourceEntity: [],
      sourceEntityTypes: [],
      userEntities: [],
    };
  },
  methods: {
    onRequest() {
      this.$q.loadingBar.start();
      Promise.all([
        this.$api.quote.getOne(this.quoteId),
        this.$api.user.getAll({ page: 0, size: 9999 }),
        this.$api.sourceType.getAll({ page: 0, size: 9999 }),
        this.$api.source.getAll({ page: 0, size: 9999 }),
      ])
        .then(([quote, users, sourceTypes, sources]) => {
          this.quoteEntity = quote;
          this.userEntities = users.content;
          this.sourceEntityTypes = sourceTypes.content;
          this.sourceEntity = sources.content;
        })
        .catch((err) =>
          this.$q.notify(errorNotify(`Some error when load: ${err}`))
        )
        .finally(() => this.$q.loadingBar.stop());
    },
    saveDialogElement(element: QuoteEntity) {
      let errorFunc = (err: string) =>
        this.$q.notify(errorNotify(`Some error when create quote: ${err}`));
      let successFunc = (val: QuoteEntity) => {
        this.$q.notify(infoNotify(`Success to save quote: ${val.id}`));
        this.$emit('close')
      };
      this.$api.quote.update(this.quoteId, element).then(successFunc)
          .catch(errorFunc);
    },
  },
  beforeMount() {
    this.onRequest();
  },
});
</script>

<template>
  <q-page>
      <quote-card-edit
        is-edit
        :is-close="isClose"
        :quote="quoteEntity"
        :authors="userEntities"
        :source-types="sourceEntityTypes"
        :sources="sourceEntity"
        :loading="$q.loadingBar.isActive"
        @update="saveDialogElement"
        @close="$emit('close')"
      />
  </q-page>
</template>

<style scoped></style>
