/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DiariaMySuffixUpdate from './diaria-my-suffix-update.vue';
import DiariaMySuffixService from './diaria-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import MissaoMySuffixService from '@/entities/missao-my-suffix/missao-my-suffix.service';

type DiariaMySuffixUpdateComponentType = InstanceType<typeof DiariaMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const diariaSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<DiariaMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('DiariaMySuffix Management Update Component', () => {
    let comp: DiariaMySuffixUpdateComponentType;
    let diariaServiceStub: SinonStubbedInstance<DiariaMySuffixService>;

    beforeEach(() => {
      route = {};
      diariaServiceStub = sinon.createStubInstance<DiariaMySuffixService>(DiariaMySuffixService);
      diariaServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          diariaService: () => diariaServiceStub,
          missaoService: () =>
            sinon.createStubInstance<MissaoMySuffixService>(MissaoMySuffixService, {
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
        const wrapper = shallowMount(DiariaMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.diaria = diariaSample;
        diariaServiceStub.update.resolves(diariaSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(diariaServiceStub.update.calledWith(diariaSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        diariaServiceStub.create.resolves(entity);
        const wrapper = shallowMount(DiariaMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.diaria = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(diariaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        diariaServiceStub.find.resolves(diariaSample);
        diariaServiceStub.retrieve.resolves([diariaSample]);

        // WHEN
        route = {
          params: {
            diariaId: '' + diariaSample.id,
          },
        };
        const wrapper = shallowMount(DiariaMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.diaria).toMatchObject(diariaSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        diariaServiceStub.find.resolves(diariaSample);
        const wrapper = shallowMount(DiariaMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
