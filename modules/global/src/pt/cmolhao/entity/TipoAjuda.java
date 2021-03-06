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
@NamePattern("%s|descricaoTipoAjuda")
@Table(name = "tipo_ajuda")
@Entity(name = "cmolhao_TipoAjuda")
public class TipoAjuda extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -5296618008809353376L;
    @Column(name = "descricao_tipo_ajuda")
    protected String descricaoTipoAjuda;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTipoapoio")
    protected Set<Apoios> apoios;

    public Set<Apoios> getApoios() {
        return apoios;
    }

    public void setApoios(Set<Apoios> apoios) {
        this.apoios = apoios;
    }

    public String getDescricaoTipoAjuda() {
        return descricaoTipoAjuda;
    }

    public void setDescricaoTipoAjuda(String descricaoTipoAjuda) {
        this.descricaoTipoAjuda = descricaoTipoAjuda;
    }
}