import { Pageable, PageableEntity, SourceTypeEntity } from 'components/models';
import { AbstractApi } from 'src/api/AbstractApi';
import { AxiosInstance } from 'axios';

export class SourceTypeApi implements AbstractApi<SourceTypeEntity, string> {
  axiosInstance: AxiosInstance;

  public constructor(axiosInstance: AxiosInstance) {
    this.axiosInstance = axiosInstance;
  }

  create(data: Partial<SourceTypeEntity>): Promise<SourceTypeEntity> {
    return this.axiosInstance
      .put('/api/entity/source-type', { ...data })
      .then((res) => res.data as SourceTypeEntity);
  }

  delete(id: string): Promise<void> {
    return this.axiosInstance
      .delete(`/api/entity/source-type/${id}`)
      .then(() => {
        return;
      });
  }

  getAll(pageable: Pageable): Promise<PageableEntity<SourceTypeEntity>> {
    return this.axiosInstance
      .get('/api/entity/source-type', { params: pageable })
      .then((res) => res.data as PageableEntity<SourceTypeEntity>);
  }

  getOne(id: string): Promise<SourceTypeEntity> {
    return this.axiosInstance
      .get(`/api/entity/source-type/${id}`)
      .then((res) => res.data as SourceTypeEntity);
  }

  update(
    id: string,
    data: Partial<SourceTypeEntity>
  ): Promise<SourceTypeEntity> {
    return this.axiosInstance
      .post(`/api/entity/source-type/${id}`, data)
      .then((res) => res.data as SourceTypeEntity);
  }
}
