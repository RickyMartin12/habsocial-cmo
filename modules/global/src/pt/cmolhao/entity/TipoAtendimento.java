package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|tipoAtendimento")
@Table(name = "tipo_atendimento")
@Entity(name = "cmolhao_TipoAtendimento")
public class TipoAtendimento extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 3117380234447049773L;
    @Column(name = "tipo_atendimento")
    protected String tipoAtendimento;

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }
}