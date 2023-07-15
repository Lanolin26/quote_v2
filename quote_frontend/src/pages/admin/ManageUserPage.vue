<script lang="ts">
import { defineComponent } from 'vue';
import { QTableColumn } from 'quasar';
import { ICON_DEFAULT, UserEntity } from "components/models";
import UserCard from 'components/cardEdit/UserCard.vue';
import { errorNotify, infoNotify } from "src/utils/notifyUtils";

interface DataV {
  filter: string;
  pagination: {
    sortBy: 'desc' | 'asc';
    descending: boolean;
    page: number;
    rowsPerPage: number;
    rowsNumber: number;
  };
  columns: Array<QTableColumn>;
  loading: boolean;

  openDialog: boolean;
  editDialog: boolean;
  deleteDialog: boolean;
  editElementType: Partial<UserEntity> | undefined;

  userElements: Array<UserEntity>;
}

export default defineComponent({
  name: 'ManageUserPage',
  components: { UserCard },
  data(): DataV {
    return {
      filter: '',
      pagination: {
        sortBy: 'desc',
        descending: false,
        page: 1,
        rowsPerPage: 10,
        rowsNumber: 10,
      },
      columns: [
        {
          name: 'id',
          label: 'quote.fields.id',
          field: 'id',
          align: 'left',
          style: 'width: 23em',
          sortable: false,
        },
        {
          name: 'name',
          label: 'field.name',
          field: 'name',
          align: 'left',
          sortable: false,
        },
        {
          name: 'icon',
          label: 'field.icon',
          field: 'icon',
          align: 'center',
          sortable: false,
        },
        {
          name: 'actions',
          label: 'quote.edit.actions',
          align: 'left',
          field: '',
        },
      ],
      loading: true,

      openDialog: false,
      editDialog: false,
      deleteDialog: false,
      editElementType: undefined,

      userElements: [],
    };
  },
  methods: {
    ICON_DEFAULT() {
      return ICON_DEFAULT
    },
    onRequest(props: { pagination: { page: number; rowsPerPage: number } }) {
      this.$api.user
        .getAll({
          page: props.pagination.page - 1,
          size: props.pagination.rowsPerPage,
        })
        .then((res) => {
          this.userElements = res.content;
          this.pagination.rowsNumber = res.totalElements;
          this.pagination.rowsPerPage = props.pagination.rowsPerPage;
          this.pagination.page = res.number + 1;
        })
        .finally(() => {
          this.loading = false;
        });
    },
    closeAndClearDialog() {
      this.deleteDialog = false;
      this.editDialog = false;
      this.openDialog = false;
      this.editElementType = undefined;
    },
    editElement(element: UserEntity) {
      this.editDialog = true;
      this.openDialog = true;
      this.editElementType = element;
    },
    deleteElement(element: UserEntity) {
      this.editElementType = element;
      this.deleteDialog = true;
      this.openDialog = true;
    },
    reload() {
      this.onRequest({
        pagination: {
          page: this.pagination.page,
          rowsPerPage: this.pagination.rowsPerPage,
        },
      });
    },
    newItem() {
      this.editElementType = {};
      this.editDialog = true;
      this.openDialog = true;
    },
    // By API
    updateDialogElement(element: UserEntity) {
      console.log(element);
      let errorFunc = (err: string) =>
        this.$q.notify(
          errorNotify(`Some error when update source type: ${err}`)
        );
      let successFunc = (val: UserEntity) => {
        this.$q.notify(infoNotify(`Success to update source type: ${val.id}`));
        this.reload();
      };

      this.$api.user
          .update(element.id, element)
          .then(successFunc)
          .catch(errorFunc)
          .finally(this.closeAndClearDialog);
    },
    saveDialogElement(element: UserEntity) {
      console.log(element);
      let errorFunc = (err: string) =>
        this.$q.notify(
          errorNotify(`Some error when create source type: ${err}`)
        );
      let successFunc = (val: UserEntity) => {
        this.$q.notify(infoNotify(`Success to save source type: ${val.id}`));
        this.reload();
      };
      this.$api.user
          .create(element)
          .then(successFunc)
          .catch(errorFunc)
          .finally(this.closeAndClearDialog);
    },
    deleteDialogElement(element: UserEntity) {
      let errorFunc = (err: string) =>
        this.$q.notify(
          errorNotify(`Some error when delete source type: ${err}`)
        );
      let successFunc = () => {
        this.$q.notify(
          infoNotify(`Success to delete source type: ${element.id}`)
        );
        this.reload();
      };
      this.$api.user
          .delete(element.id)
          .then(successFunc)
          .catch(errorFunc)
          .finally(this.closeAndClearDialog);
    },
  },
  beforeMount() {
    this.reload();
  },
});
</script>

<template>
  <div>
    <q-table
      :loading="loading"
      :columns="columns"
      :rows="userElements"
      row-key="id"
      dense
      v-model:pagination="pagination"
      :filter="filter"
      @request="onRequest"
    >
      <template v-slot:top-left>
        <q-toolbar flat>
          <q-toolbar-title>Авторы</q-toolbar-title>
          <q-separator vertical inset />
          <q-btn
            class="q-ml-sm q-mr-sm"
            color="primary"
            square
            @click="reload"
            icon="update"
          />
          <q-btn
            color="primary"
            square
            @click="newItem"
            icon="add_circle_outline"
          />
        </q-toolbar>
      </template>
      <template v-slot:top-right>
        <q-input
          borderless
          dense
          debounce="300"
          v-model="filter"
          placeholder="Search"
        >
          <template v-slot:append>
            <q-icon name="search" />
          </template>
        </q-input>
      </template>
      <template v-slot:header-cell="props">
        <q-th :props="props">
          {{ props.col.label }}
        </q-th>
      </template>
      <template v-slot:body-cell-icon="props">
        <q-td :props="props">
          <q-avatar size="36px">
            <img :alt="props.row.name"
                 :src="`/api/avatar/${props.value ? props.row.id : ICON_DEFAULT()}`">
          </q-avatar>
        </q-td>
      </template>
      <template v-slot:body-cell-actions="props">
        <q-td :props="props">
          <q-btn
            flat
            round
            color="primary"
            icon="edit"
            size="xs"
            @click="editElement(props.row)"
          />
          <q-btn
            flat
            round
            color="primary"
            icon="delete"
            size="xs"
            @click="deleteElement(props.row)"
          />
        </q-td>
      </template>
      <template v-slot:pagination="scope">
        <q-btn
          v-if="scope.pagesNumber > 2"
          icon="first_page"
          color="grey-8"
          round
          dense
          flat
          :disable="scope.isFirstPage"
          @click="scope.firstPage"
        />
        <q-btn
          icon="chevron_left"
          color="grey-8"
          round
          dense
          flat
          :disable="scope.isFirstPage"
          @click="scope.prevPage"
        />
        <q-btn
          icon="chevron_right"
          color="grey-8"
          round
          dense
          flat
          :disable="scope.isLastPage"
          @click="scope.nextPage"
        />
        <q-btn
          v-if="scope.pagesNumber > 2"
          icon="last_page"
          color="grey-8"
          round
          dense
          flat
          :disable="scope.isLastPage"
          @click="scope.lastPage"
        />
      </template>
    </q-table>
    <q-dialog v-model="openDialog">
      <user-card
        :is-edit="editDialog"
        :is-delete="deleteDialog"
        :read-only="deleteDialog"
        is-close
        :user-entity="editElementType"
        @close="closeAndClearDialog"
        @save="saveDialogElement"
        @update="updateDialogElement"
        @delete="deleteDialogElement"
      />
    </q-dialog>
  </div>
</template>

<style scoped></style>
