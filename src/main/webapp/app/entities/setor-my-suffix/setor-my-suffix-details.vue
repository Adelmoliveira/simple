<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="setor">
        <h2 class="jh-entity-heading" data-cy="setorDetailsHeading">
          <span v-text="t$('iceaApp.setor.detail.title')"></span> {{ setor.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('iceaApp.setor.nome')"></span>
          </dt>
          <dd>
            <span>{{ setor.nome }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.setor.sigla')"></span>
          </dt>
          <dd>
            <span>{{ setor.sigla }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.setor.servidor')"></span>
          </dt>
          <dd>
            <div v-if="setor.servidor">
              <router-link :to="{ name: 'ServidorMySuffixView', params: { servidorId: setor.servidor.id } }">{{
                setor.servidor.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="setor.id" :to="{ name: 'SetorMySuffixEdit', params: { setorId: setor.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./setor-my-suffix-details.component.ts"></script>
