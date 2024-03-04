<template>
  <div>
    <h2 id="page-heading" data-cy="CidadesHeading">
      <span v-text="t$('iceaApp.cidades.home.title')" id="cidades-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('iceaApp.cidades.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CidadesMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-cidades-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('iceaApp.cidades.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cidades && cidades.length === 0">
      <span v-text="t$('iceaApp.cidades.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="cidades && cidades.length > 0">
      <table class="table table-striped" aria-describedby="cidades">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.cidades.cidade')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.cidades.valorLocalidade')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cidades in cidades" :key="cidades.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CidadesMySuffixView', params: { cidadesId: cidades.id } }">{{ cidades.id }}</router-link>
            </td>
            <td v-text="t$('iceaApp.DiariaLocalidadeEnum.' + cidades.cidade)"></td>
            <td v-text="t$('iceaApp.LocalidadeEnum.' + cidades.valorLocalidade)"></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CidadesMySuffixView', params: { cidadesId: cidades.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CidadesMySuffixEdit', params: { cidadesId: cidades.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cidades)"
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
        <span id="iceaApp.cidades.delete.question" data-cy="cidadesDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-cidades-heading" v-text="t$('iceaApp.cidades.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-cidades"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCidadesMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./cidades-my-suffix.component.ts"></script>
