/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MissaoMySuffix from './missao-my-suffix.vue';
import MissaoMySuffixService from './missao-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type MissaoMySuffixComponentType = InstanceType<typeof MissaoMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MissaoMySuffix Management Component', () => {
    let missaoServiceStub: SinonStubbedInstance<MissaoMySuffixService>;
    let mountOptions: MountingOptions<MissaoMySuffixComponentType>['global'];

    beforeEach(() => {
      missaoServiceStub = sinon.createStubInstance<MissaoMySuffixService>(MissaoMySuffixService);
      missaoServiceStub.retrieve.resolves({ headers: {} });

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
          missaoService: () => missaoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        missaoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(MissaoMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(missaoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.missaos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(MissaoMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(missaoServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: MissaoMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MissaoMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        missaoServiceStub.retrieve.reset();
        missaoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        missaoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(missaoServiceStub.retrieve.called).toBeTruthy();
        expect(comp.missaos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(missaoServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        missaoServiceStub.retrieve.reset();
        missaoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(missaoServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.missaos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(missaoServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        missaoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeMissaoMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(missaoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(missaoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
