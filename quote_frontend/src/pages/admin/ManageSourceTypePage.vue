<script lang="ts">
import { defineComponent } from 'vue';
import SourceTypeCard from 'components/cardEdit/SourceTypeCard.vue';
import { SourceTypeEntity } from 'components/models';
import { QTableColumn } from 'quasar';
import { errorNotify, infoNotify } from 'src/utils/notifyUtils';

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
  editElementType: Partial<SourceTypeEntity> | undefined;

  sourceTypeEntities: Array<SourceTypeEntity>;
}

export default defineComponent({
  name: 'ManageSourceTypePage',
  components: { SourceTypeCard },
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
          name: 'type',
          label: 'quote.fields.sourceType',
          field: 'name',
          align: 'left',
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

      sourceTypeEntities: [],
    };
  },
  methods: {
    onRequest(props: { pagination: { page: number; rowsPerPage: number } }) {
      this.$api.sourceType
        .getAll({
          page: props.pagination.page - 1,
          size: props.pagination.rowsPerPage,
        })
        .then((res) => {
          this.sourceTypeEntities = res.content;
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
    editElement(element: SourceTypeEntity) {
      this.editDialog = true;
      this.openDialog = true;
      this.editElementType = element;
    },
    deleteElement(element: SourceTypeEntity) {
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
    updateDialogElement(element: SourceTypeEntity) {
      let errorFunc = (err: string) =>
        this.$q.notify(
          errorNotify(`Some error when update source type: ${err}`)
        );
      let successFunc = (val: SourceTypeEntity) => {
        this.$q.notify(infoNotify(`Success to update source type: ${val.id}`));
        this.reload();
      };

      this.$api.sourceType
        .update(element.id, element)
        .then(successFunc)
        .catch(errorFunc)
        .finally(this.closeAndClearDialog);
    },
    saveDialogElement(element: SourceTypeEntity) {
      let errorFunc = (err: string) =>
        this.$q.notify(
          errorNotify(`Some error when create source type: ${err}`)
        );
      let successFunc = (val: SourceTypeEntity) => {
        this.$q.notify(infoNotify(`Success to save source type: ${val.id}`));
        this.reload();
      };
      this.$api.sourceType
        .create(element)
        .then(successFunc)
        .catch(errorFunc)
        .finally(this.closeAndClearDialog);
    },
    deleteDialogElement(element: SourceTypeEntity) {
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
      this.$api.sourceType
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
      :rows="sourceTypeEntities"
      row-key="id"
      dense
      v-model:pagination="pagination"
      :filter="filter"
      @request="onRequest"
    >
      <template v-slot:top-left>
        <q-toolbar flat>
          <q-toolbar-title>Типы источников</q-toolbar-title>
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
      <source-type-card
        :is-edit="editDialog"
        :is-delete="deleteDialog"
        :read-only="deleteDialog"
        is-close
        :source-type="editElementType"
        @close="closeAndClearDialog"
        @save="saveDialogElement"
        @update="updateDialogElement"
        @delete="deleteDialogElement"
      />
    </q-dialog>
  </div>
</template>

<style scoped></style>
