import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import CidadesMySuffixService from './cidades-my-suffix.service';
import { type ICidadesMySuffix } from '@/shared/model/cidades-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CidadesMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const cidadesService = inject('cidadesService', () => new CidadesMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cidades: Ref<ICidadesMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveCidadesMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await cidadesService().retrieve();
        cidades.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveCidadesMySuffixs();
    };

    onMounted(async () => {
      await retrieveCidadesMySuffixs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ICidadesMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeCidadesMySuffix = async () => {
      try {
        await cidadesService().delete(removeId.value);
        const message = t$('iceaApp.cidades.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveCidadesMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      cidades,
      handleSyncList,
      isFetching,
      retrieveCidadesMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeCidadesMySuffix,
      t$,
    };
  },
});
