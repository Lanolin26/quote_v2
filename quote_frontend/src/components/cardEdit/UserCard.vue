<script lang="ts">
import { defineComponent, PropType } from "vue";
import { ICON_DEFAULT, UserEntity } from "components/models";
import { QForm } from "quasar";
import { QRejectedEntry } from "quasar/dist/types/api";

export default defineComponent({
  name: "UserCard",
  props: {
    isEdit: { type: Boolean, default: true },
    isDelete: { type: Boolean, default: true },
    isClose: { type: Boolean, default: true },
    userEntity: { type: Object as PropType<UserEntity> | undefined },
    readOnly: {type: Boolean, default: false}
  },
  emits: [ 'close', 'delete', 'save' , 'update' ],
  computed: {
    title(): string {
      if(this.isDelete)
        return 'Удалить';
      if(this.isEdit)
        return this.userEntity ? 'Редактировать' : 'Создать';
      return 'Пользователь'
    },
    localForm(): QForm {
      return this.$refs['local-form'] as QForm;
    }
  },
  watch: {
    userEntity: {
      immediate: true,
      handler() {
        this.initEditItem();
      },
    },
  },
  data(): { userEntityLocal: Partial<UserEntity> } {
    return {
      userEntityLocal: {},
    };
  },
  methods: {
    ICON_DEFAULT() {
      return ICON_DEFAULT
    },
    initEditItem() {
      this.userEntityLocal = this.userEntity ? this.userEntity : {  };
    },
    reset() {
      this.localForm.reset();
    },
    save() {
      this.userEntityLocal.icon = this.userEntityLocal.icon || !!this.userEntityLocal.iconFile;
      this.localForm.validate().then((success: boolean) => {
        if (success) {
          if(this.userEntityLocal.id) {
            this.$emit('update', this.userEntityLocal)
          }else{
            this.$emit('save', this.userEntityLocal)
          }
        }
      })

    },
    deleteQuote() {
      this.$emit('delete', this.userEntityLocal)
    },
    close() {
      this.$emit('close')
    },
    onRejected (rejectedEntries: QRejectedEntry[]) {
      this.$q.notify({
        type: 'negative',
        message: `${rejectedEntries.length} file(s) did not pass validation constraints`
      })
    }
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
        <q-input id="id" name="id" label="ID" type="text"
                 v-model="userEntityLocal.id" disable readonly />
        <q-input id="name" name="name" label="Name" type="text"
                 v-model="userEntityLocal.name" :readonly="readOnly"
                 clear-icon="highlight_off" stack-label clearable
                 :rules="[ value => value && value.length > 0 || 'Not empty' ]" />
        <q-file id="icon" name="icon"
                bottom-slots counter stack-label
                v-model="userEntityLocal.iconFile"
                label="Icon" accept=".jpg, image/*"
                max-file-size="2097152" max-files="1"
                @rejected="onRejected">
          <template v-slot:prepend>
            <q-avatar>
              <img :alt="userEntityLocal.id"
                   :src="`/api/avatar/${userEntityLocal.icon ? userEntityLocal.id : ICON_DEFAULT()}`">
            </q-avatar>
          </template>
          <template v-slot:append>
            <q-icon name="close" class="cursor-pointer"
                    @click.stop.prevent="userEntityLocal.iconFile = null" />
          </template>
        </q-file>
      </q-form>
    </q-card-section>

    <q-card-actions>
      <q-btn v-if="isEdit" label="Submit" type="submit" color="positive"  @click="save"  />
      <q-btn v-if="isDelete" label="Delete" type="reset" color="negative" @click="deleteQuote" />
      <q-btn v-if="isClose" label="Close" type="reset" color="primary" flat class="q-ml-sm" @click="close" />
    </q-card-actions>
  </q-card>
</template>

<style scoped>

</style>
