/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MissaoMySuffixDetails from './missao-my-suffix-details.vue';
import MissaoMySuffixService from './missao-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type MissaoMySuffixDetailsComponentType = InstanceType<typeof MissaoMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const missaoSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MissaoMySuffix Management Detail Component', () => {
    let missaoServiceStub: SinonStubbedInstance<MissaoMySuffixService>;
    let mountOptions: MountingOptions<MissaoMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      missaoServiceStub = sinon.createStubInstance<MissaoMySuffixService>(MissaoMySuffixService);

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
          missaoService: () => missaoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        missaoServiceStub.find.resolves(missaoSample);
        route = {
          params: {
            missaoId: '' + 123,
          },
        };
        const wrapper = shallowMount(MissaoMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.missao).toMatchObject(missaoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        missaoServiceStub.find.resolves(missaoSample);
        const wrapper = shallowMount(MissaoMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
