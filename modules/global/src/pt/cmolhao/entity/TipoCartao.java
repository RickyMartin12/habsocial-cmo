package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@DesignSupport("{'imported':true}")
@NamePattern("%s|tipo")
@Table(name = "tipo_cartao")
@Entity(name = "cmolhao_TipoCartao")
public class TipoCartao extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 7723450506300169722L;
    @Lob
    @Column(name = "tipo")
    protected String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTipoCartao")
    protected Set<Utente> utentes;

    public Set<Utente> getUtentes() {
        return utentes;
    }

    public void setUtentes(Set<Utente> utentes) {
        this.utentes = utentes;
    }
}