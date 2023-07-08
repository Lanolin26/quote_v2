<script lang="ts">
import { defineComponent } from 'vue';
import QuoteCardEdit from 'components/cardEdit/QuoteCardEdit.vue';
import { QTableColumn } from 'quasar';
import {
  QuoteEntity,
  SourceEntity,
  SourceTypeEntity,
  UserEntity,
} from 'components/models';
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
  editItem: Partial<QuoteEntity> | undefined;

  quoteEntities: Array<QuoteEntity>;
  sourceTypeEntities: Array<SourceTypeEntity>;
  sourceEntities: Array<SourceEntity>;
  authorEntities: Array<UserEntity>;
}

export default defineComponent({
  name: 'ManageQuotePage',
  components: { QuoteCardEdit },
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
          sortable: false,
        },
        {
          name: 'author',
          label: 'author',
          field: 'authorName',
          align: 'left',
          sortable: false,
        },
        {
          name: 'sourceType',
          label: 'quote.fields.sourceType',
          field: 'sourceTypeName',
          align: 'left',
          sortable: false,
        },
        {
          name: 'source',
          label: 'quote.fields.source',
          field: 'sourceName',
          align: 'left',
          sortable: false,
        },
        {
          name: 'text',
          label: 'quote.fields.text',
          field: 'text',
          align: 'left',
          sortable: false,
          format: (val: string) => `${val.length > 50 ? val.substring(0, 50)+'...' : val}`,
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
      editItem: undefined,

      quoteEntities: [],
      sourceTypeEntities: [],
      sourceEntities: [],
      authorEntities: [],
    };
  },
  watch: {
    openDialog: {
      handler() {
        if (this.openDialog) {
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
        }
      },
    },
  },
  methods: {
    onRequest(props: { pagination: { page: number; rowsPerPage: number } }) {
      this.$api.quote
        .getAll({
          page: props.pagination.page - 1,
          size: props.pagination.rowsPerPage,
        })
        .then((res) => {
          this.quoteEntities = res.content;
          this.pagination.rowsNumber = res.totalElements;
          this.pagination.rowsPerPage = res.numberOfElements;
          this.pagination.page = res.number + 1;
        })
        .finally(() => (this.loading = false));
    },
    closeAndClearDialog() {
      this.deleteDialog = false;
      this.editDialog = false;
      this.openDialog = false;
      this.editItem = undefined;
    },
    editElement(element: QuoteEntity) {
      this.editDialog = true;
      this.openDialog = true;
      this.editItem = element;
    },
    deleteElement(element: QuoteEntity) {
      this.editItem = element;
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
      this.editItem = undefined;
      this.editDialog = true;
      this.openDialog = true;
    },
    // By API
    updateDialogElement(element: QuoteEntity) {
      let errorFunc = (err: string) =>
        this.$q.notify(errorNotify(`Some error when update quote: ${err}`));
      let successFunc = (val: QuoteEntity) => {
        this.$q.notify(infoNotify(`Success to update quote: ${val.id}`));
        this.reload();
      };
      this.$api.quote
          .update(element.id, element)
          .then(successFunc)
          .catch(errorFunc)
          .finally(this.closeAndClearDialog);
    },
    saveDialogElement(element: QuoteEntity) {
      let errorFunc = (err: string) =>
        this.$q.notify(errorNotify(`Some error when create quote: ${err}`));
      let successFunc = (val: QuoteEntity) => {
        this.$q.notify(infoNotify(`Success to save quote: ${val.id}`));
        this.reload();
      };
      this.$api.quote
          .create(element)
          .then(successFunc)
          .catch(errorFunc)
          .finally(this.closeAndClearDialog);
    },
    deleteDialogElement(element: QuoteEntity) {
      let errorFunc = (err: string) =>
        this.$q.notify(errorNotify(`Some error when delete quote: ${err}`));
      let successFunc = () => {
        this.$q.notify(infoNotify(`Success to delete quote: ${element.id}`));
        this.reload();
      };
      this.$api.quote
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
      :rows="quoteEntities"
      row-key="id"
      dense
      v-model:pagination="pagination"
      :filter="filter"
      @request="onRequest"
    >
      <template v-slot:top-left>
        <q-toolbar flat>
          <q-toolbar-title>Цитаты</q-toolbar-title>
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

      <template v-slot:body-cell-source="props">
        <q-td :props="props">
          <q-chip size="sm">{{ props.value }}</q-chip>
        </q-td>
      </template>

      <template v-slot:body-cell-sourceType="props">
        <q-td :props="props">
          <q-chip size="sm">{{ props.value }}</q-chip>
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
      <quote-card-edit
        :is-edit="editDialog"
        :is-delete="deleteDialog"
        :read-only="deleteDialog"
        is-close
        :quote="editItem"
        :authors="authorEntities"
        :source-types="sourceTypeEntities"
        :sources="sourceEntities"
        @close="closeAndClearDialog"
        @save="saveDialogElement"
        @update="updateDialogElement"
        @delete="deleteDialogElement"
      />
    </q-dialog>
  </div>
</template>

<style scoped></style>
