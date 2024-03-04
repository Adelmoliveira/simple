<template>
  <div>
    <h2 id="page-heading" data-cy="MissaoHeading">
      <span v-text="t$('iceaApp.missao.home.title')" id="missao-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('iceaApp.missao.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MissaoMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-missao-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('iceaApp.missao.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && missaos && missaos.length === 0">
      <span v-text="t$('iceaApp.missao.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="missaos && missaos.length > 0">
      <table class="table table-striped" aria-describedby="missaos">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nomeMissao')">
              <span v-text="t$('iceaApp.missao.nomeMissao')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nomeMissao'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('prioridade')">
              <span v-text="t$('iceaApp.missao.prioridade')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prioridade'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('quantidadeDiaria')">
              <span v-text="t$('iceaApp.missao.quantidadeDiaria')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quantidadeDiaria'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('meiaDiaria')">
              <span v-text="t$('iceaApp.missao.meiaDiaria')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'meiaDiaria'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('quantidadeEquipe')">
              <span v-text="t$('iceaApp.missao.quantidadeEquipe')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quantidadeEquipe'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataInicio')">
              <span v-text="t$('iceaApp.missao.dataInicio')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataInicio'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataTermino')">
              <span v-text="t$('iceaApp.missao.dataTermino')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataTermino'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deslocamento')">
              <span v-text="t$('iceaApp.missao.deslocamento')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deslocamento'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('passagemAerea')">
              <span v-text="t$('iceaApp.missao.passagemAerea')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'passagemAerea'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('iceaApp.missao.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('osverificada')">
              <span v-text="t$('iceaApp.missao.osverificada')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'osverificada'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acao')">
              <span v-text="t$('iceaApp.missao.acao')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acao'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('valorTotalMissao')">
              <span v-text="t$('iceaApp.missao.valorTotalMissao')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'valorTotalMissao'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('valorDiariasRealizadas')">
              <span v-text="t$('iceaApp.missao.valorDiariasRealizadas')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'valorDiariasRealizadas'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('saldoDisponivel')">
              <span v-text="t$('iceaApp.missao.saldoDisponivel')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'saldoDisponivel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('municipio.id')">
              <span v-text="t$('iceaApp.missao.municipio')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'municipio.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="missao in missaos" :key="missao.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MissaoMySuffixView', params: { missaoId: missao.id } }">{{ missao.id }}</router-link>
            </td>
            <td>{{ missao.nomeMissao }}</td>
            <td v-text="t$('iceaApp.Prioridade.' + missao.prioridade)"></td>
            <td>{{ missao.quantidadeDiaria }}</td>
            <td>{{ missao.meiaDiaria }}</td>
            <td>{{ missao.quantidadeEquipe }}</td>
            <td>{{ missao.dataInicio }}</td>
            <td>{{ missao.dataTermino }}</td>
            <td>{{ missao.deslocamento }}</td>
            <td>{{ missao.passagemAerea }}</td>
            <td v-text="t$('iceaApp.StatusEnum.' + missao.status)"></td>
            <td>{{ missao.osverificada }}</td>
            <td v-text="t$('iceaApp.AcaoEnum.' + missao.acao)"></td>
            <td v-text="t$('iceaApp.OrcamentoEnum.' + missao.valorTotalMissao)"></td>
            <td>{{ missao.valorDiariasRealizadas }}</td>
            <td>{{ missao.saldoDisponivel }}</td>
            <td>
              <div v-if="missao.municipio">
                <router-link :to="{ name: 'CidadesMySuffixView', params: { cidadesId: missao.municipio.id } }">{{
                  missao.municipio.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MissaoMySuffixView', params: { missaoId: missao.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MissaoMySuffixEdit', params: { missaoId: missao.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(missao)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="iceaApp.missao.delete.question" data-cy="missaoDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-missao-heading" v-text="t$('iceaApp.missao.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-missao"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMissaoMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="missaos && missaos.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./missao-my-suffix.component.ts"></script>
