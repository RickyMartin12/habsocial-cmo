package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'dbView':true,'imported':true}")
@Table(name = "localizacoes_claso")
@Entity(name = "cmolhao_LocalizacoesClaso")
public class LocalizacoesClaso extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 9054947526561417484L;
    @Column(name = "coord")
    protected String coord;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvalencia")
    protected ValenciasClaso idvalencia;

    public ValenciasClaso getIdvalencia() {
        return idvalencia;
    }

    public void setIdvalencia(ValenciasClaso idvalencia) {
        this.idvalencia = idvalencia;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }
}