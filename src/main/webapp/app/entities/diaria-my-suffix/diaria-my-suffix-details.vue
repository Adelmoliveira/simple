<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="diaria">
        <h2 class="jh-entity-heading" data-cy="diariaDetailsHeading">
          <span v-text="t$('iceaApp.diaria.detail.title')"></span> {{ diaria.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('iceaApp.diaria.cidade')"></span>
          </dt>
          <dd>
            <span>{{ diaria.cidade }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.diaria.oficialSup')"></span>
          </dt>
          <dd>
            <span>{{ diaria.oficialSup }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.diaria.oficial')"></span>
          </dt>
          <dd>
            <span>{{ diaria.oficial }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.diaria.graduado')"></span>
          </dt>
          <dd>
            <span>{{ diaria.graduado }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.diaria.praca')"></span>
          </dt>
          <dd>
            <span>{{ diaria.praca }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.diaria.civil')"></span>
          </dt>
          <dd>
            <span>{{ diaria.civil }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.diaria.localidade')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.DiariaLocalidadeEnum.' + diaria.localidade)"></span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.diaria.missao')"></span>
          </dt>
          <dd>
            <div v-if="diaria.missao">
              <router-link :to="{ name: 'MissaoMySuffixView', params: { missaoId: diaria.missao.id } }">{{ diaria.missao.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="diaria.id" :to="{ name: 'DiariaMySuffixEdit', params: { diariaId: diaria.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./diaria-my-suffix-details.component.ts"></script>
