package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Equipe.
 */
@Table("equipe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("nome")
    private String nome;

    @Transient
    @JsonIgnoreProperties(value = { "equipes", "setor" }, allowSetters = true)
    private Set<Servidor> servidores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Equipe nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Servidor> getServidores() {
        return this.servidores;
    }

    public void setServidores(Set<Servidor> servidors) {
        if (this.servidores != null) {
            this.servidores.forEach(i -> i.removeEquipes(this));
        }
        if (servidors != null) {
            servidors.forEach(i -> i.addEquipes(this));
        }
        this.servidores = servidors;
    }

    public Equipe servidores(Set<Servidor> servidors) {
        this.setServidores(servidors);
        return this;
    }

    public Equipe addServidores(Servidor servidor) {
        this.servidores.add(servidor);
        servidor.getEquipes().add(this);
        return this;
    }

    public Equipe removeServidores(Servidor servidor) {
        this.servidores.remove(servidor);
        servidor.getEquipes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipe)) {
            return false;
        }
        return getId() != null && getId().equals(((Equipe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
