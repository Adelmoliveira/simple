package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.PostoEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Servidor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServidorDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] foto;

    private String fotoContentType;

    @NotNull(message = "must not be null")
    private String nome;

    private String sobreNome;

    private String email;

    private String celular;

    @NotNull(message = "must not be null")
    private PostoEnum posto;

    private Set<EquipeDTO> equipes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public PostoEnum getPosto() {
        return posto;
    }

    public void setPosto(PostoEnum posto) {
        this.posto = posto;
    }

    public Set<EquipeDTO> getEquipes() {
        return equipes;
    }

    public void setEquipes(Set<EquipeDTO> equipes) {
        this.equipes = equipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServidorDTO)) {
            return false;
        }

        ServidorDTO servidorDTO = (ServidorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servidorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServidorDTO{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", nome='" + getNome() + "'" +
            ", sobreNome='" + getSobreNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", celular='" + getCelular() + "'" +
            ", posto='" + getPosto() + "'" +
            ", equipes=" + getEquipes() +
            "}";
    }
}
