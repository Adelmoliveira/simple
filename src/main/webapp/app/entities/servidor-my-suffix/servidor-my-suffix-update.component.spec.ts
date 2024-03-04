/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ServidorMySuffixUpdate from './servidor-my-suffix-update.vue';
import ServidorMySuffixService from './servidor-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import EquipeMySuffixService from '@/entities/equipe-my-suffix/equipe-my-suffix.service';

type ServidorMySuffixUpdateComponentType = InstanceType<typeof ServidorMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const servidorSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ServidorMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ServidorMySuffix Management Update Component', () => {
    let comp: ServidorMySuffixUpdateComponentType;
    let servidorServiceStub: SinonStubbedInstance<ServidorMySuffixService>;

    beforeEach(() => {
      route = {};
      servidorServiceStub = sinon.createStubInstance<ServidorMySuffixService>(ServidorMySuffixService);
      servidorServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          servidorService: () => servidorServiceStub,
          equipeService: () =>
            sinon.createStubInstance<EquipeMySuffixService>(EquipeMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ServidorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.servidor = servidorSample;
        servidorServiceStub.update.resolves(servidorSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(servidorServiceStub.update.calledWith(servidorSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        servidorServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ServidorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.servidor = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(servidorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        servidorServiceStub.find.resolves(servidorSample);
        servidorServiceStub.retrieve.resolves([servidorSample]);

        // WHEN
        route = {
          params: {
            servidorId: '' + servidorSample.id,
          },
        };
        const wrapper = shallowMount(ServidorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.servidor).toMatchObject(servidorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        servidorServiceStub.find.resolves(servidorSample);
        const wrapper = shallowMount(ServidorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
