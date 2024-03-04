import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import SetorMySuffixService from './setor-my-suffix.service';
import { type ISetorMySuffix } from '@/shared/model/setor-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SetorMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const setorService = inject('setorService', () => new SetorMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const setors: Ref<ISetorMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveSetorMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await setorService().retrieve();
        setors.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveSetorMySuffixs();
    };

    onMounted(async () => {
      await retrieveSetorMySuffixs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ISetorMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeSetorMySuffix = async () => {
      try {
        await setorService().delete(removeId.value);
        const message = t$('iceaApp.setor.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveSetorMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      setors,
      handleSyncList,
      isFetching,
      retrieveSetorMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeSetorMySuffix,
      t$,
    };
  },
});
