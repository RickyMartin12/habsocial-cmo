package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s - %s|tipologiaFamiliar,tipologiaFamiliarEspecifica")
@Table(name = "tipologia_familiar")
@Entity(name = "cmolhao_TipologiaFamiliar")

public class TipologiaFamiliar extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2457538154877594696L;
    @Column(name = "tipologia_familiar")
    protected String tipologiaFamiliar;
    @Column(name = "tipologia_familiar_especifica")
    protected String tipologiaFamiliarEspecifica;

    public String getTipologiaFamiliarEspecifica() {
        return tipologiaFamiliarEspecifica;
    }

    public void setTipologiaFamiliarEspecifica(String tipologiaFamiliarEspecifica) {
        this.tipologiaFamiliarEspecifica = tipologiaFamiliarEspecifica;
    }

    public String getTipologiaFamiliar() {
        return tipologiaFamiliar;
    }

    public void setTipologiaFamiliar(String tipologiaFamiliar) {
        this.tipologiaFamiliar = tipologiaFamiliar;
    }
}