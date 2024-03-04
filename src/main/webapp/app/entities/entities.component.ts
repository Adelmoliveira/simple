import { defineComponent, provide } from 'vue';

import SetorMySuffixService from './setor-my-suffix/setor-my-suffix.service';
import DiariaMySuffixService from './diaria-my-suffix/diaria-my-suffix.service';
import CidadesMySuffixService from './cidades-my-suffix/cidades-my-suffix.service';
import MissaoMySuffixService from './missao-my-suffix/missao-my-suffix.service';
import ServidorMySuffixService from './servidor-my-suffix/servidor-my-suffix.service';
import EquipeMySuffixService from './equipe-my-suffix/equipe-my-suffix.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('setorService', () => new SetorMySuffixService());
    provide('diariaService', () => new DiariaMySuffixService());
    provide('cidadesService', () => new CidadesMySuffixService());
    provide('missaoService', () => new MissaoMySuffixService());
    provide('servidorService', () => new ServidorMySuffixService());
    provide('equipeService', () => new EquipeMySuffixService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
