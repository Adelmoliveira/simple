import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import SetorMySuffixService from './setor-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ServidorMySuffixService from '@/entities/servidor-my-suffix/servidor-my-suffix.service';
import { type IServidorMySuffix } from '@/shared/model/servidor-my-suffix.model';
import { type ISetorMySuffix, SetorMySuffix } from '@/shared/model/setor-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SetorMySuffixUpdate',
  setup() {
    const setorService = inject('setorService', () => new SetorMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const setor: Ref<ISetorMySuffix> = ref(new SetorMySuffix());

    const servidorService = inject('servidorService', () => new ServidorMySuffixService());

    const servidors: Ref<IServidorMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveSetorMySuffix = async setorId => {
      try {
        const res = await setorService().find(setorId);
        setor.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.setorId) {
      retrieveSetorMySuffix(route.params.setorId);
    }

    const initRelationships = () => {
      servidorService()
        .retrieve()
        .then(res => {
          servidors.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nome: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      sigla: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      servidor: {},
    };
    const v$ = useVuelidate(validationRules, setor as any);
    v$.value.$validate();

    return {
      setorService,
      alertService,
      setor,
      previousState,
      isSaving,
      currentLanguage,
      servidors,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.setor.id) {
        this.setorService()
          .update(this.setor)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('iceaApp.setor.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.setorService()
          .create(this.setor)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('iceaApp.setor.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
