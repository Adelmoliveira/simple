import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EquipeMySuffixService from './equipe-my-suffix.service';
import { type IEquipeMySuffix } from '@/shared/model/equipe-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EquipeMySuffixDetails',
  setup() {
    const equipeService = inject('equipeService', () => new EquipeMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const equipe: Ref<IEquipeMySuffix> = ref({});

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

    return {
      alertService,
      equipe,

      previousState,
      t$: useI18n().t,
    };
  },
});
