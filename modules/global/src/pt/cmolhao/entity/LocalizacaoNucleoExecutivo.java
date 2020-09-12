package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'dbView':true,'imported':true}")
@Table(name = "localizacao_nucleo_executivo")
@Entity(name = "cmolhao_LocalizacaoNucleoExecutivo")
public class LocalizacaoNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -3431073495403040389L;
    @Column(name = "coord")
    protected String coord;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvalencia")
    protected ValenciaNucleoExecutivo idvalencia;

    public ValenciaNucleoExecutivo getIdvalencia() {
        return idvalencia;
    }

    public void setIdvalencia(ValenciaNucleoExecutivo idvalencia) {
        this.idvalencia = idvalencia;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }
}