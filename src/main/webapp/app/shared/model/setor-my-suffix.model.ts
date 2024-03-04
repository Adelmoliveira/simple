import { type IServidorMySuffix } from '@/shared/model/servidor-my-suffix.model';

export interface ISetorMySuffix {
  id?: number;
  nome?: string;
  sigla?: string;
  servidor?: IServidorMySuffix | null;
}

export class SetorMySuffix implements ISetorMySuffix {
  constructor(
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public servidor?: IServidorMySuffix | null,
  ) {}
}
