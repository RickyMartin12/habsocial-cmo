package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@NamePattern("%s|nome")

@DesignSupport("{'imported':true}")
@Table(name = "pareceres")
@Entity(name = "cmolhao_Pareceres")
public class Pareceres extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -6567780714904670531L;
    @Lob
    @Column(name = "nome")
    protected String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTipoPareceres")
    protected Set<TipoPareceres> tipoPareceres;

    public Set<TipoPareceres> getTipoPareceres() {
        return tipoPareceres;
    }

    public void setTipoPareceres(Set<TipoPareceres> tipoPareceres) {
        this.tipoPareceres = tipoPareceres;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idParecer")
    protected Set<PareceresInstituicoes> pareceresInstituicoes;

    public Set<PareceresInstituicoes> getPareceresInstituicoes() {
        return pareceresInstituicoes;
    }

    public void setPareceresInstituicoes(Set<PareceresInstituicoes> pareceresInstituicoes) {
        this.pareceresInstituicoes = pareceresInstituicoes;
    }


}