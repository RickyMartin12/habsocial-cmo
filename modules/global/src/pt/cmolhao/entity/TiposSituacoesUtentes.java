package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@DesignSupport("{'imported':true}")
@NamePattern("%s|descricaoSituacao")
@Table(name = "tipos_situacoes_utentes")
@Entity(name = "cmolhao_TiposSituacoesUtentes")
public class TiposSituacoesUtentes extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -7223380758722387329L;
    @Column(name = "descricao_situacao")
    protected String descricaoSituacao;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTiposSituacaoUtentes")
    protected Set<SituacaoUtente> situacaoUtentes;

    public Set<SituacaoUtente> getSituacaoUtentes() {
        return situacaoUtentes;
    }

    public void setSituacaoUtentes(Set<SituacaoUtente> situacaoUtentes) {
        this.situacaoUtentes = situacaoUtentes;
    }


    public String getDescricaoSituacao() {
        return descricaoSituacao;
    }

    public void setDescricaoSituacao(String descricaoSituacao) {
        this.descricaoSituacao = descricaoSituacao;
    }
}