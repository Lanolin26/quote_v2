export const ICON_DEFAULT = '00000000-0000-0000-0000-000000000001';

export interface StatusMessage {
  status?: 'OK' | 'ERROR';
  error?: string;
  message?: string;
}

export interface AbstractEntity {
  id: string;
}

export interface AuthEntity extends AbstractEntity {
  login: string;
  email: string;
  password: string;
}

export interface QuoteEntity extends AbstractEntity {
  id: string;
  text: string;

  sourceId: string;
  sourceName: string;

  sourceTypeId: string;
  sourceTypeName: string;

  authorId: string;
  authorName: string;
  authorIcon: boolean;
}

export interface SourceEntity extends AbstractEntity {
  id: string;
  name: string;

  typeId: string;
  typeName: string;
}

export interface SourceTypeEntity extends AbstractEntity {
  id: string;
  name: string;
}

export interface UserEntity extends AbstractEntity {
  id: string;
  name: string;
  icon: boolean;
  iconFile?: File;
}

export type Pageable = {
  size: number;
  page: number;
};

export type PageableEntity<E> = {
  content: Array<E>;
  totalPages: number;
  numberOfElements: number;
  number: number;
  totalElements: number;
};
