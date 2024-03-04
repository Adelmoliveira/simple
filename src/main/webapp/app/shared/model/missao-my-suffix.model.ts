import { type ICidadesMySuffix } from '@/shared/model/cidades-my-suffix.model';
import { type IDiariaMySuffix } from '@/shared/model/diaria-my-suffix.model';

import { type Prioridade } from '@/shared/model/enumerations/prioridade.model';
import { type StatusEnum } from '@/shared/model/enumerations/status-enum.model';
import { type AcaoEnum } from '@/shared/model/enumerations/acao-enum.model';
import { type OrcamentoEnum } from '@/shared/model/enumerations/orcamento-enum.model';
export interface IMissaoMySuffix {
  id?: number;
  nomeMissao?: string | null;
  prioridade?: keyof typeof Prioridade | null;
  quantidadeDiaria?: number | null;
  meiaDiaria?: boolean | null;
  quantidadeEquipe?: number | null;
  dataInicio?: Date | null;
  dataTermino?: Date | null;
  deslocamento?: boolean | null;
  passagemAerea?: boolean | null;
  status?: keyof typeof StatusEnum | null;
  osverificada?: boolean;
  acao?: keyof typeof AcaoEnum;
  valorTotalMissao?: keyof typeof OrcamentoEnum | null;
  valorDiariasRealizadas?: number | null;
  saldoDisponivel?: number | null;
  municipio?: ICidadesMySuffix | null;
  diarias?: IDiariaMySuffix[] | null;
}

export class MissaoMySuffix implements IMissaoMySuffix {
  constructor(
    public id?: number,
    public nomeMissao?: string | null,
    public prioridade?: keyof typeof Prioridade | null,
    public quantidadeDiaria?: number | null,
    public meiaDiaria?: boolean | null,
    public quantidadeEquipe?: number | null,
    public dataInicio?: Date | null,
    public dataTermino?: Date | null,
    public deslocamento?: boolean | null,
    public passagemAerea?: boolean | null,
    public status?: keyof typeof StatusEnum | null,
    public osverificada?: boolean,
    public acao?: keyof typeof AcaoEnum,
    public valorTotalMissao?: keyof typeof OrcamentoEnum | null,
    public valorDiariasRealizadas?: number | null,
    public saldoDisponivel?: number | null,
    public municipio?: ICidadesMySuffix | null,
    public diarias?: IDiariaMySuffix[] | null,
  ) {
    this.meiaDiaria = this.meiaDiaria ?? false;
    this.deslocamento = this.deslocamento ?? false;
    this.passagemAerea = this.passagemAerea ?? false;
    this.osverificada = this.osverificada ?? false;
  }
}
