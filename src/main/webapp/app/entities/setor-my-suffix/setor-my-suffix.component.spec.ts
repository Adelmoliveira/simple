/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import SetorMySuffix from './setor-my-suffix.vue';
import SetorMySuffixService from './setor-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type SetorMySuffixComponentType = InstanceType<typeof SetorMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('SetorMySuffix Management Component', () => {
    let setorServiceStub: SinonStubbedInstance<SetorMySuffixService>;
    let mountOptions: MountingOptions<SetorMySuffixComponentType>['global'];

    beforeEach(() => {
      setorServiceStub = sinon.createStubInstance<SetorMySuffixService>(SetorMySuffixService);
      setorServiceStub.retrieve.resolves({ headers: {} });

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
          setorService: () => setorServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        setorServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(SetorMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(setorServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.setors[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: SetorMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(SetorMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        setorServiceStub.retrieve.reset();
        setorServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        setorServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeSetorMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(setorServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(setorServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
