package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.AcaoEnum;
import com.mycompany.myapp.domain.enumeration.OrcamentoEnum;
import com.mycompany.myapp.domain.enumeration.Prioridade;
import com.mycompany.myapp.domain.enumeration.StatusEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Missao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MissaoDTO implements Serializable {

    private Long id;

    private String nomeMissao;

    private Prioridade prioridade;

    private Long quantidadeDiaria;

    private Boolean meiaDiaria;

    private Integer quantidadeEquipe;

    private LocalDate dataInicio;

    private LocalDate dataTermino;

    private Boolean deslocamento;

    private Boolean passagemAerea;

    private StatusEnum status;

    @NotNull(message = "must not be null")
    private Boolean osverificada;

    @NotNull(message = "must not be null")
    private AcaoEnum acao;

    private OrcamentoEnum valorTotalMissao;

    private Double valorDiariasRealizadas;

    private Double saldoDisponivel;

    private CidadesDTO municipio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMissao() {
        return nomeMissao;
    }

    public void setNomeMissao(String nomeMissao) {
        this.nomeMissao = nomeMissao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Long getQuantidadeDiaria() {
        return quantidadeDiaria;
    }

    public void setQuantidadeDiaria(Long quantidadeDiaria) {
        this.quantidadeDiaria = quantidadeDiaria;
    }

    public Boolean getMeiaDiaria() {
        return meiaDiaria;
    }

    public void setMeiaDiaria(Boolean meiaDiaria) {
        this.meiaDiaria = meiaDiaria;
    }

    public Integer getQuantidadeEquipe() {
        return quantidadeEquipe;
    }

    public void setQuantidadeEquipe(Integer quantidadeEquipe) {
        this.quantidadeEquipe = quantidadeEquipe;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Boolean getDeslocamento() {
        return deslocamento;
    }

    public void setDeslocamento(Boolean deslocamento) {
        this.deslocamento = deslocamento;
    }

    public Boolean getPassagemAerea() {
        return passagemAerea;
    }

    public void setPassagemAerea(Boolean passagemAerea) {
        this.passagemAerea = passagemAerea;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Boolean getOsverificada() {
        return osverificada;
    }

    public void setOsverificada(Boolean osverificada) {
        this.osverificada = osverificada;
    }

    public AcaoEnum getAcao() {
        return acao;
    }

    public void setAcao(AcaoEnum acao) {
        this.acao = acao;
    }

    public OrcamentoEnum getValorTotalMissao() {
        return valorTotalMissao;
    }

    public void setValorTotalMissao(OrcamentoEnum valorTotalMissao) {
        this.valorTotalMissao = valorTotalMissao;
    }

    public Double getValorDiariasRealizadas() {
        return valorDiariasRealizadas;
    }

    public void setValorDiariasRealizadas(Double valorDiariasRealizadas) {
        this.valorDiariasRealizadas = valorDiariasRealizadas;
    }

    public Double getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(Double saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public CidadesDTO getMunicipio() {
        return municipio;
    }

    public void setMunicipio(CidadesDTO municipio) {
        this.municipio = municipio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MissaoDTO)) {
            return false;
        }

        MissaoDTO missaoDTO = (MissaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, missaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MissaoDTO{" +
            "id=" + getId() +
            ", nomeMissao='" + getNomeMissao() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", quantidadeDiaria=" + getQuantidadeDiaria() +
            ", meiaDiaria='" + getMeiaDiaria() + "'" +
            ", quantidadeEquipe=" + getQuantidadeEquipe() +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataTermino='" + getDataTermino() + "'" +
            ", deslocamento='" + getDeslocamento() + "'" +
            ", passagemAerea='" + getPassagemAerea() + "'" +
            ", status='" + getStatus() + "'" +
            ", osverificada='" + getOsverificada() + "'" +
            ", acao='" + getAcao() + "'" +
            ", valorTotalMissao='" + getValorTotalMissao() + "'" +
            ", valorDiariasRealizadas=" + getValorDiariasRealizadas() +
            ", saldoDisponivel=" + getSaldoDisponivel() +
            ", municipio=" + getMunicipio() +
            "}";
    }
}
