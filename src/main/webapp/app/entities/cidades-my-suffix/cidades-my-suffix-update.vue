<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iceaApp.cidades.home.createOrEditLabel"
          data-cy="CidadesCreateUpdateHeading"
          v-text="t$('iceaApp.cidades.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="cidades.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="cidades.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.cidades.cidade')" for="cidades-my-suffix-cidade"></label>
            <select
              class="form-control"
              name="cidade"
              :class="{ valid: !v$.cidade.$invalid, invalid: v$.cidade.$invalid }"
              v-model="v$.cidade.$model"
              id="cidades-my-suffix-cidade"
              data-cy="cidade"
            >
              <option
                v-for="diariaLocalidadeEnum in diariaLocalidadeEnumValues"
                :key="diariaLocalidadeEnum"
                v-bind:value="diariaLocalidadeEnum"
                v-bind:label="t$('iceaApp.DiariaLocalidadeEnum.' + diariaLocalidadeEnum)"
              >
                {{ diariaLocalidadeEnum }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('iceaApp.cidades.valorLocalidade')"
              for="cidades-my-suffix-valorLocalidade"
            ></label>
            <select
              class="form-control"
              name="valorLocalidade"
              :class="{ valid: !v$.valorLocalidade.$invalid, invalid: v$.valorLocalidade.$invalid }"
              v-model="v$.valorLocalidade.$model"
              id="cidades-my-suffix-valorLocalidade"
              data-cy="valorLocalidade"
            >
              <option
                v-for="localidadeEnum in localidadeEnumValues"
                :key="localidadeEnum"
                v-bind:value="localidadeEnum"
                v-bind:label="t$('iceaApp.LocalidadeEnum.' + localidadeEnum)"
              >
                {{ localidadeEnum }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cidades-my-suffix-update.component.ts"></script>
