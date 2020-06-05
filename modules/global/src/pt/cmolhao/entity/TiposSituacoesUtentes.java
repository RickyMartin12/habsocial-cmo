package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|descricaoSituacao")
@Table(name = "tipos_situacoes_utentes")
@Entity(name = "cmolhao_TiposSituacoesUtentes")
public class TiposSituacoesUtentes extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -7223380758722387329L;
    @Column(name = "descricao_situacao")
    protected String descricaoSituacao;

    public String getDescricaoSituacao() {
        return descricaoSituacao;
    }

    public void setDescricaoSituacao(String descricaoSituacao) {
        this.descricaoSituacao = descricaoSituacao;
    }
}