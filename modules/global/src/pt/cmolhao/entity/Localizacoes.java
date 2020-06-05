package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@Table(name = "localizacoes")
@Entity(name = "cmolhao_Localizacoes")
public class Localizacoes extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -1184491443912905314L;
    @Column(name = "coord")
    protected String coord;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvalencia")
    protected Valencias idvalencia;

    public Valencias getIdvalencia() {
        return idvalencia;
    }

    public void setIdvalencia(Valencias idvalencia) {
        this.idvalencia = idvalencia;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }
}