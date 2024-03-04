<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iceaApp.setor.home.createOrEditLabel"
          data-cy="SetorCreateUpdateHeading"
          v-text="t$('iceaApp.setor.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="setor.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="setor.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.setor.nome')" for="setor-my-suffix-nome"></label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="setor-my-suffix-nome"
              data-cy="nome"
              :class="{ valid: !v$.nome.$invalid, invalid: v$.nome.$invalid }"
              v-model="v$.nome.$model"
              required
            />
            <div v-if="v$.nome.$anyDirty && v$.nome.$invalid">
              <small class="form-text text-danger" v-for="error of v$.nome.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.setor.sigla')" for="setor-my-suffix-sigla"></label>
            <input
              type="text"
              class="form-control"
              name="sigla"
              id="setor-my-suffix-sigla"
              data-cy="sigla"
              :class="{ valid: !v$.sigla.$invalid, invalid: v$.sigla.$invalid }"
              v-model="v$.sigla.$model"
              required
            />
            <div v-if="v$.sigla.$anyDirty && v$.sigla.$invalid">
              <small class="form-text text-danger" v-for="error of v$.sigla.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.setor.servidor')" for="setor-my-suffix-servidor"></label>
            <select class="form-control" id="setor-my-suffix-servidor" data-cy="servidor" name="servidor" v-model="setor.servidor">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="setor.servidor && servidorOption.id === setor.servidor.id ? setor.servidor : servidorOption"
                v-for="servidorOption in servidors"
                :key="servidorOption.id"
              >
                {{ servidorOption.id }}
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
<script lang="ts" src="./setor-my-suffix-update.component.ts"></script>
