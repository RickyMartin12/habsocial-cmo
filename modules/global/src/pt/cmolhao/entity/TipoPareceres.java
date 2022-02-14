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
@Table(name = "tipo_pareceres")
@Entity(name = "cmolhao_TipoPareceres")
public class TipoPareceres extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 3981344030052111592L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_pareceres")
    protected Pareceres idTipoPareceres;
    @Lob
    @Column(name = "nome")
    protected String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pareceres getIdTipoPareceres() {
        return idTipoPareceres;
    }

    public void setIdTipoPareceres(Pareceres idTipoPareceres) {
        this.idTipoPareceres = idTipoPareceres;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTipoParecer")
    protected Set<PareceresInstituicoes> pareceresInstituicoes;

    public Set<PareceresInstituicoes> getPareceresInstituicoes() {
        return pareceresInstituicoes;
    }

    public void setPareceresInstituicoes(Set<PareceresInstituicoes> pareceresInstituicoes) {
        this.pareceresInstituicoes = pareceresInstituicoes;
    }



}