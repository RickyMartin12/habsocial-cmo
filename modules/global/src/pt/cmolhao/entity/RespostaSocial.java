package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@NamePattern("%s|nome")
@DesignSupport("{'imported':true}")
@Table(name = "resposta_social")
@Entity(name = "cmolhao_RespostaSocial")
public class RespostaSocial extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -739200238458686822L;
    @Column(name = "capacidade")
    protected Integer capacidade;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_valencia")
    protected Tiposvalencia idTipoValencia;
    @Lob
    @Column(name = "nome")
    protected String nome;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idResSocial")
    protected Set<Valencias> valencias;

    public Set<Valencias> getValencias() {
        return valencias;
    }

    public void setValencias(Set<Valencias> valencias) {
        this.valencias = valencias;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tiposvalencia getIdTipoValencia() {
        return idTipoValencia;
    }

    public void setIdTipoValencia(Tiposvalencia idTipoValencia) {
        this.idTipoValencia = idTipoValencia;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }
}