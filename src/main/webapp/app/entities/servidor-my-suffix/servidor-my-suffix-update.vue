<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iceaApp.servidor.home.createOrEditLabel"
          data-cy="ServidorCreateUpdateHeading"
          v-text="t$('iceaApp.servidor.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="servidor.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="servidor.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.servidor.foto')" for="servidor-my-suffix-foto"></label>
            <div>
              <div v-if="servidor.foto" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(servidor.fotoContentType, servidor.foto)" v-text="t$('entity.action.open')"></a
                ><br />
                <span class="pull-left">{{ servidor.fotoContentType }}, {{ byteSize(servidor.foto) }}</span>
                <button
                  type="button"
                  v-on:click="
                    servidor.foto = null;
                    servidor.fotoContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_foto" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_foto"
                id="file_foto"
                style="display: none"
                data-cy="foto"
                v-on:change="setFileData($event, servidor, 'foto', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="foto"
              id="servidor-my-suffix-foto"
              data-cy="foto"
              :class="{ valid: !v$.foto.$invalid, invalid: v$.foto.$invalid }"
              v-model="v$.foto.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="fotoContentType"
              id="servidor-my-suffix-fotoContentType"
              v-model="servidor.fotoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.servidor.nome')" for="servidor-my-suffix-nome"></label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="servidor-my-suffix-nome"
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
            <label class="form-control-label" v-text="t$('iceaApp.servidor.sobreNome')" for="servidor-my-suffix-sobreNome"></label>
            <input
              type="text"
              class="form-control"
              name="sobreNome"
              id="servidor-my-suffix-sobreNome"
              data-cy="sobreNome"
              :class="{ valid: !v$.sobreNome.$invalid, invalid: v$.sobreNome.$invalid }"
              v-model="v$.sobreNome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.servidor.email')" for="servidor-my-suffix-email"></label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="servidor-my-suffix-email"
              data-cy="email"
              :class="{ valid: !v$.email.$invalid, invalid: v$.email.$invalid }"
              v-model="v$.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.servidor.celular')" for="servidor-my-suffix-celular"></label>
            <input
              type="text"
              class="form-control"
              name="celular"
              id="servidor-my-suffix-celular"
              data-cy="celular"
              :class="{ valid: !v$.celular.$invalid, invalid: v$.celular.$invalid }"
              v-model="v$.celular.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.servidor.posto')" for="servidor-my-suffix-posto"></label>
            <select
              class="form-control"
              name="posto"
              :class="{ valid: !v$.posto.$invalid, invalid: v$.posto.$invalid }"
              v-model="v$.posto.$model"
              id="servidor-my-suffix-posto"
              data-cy="posto"
              required
            >
              <option
                v-for="postoEnum in postoEnumValues"
                :key="postoEnum"
                v-bind:value="postoEnum"
                v-bind:label="t$('iceaApp.PostoEnum.' + postoEnum)"
              >
                {{ postoEnum }}
              </option>
            </select>
            <div v-if="v$.posto.$anyDirty && v$.posto.$invalid">
              <small class="form-text text-danger" v-for="error of v$.posto.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label v-text="t$('iceaApp.servidor.equipes')" for="servidor-my-suffix-equipes"></label>
            <select
              class="form-control"
              id="servidor-my-suffix-equipes"
              data-cy="equipes"
              multiple
              name="equipes"
              v-if="servidor.equipes !== undefined"
              v-model="servidor.equipes"
            >
              <option v-bind:value="getSelected(servidor.equipes, equipeOption)" v-for="equipeOption in equipes" :key="equipeOption.id">
                {{ equipeOption.id }}
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
<script lang="ts" src="./servidor-my-suffix-update.component.ts"></script>
