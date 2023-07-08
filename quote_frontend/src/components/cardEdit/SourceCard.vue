<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { SourceEntity, SourceTypeEntity } from "components/models";
import { QForm } from "quasar";

export default defineComponent({
  name: 'SourceCard',
  props: {
    isEdit: { type: Boolean, default: true },
    isDelete: { type: Boolean, default: true },
    isClose: { type: Boolean, default: true },
    source: { type: Object as PropType<SourceEntity> | undefined },
    sourceTypes: { type: Object as PropType<Array<SourceTypeEntity>> },
    readOnly: { type: Boolean, default: false },
  },
  emits: [ 'close', 'delete', 'save' , 'update' ],
  computed: {
    title(): string {
      if(this.isDelete)
        return 'Удалить';
      if(this.isEdit)
        return this.source ? 'Редактировать' : 'Создать';
      return 'Источник'
    },
    localForm(): QForm {
      return this.$refs['local-form'] as QForm;
    }
  },
  watch: {
    source: {
      immediate: true,
      handler() {
        this.initEditItem();
      },
    },
  },
  data(): { sourceLocal: Partial<SourceEntity> } {
    return {
      sourceLocal: {}
    };
  },
  methods: {
    initEditItem() {
      this.sourceLocal = this.source ? this.source : {  };
    },
    reset() {
      this.localForm.reset();
    },
    save() {
      this.localForm.validate().then((success: boolean) => {
        if (success) {
          if(this.sourceLocal.id) {
            this.$emit('update', this.sourceLocal)
          }else{
            this.$emit('save', this.sourceLocal)
          }
        }
      })
    },
    deleteQuote() {
      this.$emit('delete', this.sourceLocal)
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
  <q-card bordered class="text-black q-pa-sm q-ma-sm" style="min-width: 640px">
    <q-card-section>
      <span class="text-h5">{{ title }}</span>
    </q-card-section>

    <q-card-section>
      <q-form ref="local-form">
        <q-input id="id" name="id" label="ID" type="text" v-model="sourceLocal.id" disable readonly />
        <q-select id="sourceType" name="sourceType" label="Source Type"
                  v-model="sourceLocal.typeId"
                  :options="sourceTypes"
                  :readonly="readOnly"
                  :option-value="opt => Object(opt) === opt && 'id' in opt ? opt.id : null"
                  :option-label="opt => Object(opt) === opt && 'name' in opt ? opt.name : ''"
                  dense map-options emit-value stack-label
                  :rules="[val => (!!val) || 'Field is required']"
        />
        <q-input id="sourceName" name="name" label="Source Name" type="text"
                 :readonly="readOnly" clear-icon="highlight_off" stack-label
                 clearable
                 v-model="sourceLocal.name"
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
