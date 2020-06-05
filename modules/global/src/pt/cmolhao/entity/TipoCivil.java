package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|tipo")
@Table(name = "tipo_civil")
@Entity(name = "cmolhao_TipoCivil")
public class TipoCivil extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -6153209508428054822L;
    @Column(name = "tipo")
    protected String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}