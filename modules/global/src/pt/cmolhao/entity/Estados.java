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
@NamePattern("%s|estadosProcessos")
@Table(name = "estados")
@Entity(name = "cmolhao_Estados")
public class Estados extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 8045826733255140118L;
    @Column(name = "estados_processos")
    protected String estadosProcessos;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idEstado")
    protected Set<Apoios> apoios;

    public Set<Apoios> getApoios() {
        return apoios;
    }

    public void setApoios(Set<Apoios> apoios) {
        this.apoios = apoios;
    }

    public String getEstadosProcessos() {
        return estadosProcessos;
    }

    public void setEstadosProcessos(String estadosProcessos) {
        this.estadosProcessos = estadosProcessos;
    }
}