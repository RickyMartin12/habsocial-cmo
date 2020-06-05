package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|tipoRendimento")
@Table(name = "tipo_rendimento")
@Entity(name = "cmolhao_TipoRendimento")
public class TipoRendimento extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2651618778251690087L;
    @Column(name = "tipo_rendimento")
    protected String tipoRendimento;

    public String getTipoRendimento() {
        return tipoRendimento;
    }

    public void setTipoRendimento(String tipoRendimento) {
        this.tipoRendimento = tipoRendimento;
    }
}