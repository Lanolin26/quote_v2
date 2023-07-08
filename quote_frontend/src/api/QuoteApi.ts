import { AbstractApi } from "src/api/AbstractApi";
import { Pageable, PageableEntity, QuoteEntity } from "components/models";
import { AxiosInstance } from "axios";

export class QuoteApi implements AbstractApi<QuoteEntity, string> {
  axiosInstance: AxiosInstance;

  public constructor(axiosInstance: AxiosInstance) {
    this.axiosInstance = axiosInstance;
  }

  create(data: Partial<QuoteEntity>): Promise<QuoteEntity> {
    return this.axiosInstance
               .put('/api/entity/quote', { ...data })
               .then((res) => res.data as QuoteEntity);
  }

  delete(id: string): Promise<void> {
    return this.axiosInstance.delete(`/api/entity/quote/${id}`).then(() => {
      return;
    });
  }

  getAll(pageable: Pageable): Promise<PageableEntity<QuoteEntity>> {
    return this.axiosInstance
               .get('/api/entity/quote', { params: pageable })
               .then((res) => res.data as PageableEntity<QuoteEntity>);
  }

  getOne(id: string): Promise<QuoteEntity> {
    return this.axiosInstance
               .get(`/api/entity/quote/${id}`)
               .then((res) => res.data as QuoteEntity);
  }

  update(id: string, data: Partial<QuoteEntity>): Promise<QuoteEntity> {
    return this.axiosInstance
               .post(`/api/entity/quote/${id}`, data)
               .then((res) => res.data as QuoteEntity);
  }
}
