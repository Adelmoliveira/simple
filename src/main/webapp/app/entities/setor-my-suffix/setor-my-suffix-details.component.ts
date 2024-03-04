import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import SetorMySuffixService from './setor-my-suffix.service';
import { type ISetorMySuffix } from '@/shared/model/setor-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SetorMySuffixDetails',
  setup() {
    const setorService = inject('setorService', () => new SetorMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const setor: Ref<ISetorMySuffix> = ref({});

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

    return {
      alertService,
      setor,

      previousState,
      t$: useI18n().t,
    };
  },
});
