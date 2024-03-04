<template>
  <div>
    <h2 id="page-heading" data-cy="ServidorHeading">
      <span v-text="t$('iceaApp.servidor.home.title')" id="servidor-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('iceaApp.servidor.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ServidorMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-servidor-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('iceaApp.servidor.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && servidors && servidors.length === 0">
      <span v-text="t$('iceaApp.servidor.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="servidors && servidors.length > 0">
      <table class="table table-striped" aria-describedby="servidors">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('foto')">
              <span v-text="t$('iceaApp.servidor.foto')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'foto'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nome')">
              <span v-text="t$('iceaApp.servidor.nome')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nome'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('sobreNome')">
              <span v-text="t$('iceaApp.servidor.sobreNome')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sobreNome'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span v-text="t$('iceaApp.servidor.email')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('celular')">
              <span v-text="t$('iceaApp.servidor.celular')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'celular'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('posto')">
              <span v-text="t$('iceaApp.servidor.posto')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'posto'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="servidor in servidors" :key="servidor.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ServidorMySuffixView', params: { servidorId: servidor.id } }">{{ servidor.id }}</router-link>
            </td>
            <td>
              <a v-if="servidor.foto" v-on:click="openFile(servidor.fotoContentType, servidor.foto)" v-text="t$('entity.action.open')"></a>
              <span v-if="servidor.foto">{{ servidor.fotoContentType }}, {{ byteSize(servidor.foto) }}</span>
            </td>
            <td>{{ servidor.nome }}</td>
            <td>{{ servidor.sobreNome }}</td>
            <td>{{ servidor.email }}</td>
            <td>{{ servidor.celular }}</td>
            <td v-text="t$('iceaApp.PostoEnum.' + servidor.posto)"></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ServidorMySuffixView', params: { servidorId: servidor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ServidorMySuffixEdit', params: { servidorId: servidor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(servidor)"
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
        <span ref="infiniteScrollEl"></span>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="iceaApp.servidor.delete.question" data-cy="servidorDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-servidor-heading" v-text="t$('iceaApp.servidor.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-servidor"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeServidorMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./servidor-my-suffix.component.ts"></script>
