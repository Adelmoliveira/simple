import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import DiariaMySuffixService from './diaria-my-suffix.service';
import { type IDiariaMySuffix } from '@/shared/model/diaria-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DiariaMySuffixDetails',
  setup() {
    const diariaService = inject('diariaService', () => new DiariaMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const diaria: Ref<IDiariaMySuffix> = ref({});

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

    return {
      alertService,
      diaria,

      previousState,
      t$: useI18n().t,
    };
  },
});
