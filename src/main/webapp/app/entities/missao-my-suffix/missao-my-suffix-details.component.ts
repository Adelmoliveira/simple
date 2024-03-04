import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MissaoMySuffixService from './missao-my-suffix.service';
import { type IMissaoMySuffix } from '@/shared/model/missao-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MissaoMySuffixDetails',
  setup() {
    const missaoService = inject('missaoService', () => new MissaoMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const missao: Ref<IMissaoMySuffix> = ref({});

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

    return {
      alertService,
      missao,

      previousState,
      t$: useI18n().t,
    };
  },
});
