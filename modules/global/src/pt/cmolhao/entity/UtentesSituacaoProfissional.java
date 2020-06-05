package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'imported':true}")
@Table(name = "utentes_situacao_profissional")
@Entity(name = "cmolhao_UtentesSituacaoProfissional")
public class UtentesSituacaoProfissional extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 7540540967168338237L;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_situacao_profissional")
    protected Date dataSituacaoProfissional;
    @Column(name = "situacao_profissional")
    protected String situacaoProfissional;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id")
    protected Utente utente;

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getSituacaoProfissional() {
        return situacaoProfissional;
    }

    public void setSituacaoProfissional(String situacaoProfissional) {
        this.situacaoProfissional = situacaoProfissional;
    }

    public Date getDataSituacaoProfissional() {
        return dataSituacaoProfissional;
    }

    public void setDataSituacaoProfissional(Date dataSituacaoProfissional) {
        this.dataSituacaoProfissional = dataSituacaoProfissional;
    }
}