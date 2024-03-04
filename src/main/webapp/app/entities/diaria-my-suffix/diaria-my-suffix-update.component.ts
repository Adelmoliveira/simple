import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import DiariaMySuffixService from './diaria-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MissaoMySuffixService from '@/entities/missao-my-suffix/missao-my-suffix.service';
import { type IMissaoMySuffix } from '@/shared/model/missao-my-suffix.model';
import { type IDiariaMySuffix, DiariaMySuffix } from '@/shared/model/diaria-my-suffix.model';
import { DiariaLocalidadeEnum } from '@/shared/model/enumerations/diaria-localidade-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DiariaMySuffixUpdate',
  setup() {
    const diariaService = inject('diariaService', () => new DiariaMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const diaria: Ref<IDiariaMySuffix> = ref(new DiariaMySuffix());

    const missaoService = inject('missaoService', () => new MissaoMySuffixService());

    const missaos: Ref<IMissaoMySuffix[]> = ref([]);
    const diariaLocalidadeEnumValues: Ref<string[]> = ref(Object.keys(DiariaLocalidadeEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveDiariaMySuffix = async diariaId => {
      try {
        const res = await diariaService().find(diariaId);
        diaria.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.diariaId) {
      retrieveDiariaMySuffix(route.params.diariaId);
    }

    const initRelationships = () => {
      missaoService()
        .retrieve()
        .then(res => {
          missaos.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      cidade: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      oficialSup: {},
      oficial: {},
      graduado: {},
      praca: {},
      civil: {},
      localidade: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      missao: {},
    };
    const v$ = useVuelidate(validationRules, diaria as any);
    v$.value.$validate();

    return {
      diariaService,
      alertService,
      diaria,
      previousState,
      diariaLocalidadeEnumValues,
      isSaving,
      currentLanguage,
      missaos,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.diaria.id) {
        this.diariaService()
          .update(this.diaria)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('iceaApp.diaria.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.diariaService()
          .create(this.diaria)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('iceaApp.diaria.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
