/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MissaoMySuffixUpdate from './missao-my-suffix-update.vue';
import MissaoMySuffixService from './missao-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import CidadesMySuffixService from '@/entities/cidades-my-suffix/cidades-my-suffix.service';

type MissaoMySuffixUpdateComponentType = InstanceType<typeof MissaoMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const missaoSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MissaoMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MissaoMySuffix Management Update Component', () => {
    let comp: MissaoMySuffixUpdateComponentType;
    let missaoServiceStub: SinonStubbedInstance<MissaoMySuffixService>;

    beforeEach(() => {
      route = {};
      missaoServiceStub = sinon.createStubInstance<MissaoMySuffixService>(MissaoMySuffixService);
      missaoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          missaoService: () => missaoServiceStub,
          cidadesService: () =>
            sinon.createStubInstance<CidadesMySuffixService>(CidadesMySuffixService, {
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
        const wrapper = shallowMount(MissaoMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.missao = missaoSample;
        missaoServiceStub.update.resolves(missaoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(missaoServiceStub.update.calledWith(missaoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        missaoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MissaoMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.missao = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(missaoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        missaoServiceStub.find.resolves(missaoSample);
        missaoServiceStub.retrieve.resolves([missaoSample]);

        // WHEN
        route = {
          params: {
            missaoId: '' + missaoSample.id,
          },
        };
        const wrapper = shallowMount(MissaoMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.missao).toMatchObject(missaoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        missaoServiceStub.find.resolves(missaoSample);
        const wrapper = shallowMount(MissaoMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
