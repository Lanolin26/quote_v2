<script lang="ts">
import { defineComponent, PropType } from 'vue';
import {
  QuoteEntity,
  SourceEntity,
  SourceTypeEntity,
  UserEntity,
} from 'components/models';
import { QForm } from 'quasar';
import OcrCard from 'components/cardEdit/OcrCard.vue';

type DataV = {
  quoteLocal: Partial<QuoteEntity>;
  openDialog: boolean;
};

export default defineComponent({
  name: 'QuoteCardEdit',
  components: { OcrCard },
  props: {
    isEdit: { type: Boolean, default: false },
    isDelete: { type: Boolean, default: false },
    isClose: { type: Boolean, default: false },
    isView: { type: Boolean, default: false },
    quote: { type: Object as PropType<QuoteEntity> | undefined },
    sources: { type: Array as PropType<Array<SourceEntity>>, required: true },
    sourceTypes: {
      type: Array as PropType<Array<SourceTypeEntity>>,
      required: true,
    },
    authors: { type: Array as PropType<Array<UserEntity>>, required: true },
    readOnly: { type: Boolean, default: false },
  },
  emits: ['close', 'delete', 'save', 'update'],
  computed: {
    title(): string {
      if (this.isView) return 'Цитата';
      if (this.isDelete) return 'Удалить';
      if (this.isEdit) return this.quote ? 'Редактировать' : 'Создать';
      return 'Цитата';
    },
    localForm(): QForm {
      return this.$refs['local-form'] as QForm;
    },
    filteredSources(): Array<SourceEntity> {
      return this.sources.filter(
        (item) => item.typeId === this.quoteLocal.sourceTypeId
      );
    },
  },
  watch: {
    quote: {
      immediate: true,
      handler() {
        this.initEditItem();
      },
    },
  },
  data(): DataV {
    return {
      quoteLocal: {},
      openDialog: false,
    };
  },
  methods: {
    initEditItem() {
      this.quoteLocal = this.quote ? this.quote : {};
    },
    reset() {
      this.localForm.reset();
    },
    save() {
      this.localForm.validate().then((success: boolean) => {
        if (success) {
          if (this.quoteLocal.id) {
            this.$emit('update', this.quoteLocal);
          } else {
            this.$emit('save', this.quoteLocal);
          }
          this.reset();
        }
      });
    },
    deleteQuote() {
      this.$emit('delete', this.quoteLocal);
    },
    close() {
      this.$emit('close');
    },
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
      this.$router.push({
        name: 'EditOneQuote',
        params: { id: this.quoteLocal.id },
      });
    },
    clickToCopyText() {
      this.copyToClipboards(this.quoteLocal.text);
    },
    clickToCopyUrl() {
      this.copyToClipboards(
        `${window.location.origin}/quote/${this.quoteLocal.id}`
      );
    },
    cleanNextLine() {
      if (this.quoteLocal.text) {
        this.quoteLocal.text = this.quoteLocal.text
          .replace(/(?<=[\s\S])\n(?=.)/g, ' ')
          .replace(/—/g, '-')
          .replace(/--/g, '-')
          .replace('  ', ' ')
          .replace(/^\w/g, '')
          .replace(/\n\s/g, '\n\n')
          .replace(/\r/g, '')
          .replace(/&nbsp;/g, ' ')
          .replace('\n\n ', '\n\n')
          .replace(String.fromCharCode(160), ' ');
      }
    },
  },
  mount() {
    this.initEditItem();
  },
});
</script>

<template>
  <div>
    <q-card
      bordered
      class="text-black q-pa-sm q-ma-sm"
      style="min-width: 640px"
    >
      <q-card-section>
        <span class="text-h5">{{ title }}</span>
      </q-card-section>

      <q-card-section>
        <q-form ref="local-form">
          <q-input
            id="id"
            name="id"
            label="ID"
            type="text"
            v-model="quoteLocal.id"
            disable
            readonly
          />
          <q-select
            id="sourceType"
            name="sourceType"
            label="Source Type"
            v-model="quoteLocal.sourceTypeId"
            :options="sourceTypes"
            :readonly="readOnly"
            :option-value="
              (opt) => (Object(opt) === opt && 'id' in opt ? opt.id : null)
            "
            :option-label="
              (opt) => (Object(opt) === opt && 'name' in opt ? opt.name : '')
            "
            dense
            map-options
            emit-value
            stack-label
            :rules="[(val) => !!val || 'Field is required']"
          />
          <q-select
            id="source"
            name="source"
            label="Source"
            v-model="quoteLocal.sourceId"
            :options="filteredSources"
            :readonly="readOnly"
            :option-value="
              (opt) => (Object(opt) === opt && 'id' in opt ? opt.id : null)
            "
            :option-label="
              (opt) => (Object(opt) === opt && 'name' in opt ? opt.name : '')
            "
            dense
            map-options
            emit-value
            stack-label
            :rules="[(val) => !!val || 'Field is required']"
          />
          <q-select
            id="author"
            name="Author"
            label="Author"
            v-model="quoteLocal.authorId"
            :options="authors"
            :readonly="readOnly"
            :option-value="
              (opt) => (Object(opt) === opt && 'id' in opt ? opt.id : null)
            "
            :option-label="
              (opt) => (Object(opt) === opt && 'name' in opt ? opt.name : '')
            "
            dense
            map-options
            emit-value
            stack-label
            :rules="[(val) => !!val || 'Field is required']"
          />
          <q-input
            id="text"
            label="Text"
            type="textarea"
            v-model="quoteLocal.text"
            :readonly="readOnly"
            clear-icon="highlight_off"
            stack-label
            :rules="[(val) => (!!val && val !== '') || 'Field is required']"
            clearable
            autogrow
            dense
            counter
            outlined
          >
            <template v-slot:after>
              <q-btn
                round
                dense
                flat
                icon="language"
                @click="openDialog = true"
              />
            </template>
          </q-input>
        </q-form>
      </q-card-section>

      <q-card-actions>
        <q-btn
          v-if="isEdit && !isView"
          label="Submit"
          type="submit"
          color="positive"
          @click="save"
        />
        <q-btn
          v-if="isEdit && !isView"
          @click="cleanNextLine"
          :label="'quote.edit.clear'"
        />
        <q-btn
          v-if="isEdit && !isView"
          @click="reset"
          label="Reset"
        />
        <q-btn
          v-if="isDelete && !isView"
          label="Delete"
          type="reset"
          color="negative"
          @click="deleteQuote"
        />
        <q-btn
          v-if="isClose && !isView"
          label="Close"
          type="reset"
          color="primary"
          flat
          class="q-ml-sm"
          @click="close"
        />
        <q-space />
        <q-btn v-if="isView && isEdit" round icon="edit" @click="clickToEdit" />
        <q-btn
          v-if="isView"
          round
          icon="content_copy"
          @click="clickToCopyText"
        />
        <q-btn v-if="isView" round icon="link" @click="clickToCopyUrl" />
      </q-card-actions>
    </q-card>
    <q-dialog v-model="openDialog">
      <ocr-card v-model="quoteLocal.text" @close="openDialog = false" />
    </q-dialog>
  </div>
</template>

<style scoped></style>
