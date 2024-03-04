/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EquipeMySuffixDetails from './equipe-my-suffix-details.vue';
import EquipeMySuffixService from './equipe-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type EquipeMySuffixDetailsComponentType = InstanceType<typeof EquipeMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const equipeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('EquipeMySuffix Management Detail Component', () => {
    let equipeServiceStub: SinonStubbedInstance<EquipeMySuffixService>;
    let mountOptions: MountingOptions<EquipeMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      equipeServiceStub = sinon.createStubInstance<EquipeMySuffixService>(EquipeMySuffixService);

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
          equipeService: () => equipeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        equipeServiceStub.find.resolves(equipeSample);
        route = {
          params: {
            equipeId: '' + 123,
          },
        };
        const wrapper = shallowMount(EquipeMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.equipe).toMatchObject(equipeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        equipeServiceStub.find.resolves(equipeSample);
        const wrapper = shallowMount(EquipeMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
