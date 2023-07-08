import { defineStore } from 'pinia';
import { Pageable, PageableEntity, SourceTypeEntity } from 'components/models';
import axios from 'axios';
import { Notify } from 'quasar';

interface StateSourceTypeStore {
  sourceTypeEntities: Array<SourceTypeEntity>;
  totalElements: number;
  elementByPage: number;
  page: number;
}

export const useSourceTypeStore = defineStore('source-type', {
  state: (): StateSourceTypeStore => ({
    sourceTypeEntities: [],
    totalElements: 0,
    elementByPage: 0,
    page: 1,
  }),

  actions: {
    getOneSourceTypeEntity(
      data: Partial<SourceTypeEntity>,
      success: (res: SourceTypeEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally save');
      }
    ) {
      axios
        .get(`/api/entity/source-type/${data.id}`)
        .then((res) => res.data as SourceTypeEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
    getSourceTypeEntity(pageable: Pageable, fin: () => void) {
      axios
        .get('/api/entity/source-type', { params: pageable })
        .then((res) => res.data as PageableEntity<SourceTypeEntity>)
        .then((res) => {
          this.sourceTypeEntities = res.content;
          this.totalElements = res.totalElements;
          this.elementByPage = res.numberOfElements;
          this.page = res.number + 1;
        })
        .catch((err) =>
          Notify.create({ message: 'Some error: ' + err, type: 'error' })
        )
        .finally(fin);
    },
    createSourceTypeEntity(
      data: Partial<SourceTypeEntity>,
      success: (res: SourceTypeEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally save');
      }
    ) {
      axios
        .put('/api/entity/source-type', { ...data })
        .then((res) => res.data as SourceTypeEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
    updateSourceTypeEntity(
      data: SourceTypeEntity,
      success: (res: SourceTypeEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally update');
      }
    ) {
      axios
        .post(`/api/entity/source-type/${data.id}`, data)
        .then((res) => res.data as SourceTypeEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
    deleteSourceTypeEntity(
      data: SourceTypeEntity,
      success: (res: SourceTypeEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally delete');
      }
    ) {
      axios
        .delete(`/api/entity/source-type/${data.id}`)
        .then((res) => res.data as SourceTypeEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
  },
});
