import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MissaoMySuffixService from './missao-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CidadesMySuffixService from '@/entities/cidades-my-suffix/cidades-my-suffix.service';
import { type ICidadesMySuffix } from '@/shared/model/cidades-my-suffix.model';
import { type IMissaoMySuffix, MissaoMySuffix } from '@/shared/model/missao-my-suffix.model';
import { Prioridade } from '@/shared/model/enumerations/prioridade.model';
import { StatusEnum } from '@/shared/model/enumerations/status-enum.model';
import { AcaoEnum } from '@/shared/model/enumerations/acao-enum.model';
import { OrcamentoEnum } from '@/shared/model/enumerations/orcamento-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MissaoMySuffixUpdate',
  setup() {
    const missaoService = inject('missaoService', () => new MissaoMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const missao: Ref<IMissaoMySuffix> = ref(new MissaoMySuffix());

    const cidadesService = inject('cidadesService', () => new CidadesMySuffixService());

    const cidades: Ref<ICidadesMySuffix[]> = ref([]);
    const prioridadeValues: Ref<string[]> = ref(Object.keys(Prioridade));
    const statusEnumValues: Ref<string[]> = ref(Object.keys(StatusEnum));
    const acaoEnumValues: Ref<string[]> = ref(Object.keys(AcaoEnum));
    const orcamentoEnumValues: Ref<string[]> = ref(Object.keys(OrcamentoEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMissaoMySuffix = async missaoId => {
      try {
        const res = await missaoService().find(missaoId);
        missao.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.missaoId) {
      retrieveMissaoMySuffix(route.params.missaoId);
    }

    const initRelationships = () => {
      cidadesService()
        .retrieve()
        .then(res => {
          cidades.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nomeMissao: {},
      prioridade: {},
      quantidadeDiaria: {},
      meiaDiaria: {},
      quantidadeEquipe: {},
      dataInicio: {},
      dataTermino: {},
      deslocamento: {},
      passagemAerea: {},
      status: {},
      osverificada: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      acao: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      valorTotalMissao: {},
      valorDiariasRealizadas: {},
      saldoDisponivel: {},
      municipio: {},
      diarias: {},
    };
    const v$ = useVuelidate(validationRules, missao as any);
    v$.value.$validate();

    return {
      missaoService,
      alertService,
      missao,
      previousState,
      prioridadeValues,
      statusEnumValues,
      acaoEnumValues,
      orcamentoEnumValues,
      isSaving,
      currentLanguage,
      cidades,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.missao.id) {
        this.missaoService()
          .update(this.missao)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('iceaApp.missao.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.missaoService()
          .create(this.missao)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('iceaApp.missao.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
