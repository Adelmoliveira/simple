<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cidades">
        <h2 class="jh-entity-heading" data-cy="cidadesDetailsHeading">
          <span v-text="t$('iceaApp.cidades.detail.title')"></span> {{ cidades.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('iceaApp.cidades.cidade')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.DiariaLocalidadeEnum.' + cidades.cidade)"></span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.cidades.valorLocalidade')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.LocalidadeEnum.' + cidades.valorLocalidade)"></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="cidades.id"
          :to="{ name: 'CidadesMySuffixEdit', params: { cidadesId: cidades.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cidades-my-suffix-details.component.ts"></script>
