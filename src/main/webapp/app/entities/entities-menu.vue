<template>
  <div>
    <b-dropdown-item to="/setor-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.setorMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/diaria-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.diariaMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cidades-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cidadesMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/missao-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.missaoMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/servidor-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.servidorMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/equipe-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.equipeMySuffix')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
