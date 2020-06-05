package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'imported':true}")
@Table(name = "situacao_utente")
@Entity(name = "cmolhao_SituacaoUtente")
public class SituacaoUtente extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 6034454400662794893L;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim")
    protected Date dataFim;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    protected Date dataInicio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipos_situacao_utentes")
    protected TiposSituacoesUtentes idTiposSituacaoUtentes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    protected Utente idUtente;
    @Column(name = "obs_gerais")
    protected String obsGerais;
    @Column(name = "obser_conf")
    protected String obserConf;

    public String getObserConf() {
        return obserConf;
    }

    public void setObserConf(String obserConf) {
        this.obserConf = obserConf;
    }

    public String getObsGerais() {
        return obsGerais;
    }

    public void setObsGerais(String obsGerais) {
        this.obsGerais = obsGerais;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public TiposSituacoesUtentes getIdTiposSituacaoUtentes() {
        return idTiposSituacaoUtentes;
    }

    public void setIdTiposSituacaoUtentes(TiposSituacoesUtentes idTiposSituacaoUtentes) {
        this.idTiposSituacaoUtentes = idTiposSituacaoUtentes;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}