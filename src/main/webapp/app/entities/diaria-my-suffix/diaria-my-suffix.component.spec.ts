/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import DiariaMySuffix from './diaria-my-suffix.vue';
import DiariaMySuffixService from './diaria-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type DiariaMySuffixComponentType = InstanceType<typeof DiariaMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('DiariaMySuffix Management Component', () => {
    let diariaServiceStub: SinonStubbedInstance<DiariaMySuffixService>;
    let mountOptions: MountingOptions<DiariaMySuffixComponentType>['global'];

    beforeEach(() => {
      diariaServiceStub = sinon.createStubInstance<DiariaMySuffixService>(DiariaMySuffixService);
      diariaServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          diariaService: () => diariaServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        diariaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(DiariaMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(diariaServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.diarias[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: DiariaMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(DiariaMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        diariaServiceStub.retrieve.reset();
        diariaServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        diariaServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeDiariaMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(diariaServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(diariaServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
