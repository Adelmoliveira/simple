import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CidadesMySuffixService from './cidades-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ICidadesMySuffix, CidadesMySuffix } from '@/shared/model/cidades-my-suffix.model';
import { DiariaLocalidadeEnum } from '@/shared/model/enumerations/diaria-localidade-enum.model';
import { LocalidadeEnum } from '@/shared/model/enumerations/localidade-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CidadesMySuffixUpdate',
  setup() {
    const cidadesService = inject('cidadesService', () => new CidadesMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cidades: Ref<ICidadesMySuffix> = ref(new CidadesMySuffix());
    const diariaLocalidadeEnumValues: Ref<string[]> = ref(Object.keys(DiariaLocalidadeEnum));
    const localidadeEnumValues: Ref<string[]> = ref(Object.keys(LocalidadeEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCidadesMySuffix = async cidadesId => {
      try {
        const res = await cidadesService().find(cidadesId);
        cidades.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cidadesId) {
      retrieveCidadesMySuffix(route.params.cidadesId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      cidade: {},
      valorLocalidade: {},
      missao: {},
    };
    const v$ = useVuelidate(validationRules, cidades as any);
    v$.value.$validate();

    return {
      cidadesService,
      alertService,
      cidades,
      previousState,
      diariaLocalidadeEnumValues,
      localidadeEnumValues,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.cidades.id) {
        this.cidadesService()
          .update(this.cidades)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('iceaApp.cidades.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.cidadesService()
          .create(this.cidades)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('iceaApp.cidades.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
