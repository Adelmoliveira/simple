/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EquipeMySuffixUpdate from './equipe-my-suffix-update.vue';
import EquipeMySuffixService from './equipe-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type EquipeMySuffixUpdateComponentType = InstanceType<typeof EquipeMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const equipeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EquipeMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('EquipeMySuffix Management Update Component', () => {
    let comp: EquipeMySuffixUpdateComponentType;
    let equipeServiceStub: SinonStubbedInstance<EquipeMySuffixService>;

    beforeEach(() => {
      route = {};
      equipeServiceStub = sinon.createStubInstance<EquipeMySuffixService>(EquipeMySuffixService);
      equipeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          equipeService: () => equipeServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EquipeMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.equipe = equipeSample;
        equipeServiceStub.update.resolves(equipeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.update.calledWith(equipeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        equipeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EquipeMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.equipe = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        equipeServiceStub.find.resolves(equipeSample);
        equipeServiceStub.retrieve.resolves([equipeSample]);

        // WHEN
        route = {
          params: {
            equipeId: '' + equipeSample.id,
          },
        };
        const wrapper = shallowMount(EquipeMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.equipe).toMatchObject(equipeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        equipeServiceStub.find.resolves(equipeSample);
        const wrapper = shallowMount(EquipeMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
