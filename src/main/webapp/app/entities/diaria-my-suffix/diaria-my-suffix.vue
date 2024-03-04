<template>
  <div>
    <h2 id="page-heading" data-cy="DiariaHeading">
      <span v-text="t$('iceaApp.diaria.home.title')" id="diaria-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('iceaApp.diaria.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'DiariaMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-diaria-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('iceaApp.diaria.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && diarias && diarias.length === 0">
      <span v-text="t$('iceaApp.diaria.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="diarias && diarias.length > 0">
      <table class="table table-striped" aria-describedby="diarias">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.cidade')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.oficialSup')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.oficial')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.graduado')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.praca')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.civil')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.localidade')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.diaria.missao')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="diaria in diarias" :key="diaria.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DiariaMySuffixView', params: { diariaId: diaria.id } }">{{ diaria.id }}</router-link>
            </td>
            <td>{{ diaria.cidade }}</td>
            <td>{{ diaria.oficialSup }}</td>
            <td>{{ diaria.oficial }}</td>
            <td>{{ diaria.graduado }}</td>
            <td>{{ diaria.praca }}</td>
            <td>{{ diaria.civil }}</td>
            <td v-text="t$('iceaApp.DiariaLocalidadeEnum.' + diaria.localidade)"></td>
            <td>
              <div v-if="diaria.missao">
                <router-link :to="{ name: 'MissaoMySuffixView', params: { missaoId: diaria.missao.id } }">{{
                  diaria.missao.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DiariaMySuffixView', params: { diariaId: diaria.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DiariaMySuffixEdit', params: { diariaId: diaria.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(diaria)"
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
        <span id="iceaApp.diaria.delete.question" data-cy="diariaDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-diaria-heading" v-text="t$('iceaApp.diaria.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-diaria"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeDiariaMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./diaria-my-suffix.component.ts"></script>
