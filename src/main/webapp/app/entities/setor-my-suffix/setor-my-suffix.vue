<template>
  <div>
    <h2 id="page-heading" data-cy="SetorHeading">
      <span v-text="t$('iceaApp.setor.home.title')" id="setor-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('iceaApp.setor.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'SetorMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-setor-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('iceaApp.setor.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && setors && setors.length === 0">
      <span v-text="t$('iceaApp.setor.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="setors && setors.length > 0">
      <table class="table table-striped" aria-describedby="setors">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.setor.nome')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.setor.sigla')"></span></th>
            <th scope="row"><span v-text="t$('iceaApp.setor.servidor')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="setor in setors" :key="setor.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SetorMySuffixView', params: { setorId: setor.id } }">{{ setor.id }}</router-link>
            </td>
            <td>{{ setor.nome }}</td>
            <td>{{ setor.sigla }}</td>
            <td>
              <div v-if="setor.servidor">
                <router-link :to="{ name: 'ServidorMySuffixView', params: { servidorId: setor.servidor.id } }">{{
                  setor.servidor.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SetorMySuffixView', params: { setorId: setor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SetorMySuffixEdit', params: { setorId: setor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(setor)"
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
        <span id="iceaApp.setor.delete.question" data-cy="setorDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-setor-heading" v-text="t$('iceaApp.setor.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-setor"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeSetorMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./setor-my-suffix.component.ts"></script>
