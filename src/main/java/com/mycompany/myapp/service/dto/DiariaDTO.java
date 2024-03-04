package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Diaria} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiariaDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String cidade;

    private Double oficialSup;

    private Double oficial;

    private Double graduado;

    private Double praca;

    private Double civil;

    @NotNull(message = "must not be null")
    private DiariaLocalidadeEnum localidade;

    private MissaoDTO missao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Double getOficialSup() {
        return oficialSup;
    }

    public void setOficialSup(Double oficialSup) {
        this.oficialSup = oficialSup;
    }

    public Double getOficial() {
        return oficial;
    }

    public void setOficial(Double oficial) {
        this.oficial = oficial;
    }

    public Double getGraduado() {
        return graduado;
    }

    public void setGraduado(Double graduado) {
        this.graduado = graduado;
    }

    public Double getPraca() {
        return praca;
    }

    public void setPraca(Double praca) {
        this.praca = praca;
    }

    public Double getCivil() {
        return civil;
    }

    public void setCivil(Double civil) {
        this.civil = civil;
    }

    public DiariaLocalidadeEnum getLocalidade() {
        return localidade;
    }

    public void setLocalidade(DiariaLocalidadeEnum localidade) {
        this.localidade = localidade;
    }

    public MissaoDTO getMissao() {
        return missao;
    }

    public void setMissao(MissaoDTO missao) {
        this.missao = missao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiariaDTO)) {
            return false;
        }

        DiariaDTO diariaDTO = (DiariaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, diariaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiariaDTO{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", oficialSup=" + getOficialSup() +
            ", oficial=" + getOficial() +
            ", graduado=" + getGraduado() +
            ", praca=" + getPraca() +
            ", civil=" + getCivil() +
            ", localidade='" + getLocalidade() + "'" +
            ", missao=" + getMissao() +
            "}";
    }
}
