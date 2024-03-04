<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="iceaApp.missao.home.createOrEditLabel"
          data-cy="MissaoCreateUpdateHeading"
          v-text="t$('iceaApp.missao.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="missao.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="missao.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.nomeMissao')" for="missao-my-suffix-nomeMissao"></label>
            <input
              type="text"
              class="form-control"
              name="nomeMissao"
              id="missao-my-suffix-nomeMissao"
              data-cy="nomeMissao"
              :class="{ valid: !v$.nomeMissao.$invalid, invalid: v$.nomeMissao.$invalid }"
              v-model="v$.nomeMissao.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.prioridade')" for="missao-my-suffix-prioridade"></label>
            <select
              class="form-control"
              name="prioridade"
              :class="{ valid: !v$.prioridade.$invalid, invalid: v$.prioridade.$invalid }"
              v-model="v$.prioridade.$model"
              id="missao-my-suffix-prioridade"
              data-cy="prioridade"
            >
              <option
                v-for="prioridade in prioridadeValues"
                :key="prioridade"
                v-bind:value="prioridade"
                v-bind:label="t$('iceaApp.Prioridade.' + prioridade)"
              >
                {{ prioridade }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('iceaApp.missao.quantidadeDiaria')"
              for="missao-my-suffix-quantidadeDiaria"
            ></label>
            <input
              type="number"
              class="form-control"
              name="quantidadeDiaria"
              id="missao-my-suffix-quantidadeDiaria"
              data-cy="quantidadeDiaria"
              :class="{ valid: !v$.quantidadeDiaria.$invalid, invalid: v$.quantidadeDiaria.$invalid }"
              v-model.number="v$.quantidadeDiaria.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.meiaDiaria')" for="missao-my-suffix-meiaDiaria"></label>
            <input
              type="checkbox"
              class="form-check"
              name="meiaDiaria"
              id="missao-my-suffix-meiaDiaria"
              data-cy="meiaDiaria"
              :class="{ valid: !v$.meiaDiaria.$invalid, invalid: v$.meiaDiaria.$invalid }"
              v-model="v$.meiaDiaria.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('iceaApp.missao.quantidadeEquipe')"
              for="missao-my-suffix-quantidadeEquipe"
            ></label>
            <input
              type="number"
              class="form-control"
              name="quantidadeEquipe"
              id="missao-my-suffix-quantidadeEquipe"
              data-cy="quantidadeEquipe"
              :class="{ valid: !v$.quantidadeEquipe.$invalid, invalid: v$.quantidadeEquipe.$invalid }"
              v-model.number="v$.quantidadeEquipe.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.dataInicio')" for="missao-my-suffix-dataInicio"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="missao-my-suffix-dataInicio"
                  v-model="v$.dataInicio.$model"
                  name="dataInicio"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="missao-my-suffix-dataInicio"
                data-cy="dataInicio"
                type="text"
                class="form-control"
                name="dataInicio"
                :class="{ valid: !v$.dataInicio.$invalid, invalid: v$.dataInicio.$invalid }"
                v-model="v$.dataInicio.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.dataTermino')" for="missao-my-suffix-dataTermino"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="missao-my-suffix-dataTermino"
                  v-model="v$.dataTermino.$model"
                  name="dataTermino"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="missao-my-suffix-dataTermino"
                data-cy="dataTermino"
                type="text"
                class="form-control"
                name="dataTermino"
                :class="{ valid: !v$.dataTermino.$invalid, invalid: v$.dataTermino.$invalid }"
                v-model="v$.dataTermino.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.deslocamento')" for="missao-my-suffix-deslocamento"></label>
            <input
              type="checkbox"
              class="form-check"
              name="deslocamento"
              id="missao-my-suffix-deslocamento"
              data-cy="deslocamento"
              :class="{ valid: !v$.deslocamento.$invalid, invalid: v$.deslocamento.$invalid }"
              v-model="v$.deslocamento.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.passagemAerea')" for="missao-my-suffix-passagemAerea"></label>
            <input
              type="checkbox"
              class="form-check"
              name="passagemAerea"
              id="missao-my-suffix-passagemAerea"
              data-cy="passagemAerea"
              :class="{ valid: !v$.passagemAerea.$invalid, invalid: v$.passagemAerea.$invalid }"
              v-model="v$.passagemAerea.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.status')" for="missao-my-suffix-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="missao-my-suffix-status"
              data-cy="status"
            >
              <option
                v-for="statusEnum in statusEnumValues"
                :key="statusEnum"
                v-bind:value="statusEnum"
                v-bind:label="t$('iceaApp.StatusEnum.' + statusEnum)"
              >
                {{ statusEnum }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.osverificada')" for="missao-my-suffix-osverificada"></label>
            <input
              type="checkbox"
              class="form-check"
              name="osverificada"
              id="missao-my-suffix-osverificada"
              data-cy="osverificada"
              :class="{ valid: !v$.osverificada.$invalid, invalid: v$.osverificada.$invalid }"
              v-model="v$.osverificada.$model"
              required
            />
            <div v-if="v$.osverificada.$anyDirty && v$.osverificada.$invalid">
              <small class="form-text text-danger" v-for="error of v$.osverificada.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.acao')" for="missao-my-suffix-acao"></label>
            <select
              class="form-control"
              name="acao"
              :class="{ valid: !v$.acao.$invalid, invalid: v$.acao.$invalid }"
              v-model="v$.acao.$model"
              id="missao-my-suffix-acao"
              data-cy="acao"
              required
            >
              <option
                v-for="acaoEnum in acaoEnumValues"
                :key="acaoEnum"
                v-bind:value="acaoEnum"
                v-bind:label="t$('iceaApp.AcaoEnum.' + acaoEnum)"
              >
                {{ acaoEnum }}
              </option>
            </select>
            <div v-if="v$.acao.$anyDirty && v$.acao.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acao.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('iceaApp.missao.valorTotalMissao')"
              for="missao-my-suffix-valorTotalMissao"
            ></label>
            <select
              class="form-control"
              name="valorTotalMissao"
              :class="{ valid: !v$.valorTotalMissao.$invalid, invalid: v$.valorTotalMissao.$invalid }"
              v-model="v$.valorTotalMissao.$model"
              id="missao-my-suffix-valorTotalMissao"
              data-cy="valorTotalMissao"
            >
              <option
                v-for="orcamentoEnum in orcamentoEnumValues"
                :key="orcamentoEnum"
                v-bind:value="orcamentoEnum"
                v-bind:label="t$('iceaApp.OrcamentoEnum.' + orcamentoEnum)"
              >
                {{ orcamentoEnum }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('iceaApp.missao.valorDiariasRealizadas')"
              for="missao-my-suffix-valorDiariasRealizadas"
            ></label>
            <input
              type="number"
              class="form-control"
              name="valorDiariasRealizadas"
              id="missao-my-suffix-valorDiariasRealizadas"
              data-cy="valorDiariasRealizadas"
              :class="{ valid: !v$.valorDiariasRealizadas.$invalid, invalid: v$.valorDiariasRealizadas.$invalid }"
              v-model.number="v$.valorDiariasRealizadas.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.saldoDisponivel')" for="missao-my-suffix-saldoDisponivel"></label>
            <input
              type="number"
              class="form-control"
              name="saldoDisponivel"
              id="missao-my-suffix-saldoDisponivel"
              data-cy="saldoDisponivel"
              :class="{ valid: !v$.saldoDisponivel.$invalid, invalid: v$.saldoDisponivel.$invalid }"
              v-model.number="v$.saldoDisponivel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('iceaApp.missao.municipio')" for="missao-my-suffix-municipio"></label>
            <select class="form-control" id="missao-my-suffix-municipio" data-cy="municipio" name="municipio" v-model="missao.municipio">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="missao.municipio && cidadesOption.id === missao.municipio.id ? missao.municipio : cidadesOption"
                v-for="cidadesOption in cidades"
                :key="cidadesOption.id"
              >
                {{ cidadesOption.id }}
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
<script lang="ts" src="./missao-my-suffix-update.component.ts"></script>
