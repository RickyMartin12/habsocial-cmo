package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|descricao")
@Table(name = "habilitacoes_literarias")
@Entity(name = "cmolhao_HabilitacoesLiterarias")
public class HabilitacoesLiterarias extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -3672572851172091670L;
    @Column(name = "descricao")
    protected String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}