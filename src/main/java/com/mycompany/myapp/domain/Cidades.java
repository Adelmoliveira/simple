package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import com.mycompany.myapp.domain.enumeration.LocalidadeEnum;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Cidades.
 */
@Table("cidades")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cidades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("cidade")
    private DiariaLocalidadeEnum cidade;

    @Column("valor_localidade")
    private LocalidadeEnum valorLocalidade;

    @Transient
    private Missao missao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cidades id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiariaLocalidadeEnum getCidade() {
        return this.cidade;
    }

    public Cidades cidade(DiariaLocalidadeEnum cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(DiariaLocalidadeEnum cidade) {
        this.cidade = cidade;
    }

    public LocalidadeEnum getValorLocalidade() {
        return this.valorLocalidade;
    }

    public Cidades valorLocalidade(LocalidadeEnum valorLocalidade) {
        this.setValorLocalidade(valorLocalidade);
        return this;
    }

    public void setValorLocalidade(LocalidadeEnum valorLocalidade) {
        this.valorLocalidade = valorLocalidade;
    }

    public Missao getMissao() {
        return this.missao;
    }

    public void setMissao(Missao missao) {
        if (this.missao != null) {
            this.missao.setMunicipio(null);
        }
        if (missao != null) {
            missao.setMunicipio(this);
        }
        this.missao = missao;
    }

    public Cidades missao(Missao missao) {
        this.setMissao(missao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidades)) {
            return false;
        }
        return getId() != null && getId().equals(((Cidades) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cidades{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", valorLocalidade='" + getValorLocalidade() + "'" +
            "}";
    }
}
