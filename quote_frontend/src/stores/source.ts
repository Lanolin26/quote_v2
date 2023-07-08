import { defineStore } from "pinia";
import { Pageable, PageableEntity, SourceEntity } from "components/models";
import axios from "axios";
import { Notify } from "quasar";

interface StateSourceStore {
  sourceEntity: Array<SourceEntity>;
  totalElements: number;
  elementByPage: number;
  page: number;
}

export const useSourceStore = defineStore("source", {
  state: (): StateSourceStore => ({
    sourceEntity: [],
    totalElements: 0,
    elementByPage: 0,
    page: 1,
  }),
  actions: {
    getOneSourceEntity(
      data: Partial<SourceEntity>,
      success: (res: SourceEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally save');
      }
    ) {
      axios
        .get(`/api/entity/source/${data.id}`)
        .then((res) => res.data as SourceEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
    getSourceEntity(pageable: Pageable, fin: () => void) {
      axios
        .get('/api/entity/source', { params: pageable })
        .then((res) => res.data as PageableEntity<SourceEntity>)
        .then((res) => {
          this.sourceEntity = res.content;
          this.totalElements = res.totalElements;
          this.elementByPage = res.numberOfElements;
          this.page = res.number + 1;
        })
        .catch((err) =>
          Notify.create({ message: 'Some error: ' + err, type: 'error' })
        )
        .finally(fin);
    },
    createSourceEntity(
      data: Partial<SourceEntity>,
      success: (res: SourceEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally save');
      }
    ) {
      axios
        .put('/api/entity/source', {...data})
        .then((res) => res.data as SourceEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
    updateSourceEntity(
      data: SourceEntity,
      success: (res: SourceEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally update');
      }
    ) {
      axios
        .post(`/api/entity/source/${data.id}`, data)
        .then((res) => res.data as SourceEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
    deleteSourceEntity(
      data: SourceEntity,
      success: (res: SourceEntity) => void,
      error: (err: string) => void,
      fin: () => void = () => {
        console.trace('Finally delete');
      }
    ) {
      axios
        .delete(`/api/entity/source/${data.id}`)
        .then((res) => res.data as SourceEntity)
        .then(success)
        .catch(error)
        .finally(fin);
    },
  },
})
