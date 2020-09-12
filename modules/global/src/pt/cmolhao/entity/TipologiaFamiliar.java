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
@NamePattern("%s - %s|tipologiaFamiliar,tipologiaFamiliarEspecifica")
@Table(name = "tipologia_familiar")
@Entity(name = "cmolhao_TipologiaFamiliar")

public class TipologiaFamiliar extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2457538154877594696L;
    @Column(name = "tipologia_familiar")
    protected String tipologiaFamiliar;
    @Column(name = "tipologia_familiar_especifica")
    protected String tipologiaFamiliarEspecifica;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTipologiaFamiliar")
    protected Set<Utente> utentes;

    public Set<Utente> getUtentes() {
        return utentes;
    }

    public void setUtentes(Set<Utente> utentes) {
        this.utentes = utentes;
    }

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