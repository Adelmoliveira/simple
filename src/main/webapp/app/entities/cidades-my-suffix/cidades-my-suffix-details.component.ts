import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CidadesMySuffixService from './cidades-my-suffix.service';
import { type ICidadesMySuffix } from '@/shared/model/cidades-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CidadesMySuffixDetails',
  setup() {
    const cidadesService = inject('cidadesService', () => new CidadesMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const cidades: Ref<ICidadesMySuffix> = ref({});

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

    return {
      alertService,
      cidades,

      previousState,
      t$: useI18n().t,
    };
  },
});
