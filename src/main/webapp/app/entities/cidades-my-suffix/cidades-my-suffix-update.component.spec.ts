/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CidadesMySuffixUpdate from './cidades-my-suffix-update.vue';
import CidadesMySuffixService from './cidades-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type CidadesMySuffixUpdateComponentType = InstanceType<typeof CidadesMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cidadesSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CidadesMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CidadesMySuffix Management Update Component', () => {
    let comp: CidadesMySuffixUpdateComponentType;
    let cidadesServiceStub: SinonStubbedInstance<CidadesMySuffixService>;

    beforeEach(() => {
      route = {};
      cidadesServiceStub = sinon.createStubInstance<CidadesMySuffixService>(CidadesMySuffixService);
      cidadesServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          cidadesService: () => cidadesServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CidadesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cidades = cidadesSample;
        cidadesServiceStub.update.resolves(cidadesSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cidadesServiceStub.update.calledWith(cidadesSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        cidadesServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CidadesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cidades = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cidadesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        cidadesServiceStub.find.resolves(cidadesSample);
        cidadesServiceStub.retrieve.resolves([cidadesSample]);

        // WHEN
        route = {
          params: {
            cidadesId: '' + cidadesSample.id,
          },
        };
        const wrapper = shallowMount(CidadesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.cidades).toMatchObject(cidadesSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cidadesServiceStub.find.resolves(cidadesSample);
        const wrapper = shallowMount(CidadesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
