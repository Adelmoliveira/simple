package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import com.mycompany.myapp.domain.enumeration.LocalidadeEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Cidades} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadesDTO implements Serializable {

    private Long id;

    private DiariaLocalidadeEnum cidade;

    private LocalidadeEnum valorLocalidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiariaLocalidadeEnum getCidade() {
        return cidade;
    }

    public void setCidade(DiariaLocalidadeEnum cidade) {
        this.cidade = cidade;
    }

    public LocalidadeEnum getValorLocalidade() {
        return valorLocalidade;
    }

    public void setValorLocalidade(LocalidadeEnum valorLocalidade) {
        this.valorLocalidade = valorLocalidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CidadesDTO)) {
            return false;
        }

        CidadesDTO cidadesDTO = (CidadesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cidadesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadesDTO{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", valorLocalidade='" + getValorLocalidade() + "'" +
            "}";
    }
}
