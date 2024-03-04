import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ServidorMySuffixService from './servidor-my-suffix.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IServidorMySuffix } from '@/shared/model/servidor-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ServidorMySuffixDetails',
  setup() {
    const servidorService = inject('servidorService', () => new ServidorMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const servidor: Ref<IServidorMySuffix> = ref({});

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

    return {
      alertService,
      servidor,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
