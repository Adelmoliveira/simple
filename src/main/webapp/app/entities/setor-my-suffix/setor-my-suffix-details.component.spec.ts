/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SetorMySuffixDetails from './setor-my-suffix-details.vue';
import SetorMySuffixService from './setor-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type SetorMySuffixDetailsComponentType = InstanceType<typeof SetorMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const setorSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('SetorMySuffix Management Detail Component', () => {
    let setorServiceStub: SinonStubbedInstance<SetorMySuffixService>;
    let mountOptions: MountingOptions<SetorMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      setorServiceStub = sinon.createStubInstance<SetorMySuffixService>(SetorMySuffixService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          setorService: () => setorServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        setorServiceStub.find.resolves(setorSample);
        route = {
          params: {
            setorId: '' + 123,
          },
        };
        const wrapper = shallowMount(SetorMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.setor).toMatchObject(setorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        setorServiceStub.find.resolves(setorSample);
        const wrapper = shallowMount(SetorMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
