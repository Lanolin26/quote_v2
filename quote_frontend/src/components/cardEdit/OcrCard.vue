<script lang="ts">
import { defineComponent } from 'vue';
import { QForm } from 'quasar';
import { QRejectedEntry } from 'quasar/dist/types/api';
import { errorNotify } from 'src/utils/notifyUtils';
import { OcrLanguage } from "components/models";

type LanguageSelect = {
  label: string;
  value: OcrLanguage;
}

type DataV = {
  inputFile?: File;
  recognizedText: string;
  recognizeProcess: boolean;
  recognizeLang: OcrLanguage;
  recognizeLangs: LanguageSelect[];
};

export default defineComponent({
  name: 'OcrCard',
  props: {
    modelValue: { type: String },
  },
  emits: ['update:modelValue', 'close'],
  computed: {
    localForm(): QForm {
      return this.$refs['recognizeForm'] as QForm;
    },
  },
  data(): DataV {
    return {
      inputFile: undefined,
      recognizedText: '',
      recognizeProcess: false,
      recognizeLang: 'rus',
      recognizeLangs: [
        { label: 'Russian', value: "rus" },
        { label: 'English', value: "eng" }
      ]
    };
  },
  methods: {
    async recognize() {
      this.recognizeProcess = true;
      await this.$api.ocr
        .sendToRecognize(this.inputFile)
        .then((val) => (this.recognizedText = val))
        .catch((e) => this.$q.notify(errorNotify('Some error: ' + e)))
        .finally(() => (this.recognizeProcess = false));
    },
    saveRecognized() {
      this.$emit('update:modelValue', this.recognizedText);
      this.$emit('close');
    },
    recognizeAndSave() {
      this.recognize().then(this.saveRecognized);
    },
    onRejected(rejectedEntries: QRejectedEntry[]) {
      this.$q.notify({
        type: 'negative',
        message: `${rejectedEntries.length} file(s) did not pass validation constraints`,
      });
    },
  },
});
</script>

<template>
  <q-card bordered class="text-black q-pa-sm q-ma-sm" style="min-width: 640px">
    <q-card-section>
      <q-card-section>
        <span class="text-h5">Recognize Text</span>
      </q-card-section>
    </q-card-section>

    <q-card-section>
      <transition
        appear
        enter-active-class="animated fadeIn"
        leave-active-class="animated fadeOut"
      >
        <q-form ref="recognizeForm">
          <q-file
            outlined
            v-model="inputFile"
            clearable
            counter
            max-files="1"
            accept=".jpg, image/*"
            @rejected="onRejected"
            hint="Max file size (2k)"
            max-file-size="2048000"
          >
            <template v-slot:prepend>
              <q-icon name="attach_file" />
            </template>
          </q-file>
          <q-select v-model="recognizeLang"
                    :options="recognizeLangs"
                    label="Recognized Language"
                    emit-value
                    map-options />
          <q-input
            label="Recognized Text"
            type="textarea"
            outlined
            v-model="recognizedText"
            hint="Readonly"
            readonly
          />
        </q-form>
      </transition>
    </q-card-section>

    <q-inner-loading :showing="recognizeProcess">
      <q-spinner-gears size="50px" color="primary" />
    </q-inner-loading>

    <q-card-actions>
      <q-btn label="Recognize" @click="recognize" />
      <q-btn label="Save" @click="saveRecognized" />
      <q-btn label="Recognize & Save" @click="recognizeAndSave" />
      <q-btn label="Close" @click="$emit('close')" />
    </q-card-actions>
  </q-card>
</template>

<style scoped></style>
