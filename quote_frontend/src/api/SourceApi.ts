import { AbstractApi } from 'src/api/AbstractApi';
import { Pageable, PageableEntity, SourceEntity } from "components/models";
import { AxiosInstance } from 'axios';

export class SourceApi implements AbstractApi<SourceEntity, string> {
  axiosInstance: AxiosInstance;

  public constructor(axiosInstance: AxiosInstance) {
    this.axiosInstance = axiosInstance;
  }

  create(data: Partial<SourceEntity>): Promise<SourceEntity> {
    return this.axiosInstance
      .put('/api/entity/source', { ...data })
      .then((res) => res.data as SourceEntity);
  }

  delete(id: string): Promise<void> {
    return this.axiosInstance.delete(`/api/entity/source/${id}`).then(() => {
      return;
    });
  }

  getAll(pageable: Pageable): Promise<PageableEntity<SourceEntity>> {
    return this.axiosInstance
      .get('/api/entity/source', { params: pageable })
      .then((res) => res.data as PageableEntity<SourceEntity>);
  }

  getOne(id: string): Promise<SourceEntity> {
    return this.axiosInstance
      .get(`/api/entity/source/${id}`)
      .then((res) => res.data as SourceEntity);
  }

  update(
    id: string,
    data: Partial<SourceEntity>
  ): Promise<SourceEntity> {
    return this.axiosInstance
      .post(`/api/entity/source/${id}`, data)
      .then((res) => res.data as SourceEntity);
  }
}
