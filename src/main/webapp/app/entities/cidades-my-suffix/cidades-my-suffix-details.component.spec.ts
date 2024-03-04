/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CidadesMySuffixDetails from './cidades-my-suffix-details.vue';
import CidadesMySuffixService from './cidades-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type CidadesMySuffixDetailsComponentType = InstanceType<typeof CidadesMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cidadesSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CidadesMySuffix Management Detail Component', () => {
    let cidadesServiceStub: SinonStubbedInstance<CidadesMySuffixService>;
    let mountOptions: MountingOptions<CidadesMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      cidadesServiceStub = sinon.createStubInstance<CidadesMySuffixService>(CidadesMySuffixService);

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
          cidadesService: () => cidadesServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cidadesServiceStub.find.resolves(cidadesSample);
        route = {
          params: {
            cidadesId: '' + 123,
          },
        };
        const wrapper = shallowMount(CidadesMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.cidades).toMatchObject(cidadesSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cidadesServiceStub.find.resolves(cidadesSample);
        const wrapper = shallowMount(CidadesMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
