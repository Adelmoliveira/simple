<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="servidor">
        <h2 class="jh-entity-heading" data-cy="servidorDetailsHeading">
          <span v-text="t$('iceaApp.servidor.detail.title')"></span> {{ servidor.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('iceaApp.servidor.foto')"></span>
          </dt>
          <dd>
            <div v-if="servidor.foto">
              <a v-on:click="openFile(servidor.fotoContentType, servidor.foto)" v-text="t$('entity.action.open')"></a>
              {{ servidor.fotoContentType }}, {{ byteSize(servidor.foto) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.servidor.nome')"></span>
          </dt>
          <dd>
            <span>{{ servidor.nome }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.servidor.sobreNome')"></span>
          </dt>
          <dd>
            <span>{{ servidor.sobreNome }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.servidor.email')"></span>
          </dt>
          <dd>
            <span>{{ servidor.email }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.servidor.celular')"></span>
          </dt>
          <dd>
            <span>{{ servidor.celular }}</span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.servidor.posto')"></span>
          </dt>
          <dd>
            <span v-text="t$('iceaApp.PostoEnum.' + servidor.posto)"></span>
          </dd>
          <dt>
            <span v-text="t$('iceaApp.servidor.equipes')"></span>
          </dt>
          <dd>
            <span v-for="(equipes, i) in servidor.equipes" :key="equipes.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'EquipeMySuffixView', params: { equipeId: equipes.id } }">{{ equipes.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="servidor.id"
          :to="{ name: 'ServidorMySuffixEdit', params: { servidorId: servidor.id } }"
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

<script lang="ts" src="./servidor-my-suffix-details.component.ts"></script>
