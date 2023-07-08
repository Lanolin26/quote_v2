import { AbstractApi } from 'src/api/AbstractApi';
import {
  Pageable,
  PageableEntity,
  StatusMessage,
  UserEntity,
} from 'components/models';
import { AxiosInstance } from 'axios';
import { Notify } from 'quasar';
import { errorNotify } from 'src/utils/notifyUtils';

export class UserApi implements AbstractApi<UserEntity, string> {
  axiosInstance: AxiosInstance;

  public constructor(axiosInstance: AxiosInstance) {
    this.axiosInstance = axiosInstance;
  }

  uploadIcon(id: string, data: Partial<UserEntity>): Promise<StatusMessage> {
    if (data.iconFile) {
      const formData = new FormData();
      formData.append('image', data.iconFile);
      return this.axiosInstance
        .put(`/api/avatar/${id}`, formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        })
        .then((data) => data.data as StatusMessage);
    } else {
      return Promise.resolve({} as StatusMessage);
    }
  }

  create(data: Partial<UserEntity>): Promise<UserEntity> {
    return this.axiosInstance
      .put('/api/entity/user', { name: data.name, icon: data.icon })
      .then((res) => res.data as UserEntity)
      .then((res) => {
        this.uploadIcon(res.id, data).catch((e) =>
          Notify.create(errorNotify('Some error when upload img: ' + e))
        );
        return res;
      });
  }

  delete(id: string): Promise<void> {
    return this.axiosInstance.delete(`/api/entity/user/${id}`).then(() => {
      return;
    });
  }

  getAll(pageable: Pageable): Promise<PageableEntity<UserEntity>> {
    return this.axiosInstance
      .get('/api/entity/user', { params: pageable })
      .then((res) => res.data as PageableEntity<UserEntity>);
  }

  getOne(id: string): Promise<UserEntity> {
    return this.axiosInstance
      .get(`/api/entity/user/${id}`)
      .then((res) => res.data as UserEntity);
  }

  update(id: string, data: Partial<UserEntity>): Promise<UserEntity> {
    const userEntityPromise = this.axiosInstance
      .post(`/api/entity/user/${id}`, { id: data.id, name: data.name, icon: data.icon })
      .then((res) => res.data as UserEntity);

    const promiseIcon = this.uploadIcon(id, data);

    return Promise.all([userEntityPromise, promiseIcon]).then(
      ([user]) => user
    );
  }
}
