import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ServidorMySuffixService from './servidor-my-suffix.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EquipeMySuffixService from '@/entities/equipe-my-suffix/equipe-my-suffix.service';
import { type IEquipeMySuffix } from '@/shared/model/equipe-my-suffix.model';
import { type IServidorMySuffix, ServidorMySuffix } from '@/shared/model/servidor-my-suffix.model';
import { PostoEnum } from '@/shared/model/enumerations/posto-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ServidorMySuffixUpdate',
  setup() {
    const servidorService = inject('servidorService', () => new ServidorMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const servidor: Ref<IServidorMySuffix> = ref(new ServidorMySuffix());

    const equipeService = inject('equipeService', () => new EquipeMySuffixService());

    const equipes: Ref<IEquipeMySuffix[]> = ref([]);
    const postoEnumValues: Ref<string[]> = ref(Object.keys(PostoEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveServidorMySuffix = async servidorId => {
      try {
        const res = await servidorService().find(servidorId);
        servidor.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.servidorId) {
      retrieveServidorMySuffix(route.params.servidorId);
    }

    const initRelationships = () => {
      equipeService()
        .retrieve()
        .then(res => {
          equipes.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      foto: {},
      nome: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      sobreNome: {},
      email: {},
      celular: {},
      posto: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      equipes: {},
      setor: {},
    };
    const v$ = useVuelidate(validationRules, servidor as any);
    v$.value.$validate();

    return {
      servidorService,
      alertService,
      servidor,
      previousState,
      postoEnumValues,
      isSaving,
      currentLanguage,
      equipes,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {
    this.servidor.equipes = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.servidor.id) {
        this.servidorService()
          .update(this.servidor)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('iceaApp.servidor.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.servidorService()
          .create(this.servidor)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('iceaApp.servidor.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option): any {
      if (selectedVals) {
        return selectedVals.find(value => option.id === value.id) ?? option;
      }
      return option;
    },
  },
});
