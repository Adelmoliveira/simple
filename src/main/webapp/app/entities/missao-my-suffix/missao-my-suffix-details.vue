<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="missao">
        <h2 class="jh-entity-heading" data-cy="missaoDetailsHeading">
          <span v-text="t$('iceaApp.missao.detail.title')"></span> {{ missao.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('iceaApp.missao.nomeMissao')"></span>
          </dt>
          <dd>
            <span>{{ missao.nomeMissao }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.prioridade')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.Prioridade.' + missao.prioridade)"></span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.quantidadeDiaria')"></span>
          </dt>
          <dd>
            <span>{{ missao.quantidadeDiaria }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.meiaDiaria')"></span>
          </dt>
          <dd>
            <span>{{ missao.meiaDiaria }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.quantidadeEquipe')"></span>
          </dt>
          <dd>
            <span>{{ missao.quantidadeEquipe }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.dataInicio')"></span>
          </dt>
          <dd>
            <span>{{ missao.dataInicio }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.dataTermino')"></span>
          </dt>
          <dd>
            <span>{{ missao.dataTermino }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.deslocamento')"></span>
          </dt>
          <dd>
            <span>{{ missao.deslocamento }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.passagemAerea')"></span>
          </dt>
          <dd>
            <span>{{ missao.passagemAerea }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.StatusEnum.' + missao.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.osverificada')"></span>
          </dt>
          <dd>
            <span>{{ missao.osverificada }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.acao')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.AcaoEnum.' + missao.acao)"></span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.valorTotalMissao')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.OrcamentoEnum.' + missao.valorTotalMissao)"></span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.valorDiariasRealizadas')"></span>
          </dt>
          <dd>
            <span>{{ missao.valorDiariasRealizadas }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.saldoDisponivel')"></span>
          </dt>
          <dd>
            <span>{{ missao.saldoDisponivel }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.missao.municipio')"></span>
          </dt>
          <dd>
            <div v-if="missao.municipio">
              <router-link :to="{ name: 'CidadesMySuffixView', params: { cidadesId: missao.municipio.id } }">{{
                missao.municipio.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="missao.id" :to="{ name: 'MissaoMySuffixEdit', params: { missaoId: missao.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./missao-my-suffix-details.component.ts"></script>
