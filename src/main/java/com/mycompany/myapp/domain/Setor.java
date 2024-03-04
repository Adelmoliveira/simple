package com.mycompany.myapp.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Setor.
 */
@Table("setor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Setor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("nome")
    private String nome;

    @NotNull(message = "must not be null")
    @Column("sigla")
    private String sigla;

    @Transient
    private Servidor servidor;

    @Column("servidor_id")
    private Long servidorId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Setor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Setor nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public Setor sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Servidor getServidor() {
        return this.servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
        this.servidorId = servidor != null ? servidor.getId() : null;
    }

    public Setor servidor(Servidor servidor) {
        this.setServidor(servidor);
        return this;
    }

    public Long getServidorId() {
        return this.servidorId;
    }

    public void setServidorId(Long servidor) {
        this.servidorId = servidor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Setor)) {
            return false;
        }
        return getId() != null && getId().equals(((Setor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Setor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
