package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|descricao")
@Table(name = "equipamento")
@Entity(name = "cmolhao_Equipamento")
public class Equipamento extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 5790418328307745763L;
    @Column(name = "descricao")
    protected String descricao;
    @Column(name = "obs_gerais")
    protected String obsGerais;

    public String getObsGerais() {
        return obsGerais;
    }

    public void setObsGerais(String obsGerais) {
        this.obsGerais = obsGerais;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}