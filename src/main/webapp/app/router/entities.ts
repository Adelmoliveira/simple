import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const SetorMySuffix = () => import('@/entities/setor-my-suffix/setor-my-suffix.vue');
const SetorMySuffixUpdate = () => import('@/entities/setor-my-suffix/setor-my-suffix-update.vue');
const SetorMySuffixDetails = () => import('@/entities/setor-my-suffix/setor-my-suffix-details.vue');

const DiariaMySuffix = () => import('@/entities/diaria-my-suffix/diaria-my-suffix.vue');
const DiariaMySuffixUpdate = () => import('@/entities/diaria-my-suffix/diaria-my-suffix-update.vue');
const DiariaMySuffixDetails = () => import('@/entities/diaria-my-suffix/diaria-my-suffix-details.vue');

const CidadesMySuffix = () => import('@/entities/cidades-my-suffix/cidades-my-suffix.vue');
const CidadesMySuffixUpdate = () => import('@/entities/cidades-my-suffix/cidades-my-suffix-update.vue');
const CidadesMySuffixDetails = () => import('@/entities/cidades-my-suffix/cidades-my-suffix-details.vue');

const MissaoMySuffix = () => import('@/entities/missao-my-suffix/missao-my-suffix.vue');
const MissaoMySuffixUpdate = () => import('@/entities/missao-my-suffix/missao-my-suffix-update.vue');
const MissaoMySuffixDetails = () => import('@/entities/missao-my-suffix/missao-my-suffix-details.vue');

const ServidorMySuffix = () => import('@/entities/servidor-my-suffix/servidor-my-suffix.vue');
const ServidorMySuffixUpdate = () => import('@/entities/servidor-my-suffix/servidor-my-suffix-update.vue');
const ServidorMySuffixDetails = () => import('@/entities/servidor-my-suffix/servidor-my-suffix-details.vue');

const EquipeMySuffix = () => import('@/entities/equipe-my-suffix/equipe-my-suffix.vue');
const EquipeMySuffixUpdate = () => import('@/entities/equipe-my-suffix/equipe-my-suffix-update.vue');
const EquipeMySuffixDetails = () => import('@/entities/equipe-my-suffix/equipe-my-suffix-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'setor-my-suffix',
      name: 'SetorMySuffix',
      component: SetorMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'setor-my-suffix/new',
      name: 'SetorMySuffixCreate',
      component: SetorMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'setor-my-suffix/:setorId/edit',
      name: 'SetorMySuffixEdit',
      component: SetorMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'setor-my-suffix/:setorId/view',
      name: 'SetorMySuffixView',
      component: SetorMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'diaria-my-suffix',
      name: 'DiariaMySuffix',
      component: DiariaMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'diaria-my-suffix/new',
      name: 'DiariaMySuffixCreate',
      component: DiariaMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'diaria-my-suffix/:diariaId/edit',
      name: 'DiariaMySuffixEdit',
      component: DiariaMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'diaria-my-suffix/:diariaId/view',
      name: 'DiariaMySuffixView',
      component: DiariaMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cidades-my-suffix',
      name: 'CidadesMySuffix',
      component: CidadesMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cidades-my-suffix/new',
      name: 'CidadesMySuffixCreate',
      component: CidadesMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cidades-my-suffix/:cidadesId/edit',
      name: 'CidadesMySuffixEdit',
      component: CidadesMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cidades-my-suffix/:cidadesId/view',
      name: 'CidadesMySuffixView',
      component: CidadesMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'missao-my-suffix',
      name: 'MissaoMySuffix',
      component: MissaoMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'missao-my-suffix/new',
      name: 'MissaoMySuffixCreate',
      component: MissaoMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'missao-my-suffix/:missaoId/edit',
      name: 'MissaoMySuffixEdit',
      component: MissaoMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'missao-my-suffix/:missaoId/view',
      name: 'MissaoMySuffixView',
      component: MissaoMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servidor-my-suffix',
      name: 'ServidorMySuffix',
      component: ServidorMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servidor-my-suffix/new',
      name: 'ServidorMySuffixCreate',
      component: ServidorMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servidor-my-suffix/:servidorId/edit',
      name: 'ServidorMySuffixEdit',
      component: ServidorMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servidor-my-suffix/:servidorId/view',
      name: 'ServidorMySuffixView',
      component: ServidorMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'equipe-my-suffix',
      name: 'EquipeMySuffix',
      component: EquipeMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'equipe-my-suffix/new',
      name: 'EquipeMySuffixCreate',
      component: EquipeMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'equipe-my-suffix/:equipeId/edit',
      name: 'EquipeMySuffixEdit',
      component: EquipeMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'equipe-my-suffix/:equipeId/view',
      name: 'EquipeMySuffixView',
      component: EquipeMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
