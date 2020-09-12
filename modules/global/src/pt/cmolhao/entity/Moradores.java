package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'imported':true}")
@Table(name = "moradores")
@Entity(name = "cmolhao_Moradores")
public class Moradores extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -7672239516716378184L;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    protected Date dataInicio;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fim")
    protected Date dateFim;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitacao_social_id")
    protected HabitacaoSocial habitacaoSocial;


    @Column(name = "morada_social")
    protected String moradaSocial;
    @Column(name = "obs_conf")
    protected String obsConf;
    @Column(name = "obs_gerais")
    protected String obsGerais;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id")
    protected Utente utente;

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getObsGerais() {
        return obsGerais;
    }

    public void setObsGerais(String obsGerais) {
        this.obsGerais = obsGerais;
    }

    public String getObsConf() {
        return obsConf;
    }

    public void setObsConf(String obsConf) {
        this.obsConf = obsConf;
    }

    public String getMoradaSocial() {
        return moradaSocial;
    }

    public void setMoradaSocial(String moradaSocial) {
        this.moradaSocial = moradaSocial;
    }

    public HabitacaoSocial getHabitacaoSocial() {
        return habitacaoSocial;
    }

    public void setHabitacaoSocial(HabitacaoSocial habitacaoSocial) {
        this.habitacaoSocial = habitacaoSocial;
    }

    public Date getDateFim() {
        return dateFim;
    }

    public void setDateFim(Date dateFim) {
        this.dateFim = dateFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
}