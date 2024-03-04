package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Diaria.
 */
@Table("diaria")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Diaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("cidade")
    private String cidade;

    @Column("oficial_sup")
    private Double oficialSup;

    @Column("oficial")
    private Double oficial;

    @Column("graduado")
    private Double graduado;

    @Column("praca")
    private Double praca;

    @Column("civil")
    private Double civil;

    @NotNull(message = "must not be null")
    @Column("localidade")
    private DiariaLocalidadeEnum localidade;

    @Transient
    @JsonIgnoreProperties(value = { "municipio", "diarias" }, allowSetters = true)
    private Missao missao;

    @Column("missao_id")
    private Long missaoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Diaria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return this.cidade;
    }

    public Diaria cidade(String cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Double getOficialSup() {
        return this.oficialSup;
    }

    public Diaria oficialSup(Double oficialSup) {
        this.setOficialSup(oficialSup);
        return this;
    }

    public void setOficialSup(Double oficialSup) {
        this.oficialSup = oficialSup;
    }

    public Double getOficial() {
        return this.oficial;
    }

    public Diaria oficial(Double oficial) {
        this.setOficial(oficial);
        return this;
    }

    public void setOficial(Double oficial) {
        this.oficial = oficial;
    }

    public Double getGraduado() {
        return this.graduado;
    }

    public Diaria graduado(Double graduado) {
        this.setGraduado(graduado);
        return this;
    }

    public void setGraduado(Double graduado) {
        this.graduado = graduado;
    }

    public Double getPraca() {
        return this.praca;
    }

    public Diaria praca(Double praca) {
        this.setPraca(praca);
        return this;
    }

    public void setPraca(Double praca) {
        this.praca = praca;
    }

    public Double getCivil() {
        return this.civil;
    }

    public Diaria civil(Double civil) {
        this.setCivil(civil);
        return this;
    }

    public void setCivil(Double civil) {
        this.civil = civil;
    }

    public DiariaLocalidadeEnum getLocalidade() {
        return this.localidade;
    }

    public Diaria localidade(DiariaLocalidadeEnum localidade) {
        this.setLocalidade(localidade);
        return this;
    }

    public void setLocalidade(DiariaLocalidadeEnum localidade) {
        this.localidade = localidade;
    }

    public Missao getMissao() {
        return this.missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
        this.missaoId = missao != null ? missao.getId() : null;
    }

    public Diaria missao(Missao missao) {
        this.setMissao(missao);
        return this;
    }

    public Long getMissaoId() {
        return this.missaoId;
    }

    public void setMissaoId(Long missao) {
        this.missaoId = missao;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diaria)) {
            return false;
        }
        return getId() != null && getId().equals(((Diaria) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diaria{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", oficialSup=" + getOficialSup() +
            ", oficial=" + getOficial() +
            ", graduado=" + getGraduado() +
            ", praca=" + getPraca() +
            ", civil=" + getCivil() +
            ", localidade='" + getLocalidade() + "'" +
            "}";
    }
}
