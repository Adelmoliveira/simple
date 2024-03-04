import { type IMissaoMySuffix } from '@/shared/model/missao-my-suffix.model';

import { type DiariaLocalidadeEnum } from '@/shared/model/enumerations/diaria-localidade-enum.model';
import { type LocalidadeEnum } from '@/shared/model/enumerations/localidade-enum.model';
export interface ICidadesMySuffix {
  id?: number;
  cidade?: keyof typeof DiariaLocalidadeEnum | null;
  valorLocalidade?: keyof typeof LocalidadeEnum | null;
  missao?: IMissaoMySuffix | null;
}

export class CidadesMySuffix implements ICidadesMySuffix {
  constructor(
    public id?: number,
    public cidade?: keyof typeof DiariaLocalidadeEnum | null,
    public valorLocalidade?: keyof typeof LocalidadeEnum | null,
    public missao?: IMissaoMySuffix | null,
  ) {}
}
