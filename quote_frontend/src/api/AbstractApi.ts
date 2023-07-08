import { Pageable, PageableEntity } from "components/models";
import { AuthApi } from "src/api/AuthApi";
import { QuoteApi } from "src/api/QuoteApi";
import { SourceApi } from "src/api/SourceApi";
import { SourceTypeApi } from "src/api/SourceTypeApi";
import { UserApi } from "src/api/UserApi";

export interface Apis {
  auth: AuthApi;
  quote: QuoteApi;
  source: SourceApi;
  sourceType: SourceTypeApi;
  user: UserApi;
}

export interface AbstractApi<T, ID> {
  getAll: (pageable: Pageable) => Promise<PageableEntity<T>>;
  getOne: (id: ID) => Promise<T>;
  create: (data: Partial<T>) => Promise<T>;
  update: (id: ID, data: Partial<T>) => Promise<T>;
  delete: (id: ID) => Promise<void>;
}
