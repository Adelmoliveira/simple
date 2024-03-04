package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.AcaoEnum;
import com.mycompany.myapp.domain.enumeration.OrcamentoEnum;
import com.mycompany.myapp.domain.enumeration.Prioridade;
import com.mycompany.myapp.domain.enumeration.StatusEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Missao.
 */
@Table("missao")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Missao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("nome_missao")
    private String nomeMissao;

    @Column("prioridade")
    private Prioridade prioridade;

    @Column("quantidade_diaria")
    private Long quantidadeDiaria;

    @Column("meia_diaria")
    private Boolean meiaDiaria;

    @Column("quantidade_equipe")
    private Integer quantidadeEquipe;

    @Column("data_inicio")
    private LocalDate dataInicio;

    @Column("data_termino")
    private LocalDate dataTermino;

    @Column("deslocamento")
    private Boolean deslocamento;

    @Column("passagem_aerea")
    private Boolean passagemAerea;

    @Column("status")
    private StatusEnum status;

    @NotNull(message = "must not be null")
    @Column("osverificada")
    private Boolean osverificada;

    @NotNull(message = "must not be null")
    @Column("acao")
    private AcaoEnum acao;

    @Column("valor_total_missao")
    private OrcamentoEnum valorTotalMissao;

    @Column("valor_diarias_realizadas")
    private Double valorDiariasRealizadas;

    @Column("saldo_disponivel")
    private Double saldoDisponivel;

    @Transient
    private Cidades municipio;

    @Transient
    @JsonIgnoreProperties(value = { "missao" }, allowSetters = true)
    private Set<Diaria> diarias = new HashSet<>();

    @Column("municipio_id")
    private Long municipioId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Missao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMissao() {
        return this.nomeMissao;
    }

    public Missao nomeMissao(String nomeMissao) {
        this.setNomeMissao(nomeMissao);
        return this;
    }

    public void setNomeMissao(String nomeMissao) {
        this.nomeMissao = nomeMissao;
    }

    public Prioridade getPrioridade() {
        return this.prioridade;
    }

    public Missao prioridade(Prioridade prioridade) {
        this.setPrioridade(prioridade);
        return this;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Long getQuantidadeDiaria() {
        return this.quantidadeDiaria;
    }

    public Missao quantidadeDiaria(Long quantidadeDiaria) {
        this.setQuantidadeDiaria(quantidadeDiaria);
        return this;
    }

    public void setQuantidadeDiaria(Long quantidadeDiaria) {
        this.quantidadeDiaria = quantidadeDiaria;
    }

    public Boolean getMeiaDiaria() {
        return this.meiaDiaria;
    }

    public Missao meiaDiaria(Boolean meiaDiaria) {
        this.setMeiaDiaria(meiaDiaria);
        return this;
    }

    public void setMeiaDiaria(Boolean meiaDiaria) {
        this.meiaDiaria = meiaDiaria;
    }

    public Integer getQuantidadeEquipe() {
        return this.quantidadeEquipe;
    }

    public Missao quantidadeEquipe(Integer quantidadeEquipe) {
        this.setQuantidadeEquipe(quantidadeEquipe);
        return this;
    }

    public void setQuantidadeEquipe(Integer quantidadeEquipe) {
        this.quantidadeEquipe = quantidadeEquipe;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public Missao dataInicio(LocalDate dataInicio) {
        this.setDataInicio(dataInicio);
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return this.dataTermino;
    }

    public Missao dataTermino(LocalDate dataTermino) {
        this.setDataTermino(dataTermino);
        return this;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Boolean getDeslocamento() {
        return this.deslocamento;
    }

    public Missao deslocamento(Boolean deslocamento) {
        this.setDeslocamento(deslocamento);
        return this;
    }

    public void setDeslocamento(Boolean deslocamento) {
        this.deslocamento = deslocamento;
    }

    public Boolean getPassagemAerea() {
        return this.passagemAerea;
    }

    public Missao passagemAerea(Boolean passagemAerea) {
        this.setPassagemAerea(passagemAerea);
        return this;
    }

    public void setPassagemAerea(Boolean passagemAerea) {
        this.passagemAerea = passagemAerea;
    }

    public StatusEnum getStatus() {
        return this.status;
    }

    public Missao status(StatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Boolean getOsverificada() {
        return this.osverificada;
    }

    public Missao osverificada(Boolean osverificada) {
        this.setOsverificada(osverificada);
        return this;
    }

    public void setOsverificada(Boolean osverificada) {
        this.osverificada = osverificada;
    }

    public AcaoEnum getAcao() {
        return this.acao;
    }

    public Missao acao(AcaoEnum acao) {
        this.setAcao(acao);
        return this;
    }

    public void setAcao(AcaoEnum acao) {
        this.acao = acao;
    }

    public OrcamentoEnum getValorTotalMissao() {
        return this.valorTotalMissao;
    }

    public Missao valorTotalMissao(OrcamentoEnum valorTotalMissao) {
        this.setValorTotalMissao(valorTotalMissao);
        return this;
    }

    public void setValorTotalMissao(OrcamentoEnum valorTotalMissao) {
        this.valorTotalMissao = valorTotalMissao;
    }

    public Double getValorDiariasRealizadas() {
        return this.valorDiariasRealizadas;
    }

    public Missao valorDiariasRealizadas(Double valorDiariasRealizadas) {
        this.setValorDiariasRealizadas(valorDiariasRealizadas);
        return this;
    }

    public void setValorDiariasRealizadas(Double valorDiariasRealizadas) {
        this.valorDiariasRealizadas = valorDiariasRealizadas;
    }

    public Double getSaldoDisponivel() {
        return this.saldoDisponivel;
    }

    public Missao saldoDisponivel(Double saldoDisponivel) {
        this.setSaldoDisponivel(saldoDisponivel);
        return this;
    }

    public void setSaldoDisponivel(Double saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public Cidades getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(Cidades cidades) {
        this.municipio = cidades;
        this.municipioId = cidades != null ? cidades.getId() : null;
    }

    public Missao municipio(Cidades cidades) {
        this.setMunicipio(cidades);
        return this;
    }

    public Set<Diaria> getDiarias() {
        return this.diarias;
    }

    public void setDiarias(Set<Diaria> diarias) {
        if (this.diarias != null) {
            this.diarias.forEach(i -> i.setMissao(null));
        }
        if (diarias != null) {
            diarias.forEach(i -> i.setMissao(this));
        }
        this.diarias = diarias;
    }

    public Missao diarias(Set<Diaria> diarias) {
        this.setDiarias(diarias);
        return this;
    }

    public Missao addDiarias(Diaria diaria) {
        this.diarias.add(diaria);
        diaria.setMissao(this);
        return this;
    }

    public Missao removeDiarias(Diaria diaria) {
        this.diarias.remove(diaria);
        diaria.setMissao(null);
        return this;
    }

    public Long getMunicipioId() {
        return this.municipioId;
    }

    public void setMunicipioId(Long cidades) {
        this.municipioId = cidades;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Missao)) {
            return false;
        }
        return getId() != null && getId().equals(((Missao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Missao{" +
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
            "}";
    }
}
