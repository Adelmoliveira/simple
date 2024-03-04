import { type IMissaoMySuffix } from '@/shared/model/missao-my-suffix.model';

import { type DiariaLocalidadeEnum } from '@/shared/model/enumerations/diaria-localidade-enum.model';
export interface IDiariaMySuffix {
  id?: number;
  cidade?: string;
  oficialSup?: number | null;
  oficial?: number | null;
  graduado?: number | null;
  praca?: number | null;
  civil?: number | null;
  localidade?: keyof typeof DiariaLocalidadeEnum;
  missao?: IMissaoMySuffix | null;
}

export class DiariaMySuffix implements IDiariaMySuffix {
  constructor(
    public id?: number,
    public cidade?: string,
    public oficialSup?: number | null,
    public oficial?: number | null,
    public graduado?: number | null,
    public praca?: number | null,
    public civil?: number | null,
    public localidade?: keyof typeof DiariaLocalidadeEnum,
    public missao?: IMissaoMySuffix | null,
  ) {}
}
