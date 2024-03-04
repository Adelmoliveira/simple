/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import EquipeMySuffix from './equipe-my-suffix.vue';
import EquipeMySuffixService from './equipe-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type EquipeMySuffixComponentType = InstanceType<typeof EquipeMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('EquipeMySuffix Management Component', () => {
    let equipeServiceStub: SinonStubbedInstance<EquipeMySuffixService>;
    let mountOptions: MountingOptions<EquipeMySuffixComponentType>['global'];

    beforeEach(() => {
      equipeServiceStub = sinon.createStubInstance<EquipeMySuffixService>(EquipeMySuffixService);
      equipeServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          equipeService: () => equipeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        equipeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(EquipeMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.equipes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(EquipeMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: EquipeMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(EquipeMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        equipeServiceStub.retrieve.reset();
        equipeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        equipeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.retrieve.called).toBeTruthy();
        expect(comp.equipes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        equipeServiceStub.retrieve.reset();
        equipeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(equipeServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.equipes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        equipeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeEquipeMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(equipeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(equipeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
