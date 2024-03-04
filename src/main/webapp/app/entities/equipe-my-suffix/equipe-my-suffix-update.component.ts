import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EquipeMySuffixService from './equipe-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IEquipeMySuffix, EquipeMySuffix } from '@/shared/model/equipe-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EquipeMySuffixUpdate',
  setup() {
    const equipeService = inject('equipeService', () => new EquipeMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const equipe: Ref<IEquipeMySuffix> = ref(new EquipeMySuffix());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEquipeMySuffix = async equipeId => {
      try {
        const res = await equipeService().find(equipeId);
        equipe.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.equipeId) {
      retrieveEquipeMySuffix(route.params.equipeId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nome: {},
      servidores: {},
    };
    const v$ = useVuelidate(validationRules, equipe as any);
    v$.value.$validate();

    return {
      equipeService,
      alertService,
      equipe,
      previousState,
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
      if (this.equipe.id) {
        this.equipeService()
          .update(this.equipe)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('iceaApp.equipe.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.equipeService()
          .create(this.equipe)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('iceaApp.equipe.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
