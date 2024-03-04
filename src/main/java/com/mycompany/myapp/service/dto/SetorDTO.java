package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Setor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SetorDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String nome;

    @NotNull(message = "must not be null")
    private String sigla;

    private ServidorDTO servidor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public ServidorDTO getServidor() {
        return servidor;
    }

    public void setServidor(ServidorDTO servidor) {
        this.servidor = servidor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetorDTO)) {
            return false;
        }

        SetorDTO setorDTO = (SetorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, setorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SetorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", servidor=" + getServidor() +
            "}";
    }
}
