import { type IServidorMySuffix } from '@/shared/model/servidor-my-suffix.model';

export interface IEquipeMySuffix {
  id?: number;
  nome?: string | null;
  servidores?: IServidorMySuffix[] | null;
}

export class EquipeMySuffix implements IEquipeMySuffix {
  constructor(
    public id?: number,
    public nome?: string | null,
    public servidores?: IServidorMySuffix[] | null,
  ) {}
}
