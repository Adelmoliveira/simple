/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import CidadesMySuffix from './cidades-my-suffix.vue';
import CidadesMySuffixService from './cidades-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type CidadesMySuffixComponentType = InstanceType<typeof CidadesMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('CidadesMySuffix Management Component', () => {
    let cidadesServiceStub: SinonStubbedInstance<CidadesMySuffixService>;
    let mountOptions: MountingOptions<CidadesMySuffixComponentType>['global'];

    beforeEach(() => {
      cidadesServiceStub = sinon.createStubInstance<CidadesMySuffixService>(CidadesMySuffixService);
      cidadesServiceStub.retrieve.resolves({ headers: {} });

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
          cidadesService: () => cidadesServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cidadesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(CidadesMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(cidadesServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.cidades[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: CidadesMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(CidadesMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        cidadesServiceStub.retrieve.reset();
        cidadesServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        cidadesServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeCidadesMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(cidadesServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(cidadesServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
