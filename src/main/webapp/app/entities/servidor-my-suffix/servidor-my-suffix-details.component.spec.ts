/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ServidorMySuffixDetails from './servidor-my-suffix-details.vue';
import ServidorMySuffixService from './servidor-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type ServidorMySuffixDetailsComponentType = InstanceType<typeof ServidorMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const servidorSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ServidorMySuffix Management Detail Component', () => {
    let servidorServiceStub: SinonStubbedInstance<ServidorMySuffixService>;
    let mountOptions: MountingOptions<ServidorMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      servidorServiceStub = sinon.createStubInstance<ServidorMySuffixService>(ServidorMySuffixService);

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
          servidorService: () => servidorServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        servidorServiceStub.find.resolves(servidorSample);
        route = {
          params: {
            servidorId: '' + 123,
          },
        };
        const wrapper = shallowMount(ServidorMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.servidor).toMatchObject(servidorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        servidorServiceStub.find.resolves(servidorSample);
        const wrapper = shallowMount(ServidorMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
