package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|descricaoTipoAjuda")
@Table(name = "tipo_ajuda")
@Entity(name = "cmolhao_TipoAjuda")
public class TipoAjuda extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -5296618008809353376L;
    @Column(name = "descricao_tipo_ajuda")
    protected String descricaoTipoAjuda;

    public String getDescricaoTipoAjuda() {
        return descricaoTipoAjuda;
    }

    public void setDescricaoTipoAjuda(String descricaoTipoAjuda) {
        this.descricaoTipoAjuda = descricaoTipoAjuda;
    }
}