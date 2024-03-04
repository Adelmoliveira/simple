import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import DiariaMySuffixService from './diaria-my-suffix.service';
import { type IDiariaMySuffix } from '@/shared/model/diaria-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DiariaMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const diariaService = inject('diariaService', () => new DiariaMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const diarias: Ref<IDiariaMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveDiariaMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await diariaService().retrieve();
        diarias.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveDiariaMySuffixs();
    };

    onMounted(async () => {
      await retrieveDiariaMySuffixs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IDiariaMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeDiariaMySuffix = async () => {
      try {
        await diariaService().delete(removeId.value);
        const message = t$('iceaApp.diaria.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveDiariaMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      diarias,
      handleSyncList,
      isFetching,
      retrieveDiariaMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeDiariaMySuffix,
      t$,
    };
  },
});
