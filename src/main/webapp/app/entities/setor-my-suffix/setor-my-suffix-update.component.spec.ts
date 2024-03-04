/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SetorMySuffixUpdate from './setor-my-suffix-update.vue';
import SetorMySuffixService from './setor-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import ServidorMySuffixService from '@/entities/servidor-my-suffix/servidor-my-suffix.service';

type SetorMySuffixUpdateComponentType = InstanceType<typeof SetorMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const setorSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<SetorMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('SetorMySuffix Management Update Component', () => {
    let comp: SetorMySuffixUpdateComponentType;
    let setorServiceStub: SinonStubbedInstance<SetorMySuffixService>;

    beforeEach(() => {
      route = {};
      setorServiceStub = sinon.createStubInstance<SetorMySuffixService>(SetorMySuffixService);
      setorServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          setorService: () => setorServiceStub,
          servidorService: () =>
            sinon.createStubInstance<ServidorMySuffixService>(ServidorMySuffixService, {
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
        const wrapper = shallowMount(SetorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.setor = setorSample;
        setorServiceStub.update.resolves(setorSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(setorServiceStub.update.calledWith(setorSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        setorServiceStub.create.resolves(entity);
        const wrapper = shallowMount(SetorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.setor = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(setorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        setorServiceStub.find.resolves(setorSample);
        setorServiceStub.retrieve.resolves([setorSample]);

        // WHEN
        route = {
          params: {
            setorId: '' + setorSample.id,
          },
        };
        const wrapper = shallowMount(SetorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.setor).toMatchObject(setorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        setorServiceStub.find.resolves(setorSample);
        const wrapper = shallowMount(SetorMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
