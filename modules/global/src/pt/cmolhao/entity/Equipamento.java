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
@NamePattern("%s|descricao")
@Table(name = "equipamento")
@Entity(name = "cmolhao_Equipamento")
public class Equipamento extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 5790418328307745763L;
    @Column(name = "descricao")
    protected String descricao;
    @Column(name = "obs_gerais")
    protected String obsGerais;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idEquipamento")
    protected Set<Apoios> apoios;

    public Set<Apoios> getApoios() {
        return apoios;
    }

    public void setApoios(Set<Apoios> apoios) {
        this.apoios = apoios;
    }

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