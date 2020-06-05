package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|estadosProcessos")
@Table(name = "estados")
@Entity(name = "cmolhao_Estados")
public class Estados extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 8045826733255140118L;
    @Column(name = "estados_processos")
    protected String estadosProcessos;

    public String getEstadosProcessos() {
        return estadosProcessos;
    }

    public void setEstadosProcessos(String estadosProcessos) {
        this.estadosProcessos = estadosProcessos;
    }
}