<script lang="ts">
import { defineComponent } from 'vue';
import QuoteCardEdit from 'components/cardEdit/QuoteCardEdit.vue';
import {
  QuoteEntity,
  SourceEntity,
  SourceTypeEntity,
  UserEntity,
} from 'components/models';
import { errorNotify, infoNotify } from 'src/utils/notifyUtils';

interface DataV {
  sourceTypeEntities: Array<SourceTypeEntity>;
  sourceEntities: Array<SourceEntity>;
  authorEntities: Array<UserEntity>;
}

export default defineComponent({
  name: 'CreateQuotePage',
  components: { QuoteCardEdit },
  data(): DataV {
    return {
      sourceTypeEntities: [],
      sourceEntities: [],
      authorEntities: [],
    };
  },
  methods: {
    initEditItem() {
      this.$q.loadingBar.start();
      Promise.all([
        this.$api.user.getAll({ page: 0, size: 9999 }),
        this.$api.sourceType.getAll({ page: 0, size: 9999 }),
        this.$api.source.getAll({ page: 0, size: 9999 }),
      ])
        .then(([users, sourceTypes, sources]) => {
          this.authorEntities = users.content;
          this.sourceTypeEntities = sourceTypes.content;
          this.sourceEntities = sources.content;
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
      };
      this.$api.quote.create(element).then(successFunc).catch(errorFunc);
    },
  },
  beforeMount() {
    this.initEditItem();
  },
});
</script>

<template>
  <div>
    <quote-card-edit
      is-edit
      :authors="authorEntities"
      :source-types="sourceTypeEntities"
      :sources="sourceEntities"
      :loading="$q.loadingBar.isActive"
      @save="saveDialogElement"
    />
  </div>
</template>

<style scoped></style>
