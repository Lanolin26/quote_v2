<script lang="ts">
import { defineComponent, PropType } from 'vue';
import {  SourceTypeEntity } from "components/models";
import { QForm } from "quasar";

export default defineComponent({
  name: 'SourceTypeCard',
  props: {
    isEdit: { type: Boolean, default: true },
    isDelete: { type: Boolean, default: true },
    isClose: { type: Boolean, default: true },
    sourceType: { type: Object as PropType<SourceTypeEntity> | undefined },
    readOnly: {type: Boolean, default: false}
  },
  emits: [ 'close', 'delete', 'save' , 'update' ],
  computed: {
    title(): string {
      return this.isEdit ? 'Редактировать' : this.isDelete ? 'Удалить' : 'Создать';
    },
    localForm(): QForm {
      return this.$refs['local-form'] as QForm;
    }
  },
  watch: {
    sourceType: {
      immediate: true,
      handler() {
        this.initEditItem();
      },
    },
  },
  data(): { sourceTypeLocal: Partial<SourceTypeEntity> } {
    return {
      sourceTypeLocal: {}
    };
  },
  methods: {
    initEditItem() {
      this.sourceTypeLocal = this.sourceType ? this.sourceType : {  };
    },
    reset() {
      this.localForm.reset();
    },
    save() {
      this.localForm.validate().then((success: boolean) => {
        if (success) {
          if(this.sourceTypeLocal.id) {
            this.$emit('update', this.sourceTypeLocal)
          }else{
            this.$emit('save', this.sourceTypeLocal)
          }
        }
      })

    },
    deleteQuote() {
      this.$emit('delete', this.sourceTypeLocal)
    },
    close() {
      this.$emit('close')
    },
  },
  mount() {
    this.initEditItem();
  },
});
</script>

<template>
  <q-card bordered class="q-pa-sm q-ma-sm" style="min-width: 640px">
    <q-card-section>
      <span class="text-h5">{{ title }}</span>
    </q-card-section>

    <q-card-section>
      <q-form ref="local-form">
        <q-input id="id" name="id" label="ID" type="text" v-model="sourceTypeLocal.id" disable readonly />
        <q-input id="sourceType" name="type" label="Source Type" type="text"
                 :readonly="readOnly" clear-icon="highlight_off" stack-label
                 clearable
                 v-model="sourceTypeLocal.name"
                 :rules="[ value => value && value.length > 0 || 'Not empty' ]"
        />
      </q-form>
    </q-card-section>

    <q-card-actions>
      <q-btn v-if="isEdit" label="Submit" type="submit" color="positive"  @click="save"  />
      <q-btn v-if="isDelete" label="Delete" type="reset" color="negative" @click="deleteQuote" />
      <q-btn v-if="isClose" label="Close" type="reset" color="primary" flat class="q-ml-sm" @click="close" />
    </q-card-actions>
  </q-card>
</template>

<style scoped></style>
