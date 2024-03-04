package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.PostoEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Servidor.
 */
@Table("servidor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Servidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("foto")
    private byte[] foto;

    @Column("foto_content_type")
    private String fotoContentType;

    @NotNull(message = "must not be null")
    @Column("nome")
    private String nome;

    @Column("sobre_nome")
    private String sobreNome;

    @Column("email")
    private String email;

    @Column("celular")
    private String celular;

    @NotNull(message = "must not be null")
    @Column("posto")
    private PostoEnum posto;

    @Transient
    @JsonIgnoreProperties(value = { "servidores" }, allowSetters = true)
    private Set<Equipe> equipes = new HashSet<>();

    @Transient
    private Setor setor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Servidor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public Servidor foto(byte[] foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return this.fotoContentType;
    }

    public Servidor fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getNome() {
        return this.nome;
    }

    public Servidor nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return this.sobreNome;
    }

    public Servidor sobreNome(String sobreNome) {
        this.setSobreNome(sobreNome);
        return this;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getEmail() {
        return this.email;
    }

    public Servidor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return this.celular;
    }

    public Servidor celular(String celular) {
        this.setCelular(celular);
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public PostoEnum getPosto() {
        return this.posto;
    }

    public Servidor posto(PostoEnum posto) {
        this.setPosto(posto);
        return this;
    }

    public void setPosto(PostoEnum posto) {
        this.posto = posto;
    }

    public Set<Equipe> getEquipes() {
        return this.equipes;
    }

    public void setEquipes(Set<Equipe> equipes) {
        this.equipes = equipes;
    }

    public Servidor equipes(Set<Equipe> equipes) {
        this.setEquipes(equipes);
        return this;
    }

    public Servidor addEquipes(Equipe equipe) {
        this.equipes.add(equipe);
        return this;
    }

    public Servidor removeEquipes(Equipe equipe) {
        this.equipes.remove(equipe);
        return this;
    }

    public Setor getSetor() {
        return this.setor;
    }

    public void setSetor(Setor setor) {
        if (this.setor != null) {
            this.setor.setServidor(null);
        }
        if (setor != null) {
            setor.setServidor(this);
        }
        this.setor = setor;
    }

    public Servidor setor(Setor setor) {
        this.setSetor(setor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servidor)) {
            return false;
        }
        return getId() != null && getId().equals(((Servidor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servidor{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", nome='" + getNome() + "'" +
            ", sobreNome='" + getSobreNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", celular='" + getCelular() + "'" +
            ", posto='" + getPosto() + "'" +
            "}";
    }
}
