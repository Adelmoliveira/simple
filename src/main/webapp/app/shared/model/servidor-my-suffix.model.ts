import { type IEquipeMySuffix } from '@/shared/model/equipe-my-suffix.model';
import { type ISetorMySuffix } from '@/shared/model/setor-my-suffix.model';

import { type PostoEnum } from '@/shared/model/enumerations/posto-enum.model';
export interface IServidorMySuffix {
  id?: number;
  fotoContentType?: string | null;
  foto?: string | null;
  nome?: string;
  sobreNome?: string | null;
  email?: string | null;
  celular?: string | null;
  posto?: keyof typeof PostoEnum;
  equipes?: IEquipeMySuffix[] | null;
  setor?: ISetorMySuffix | null;
}

export class ServidorMySuffix implements IServidorMySuffix {
  constructor(
    public id?: number,
    public fotoContentType?: string | null,
    public foto?: string | null,
    public nome?: string,
    public sobreNome?: string | null,
    public email?: string | null,
    public celular?: string | null,
    public posto?: keyof typeof PostoEnum,
    public equipes?: IEquipeMySuffix[] | null,
    public setor?: ISetorMySuffix | null,
  ) {}
}
