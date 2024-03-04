<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iceaApp.diaria.home.createOrEditLabel"
          data-cy="DiariaCreateUpdateHeading"
          v-text="t$('iceaApp.diaria.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="diaria.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="diaria.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.cidade')" for="diaria-my-suffix-cidade"></label>
            <input
              type="text"
              class="form-control"
              name="cidade"
              id="diaria-my-suffix-cidade"
              data-cy="cidade"
              :class="{ valid: !v$.cidade.$invalid, invalid: v$.cidade.$invalid }"
              v-model="v$.cidade.$model"
              required
            />
            <div v-if="v$.cidade.$anyDirty && v$.cidade.$invalid">
              <small class="form-text text-danger" v-for="error of v$.cidade.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.oficialSup')" for="diaria-my-suffix-oficialSup"></label>
            <input
              type="number"
              class="form-control"
              name="oficialSup"
              id="diaria-my-suffix-oficialSup"
              data-cy="oficialSup"
              :class="{ valid: !v$.oficialSup.$invalid, invalid: v$.oficialSup.$invalid }"
              v-model.number="v$.oficialSup.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.oficial')" for="diaria-my-suffix-oficial"></label>
            <input
              type="number"
              class="form-control"
              name="oficial"
              id="diaria-my-suffix-oficial"
              data-cy="oficial"
              :class="{ valid: !v$.oficial.$invalid, invalid: v$.oficial.$invalid }"
              v-model.number="v$.oficial.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.graduado')" for="diaria-my-suffix-graduado"></label>
            <input
              type="number"
              class="form-control"
              name="graduado"
              id="diaria-my-suffix-graduado"
              data-cy="graduado"
              :class="{ valid: !v$.graduado.$invalid, invalid: v$.graduado.$invalid }"
              v-model.number="v$.graduado.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.praca')" for="diaria-my-suffix-praca"></label>
            <input
              type="number"
              class="form-control"
              name="praca"
              id="diaria-my-suffix-praca"
              data-cy="praca"
              :class="{ valid: !v$.praca.$invalid, invalid: v$.praca.$invalid }"
              v-model.number="v$.praca.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.civil')" for="diaria-my-suffix-civil"></label>
            <input
              type="number"
              class="form-control"
              name="civil"
              id="diaria-my-suffix-civil"
              data-cy="civil"
              :class="{ valid: !v$.civil.$invalid, invalid: v$.civil.$invalid }"
              v-model.number="v$.civil.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.localidade')" for="diaria-my-suffix-localidade"></label>
            <select
              class="form-control"
              name="localidade"
              :class="{ valid: !v$.localidade.$invalid, invalid: v$.localidade.$invalid }"
              v-model="v$.localidade.$model"
              id="diaria-my-suffix-localidade"
              data-cy="localidade"
              required
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
            <div v-if="v$.localidade.$anyDirty && v$.localidade.$invalid">
              <small class="form-text text-danger" v-for="error of v$.localidade.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.diaria.missao')" for="diaria-my-suffix-missao"></label>
            <select class="form-control" id="diaria-my-suffix-missao" data-cy="missao" name="missao" v-model="diaria.missao">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="diaria.missao && missaoOption.id === diaria.missao.id ? diaria.missao : missaoOption"
                v-for="missaoOption in missaos"
                :key="missaoOption.id"
              >
                {{ missaoOption.id }}
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
<script lang="ts" src="./diaria-my-suffix-update.component.ts"></script>
