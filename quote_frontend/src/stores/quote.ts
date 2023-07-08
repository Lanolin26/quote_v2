import {defineStore} from 'pinia';
import {Pageable, PageableEntity, QuoteEntity} from 'components/models';
import {Notify} from 'quasar';
import axios from 'axios';

interface StateQuoteStore {
  quoteEntity: Array<QuoteEntity>,
  totalPage: number,
  elementByPage: number,
  page: number,
}

export const useQuoteStore = defineStore('quote', {
  state: (): StateQuoteStore => ({
    quoteEntity: [],
    totalPage: 0,
    elementByPage: 0,
    page: 0,
  }),

  getters: {},

  actions: {
    getQuoteEntity(pageable: Pageable, fin: () => void) {
      axios.get('/api/entity/quote', {params: pageable})
        .then(res => res.data as PageableEntity<QuoteEntity>)
        .then(res => {
          this.quoteEntity = res.content;
          this.totalPage = res.totalPages;
          this.elementByPage = res.numberOfElements;
          this.page = res.number + 1;
        })
        .catch(err => Notify.create({message: 'Some error: ' + err, type: 'error'}))
        .finally(fin)
    }
  }
});
