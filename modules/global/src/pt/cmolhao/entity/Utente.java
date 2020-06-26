package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'imported':true}")
@NamePattern("( %s ) %s|numContribuinte,nome")
@Table(name = "utente")
@Entity(name = "cmolhao_Utente")
public class Utente extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 6959758886872812778L;
    @Column(name = "cert_uniao_europeia")
    protected String certUniaoEuropeia;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nasc")
    protected Date dataNasc;
    @Column(name = "deficiente")
    protected Boolean deficiente;
    @Column(name = "dependente")
    protected Boolean dependente;
    @Column(name = "email")
    protected String email;
    @Column(name = "estado_civil")
    protected String estadoCivil;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grau_escolaridade_id")
    protected GrauEscolaridade grauEscolaridade;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habilitacoes_id")
    protected HabilitacoesLiterarias habilitacoes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_civil")
    protected TipoCivil idTipoCivil;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipologia_familiar")
    protected TipologiaFamiliar idTipologiaFamiliar;
    @Column(name = "niss")
    protected String niss;
    @Column(name = "nome")
    protected String nome;
    @Column(name = "num_contribuinte")
    protected String numContribuinte;
    @Column(name = "num_id_civil")
    protected String numIdCivil;
    @Column(name = "obs_conf")
    protected String obsConf;
    @Column(name = "obs_gerais")
    protected String obsGerais;
    @Column(name = "pais_origem")
    protected String paisOrigem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissao_id")
    protected Profissao profissao;
    @Lob
    @Column(name = "telefone")
    protected String telefone;
    @Lob
    @Column(name = "\"telemóvel\"")
    protected String telem_vel;

    public String getTelem_vel() {
        return telem_vel;
    }

    public void setTelem_vel(String telem_vel) {
        this.telem_vel = telem_vel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
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

    public String getNumIdCivil() {
        return numIdCivil;
    }

    public void setNumIdCivil(String numIdCivil) {
        this.numIdCivil = numIdCivil;
    }

    public String getNumContribuinte() {
        return numContribuinte;
    }

    public void setNumContribuinte(String numContribuinte) {
        this.numContribuinte = numContribuinte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNiss() {
        return niss;
    }

    public void setNiss(String niss) {
        this.niss = niss;
    }

    public TipologiaFamiliar getIdTipologiaFamiliar() {
        return idTipologiaFamiliar;
    }

    public void setIdTipologiaFamiliar(TipologiaFamiliar idTipologiaFamiliar) {
        this.idTipologiaFamiliar = idTipologiaFamiliar;
    }

    public TipoCivil getIdTipoCivil() {
        return idTipoCivil;
    }

    public void setIdTipoCivil(TipoCivil idTipoCivil) {
        this.idTipoCivil = idTipoCivil;
    }

    public HabilitacoesLiterarias getHabilitacoes() {
        return habilitacoes;
    }

    public void setHabilitacoes(HabilitacoesLiterarias habilitacoes) {
        this.habilitacoes = habilitacoes;
    }

    public GrauEscolaridade getGrauEscolaridade() {
        return grauEscolaridade;
    }

    public void setGrauEscolaridade(GrauEscolaridade grauEscolaridade) {
        this.grauEscolaridade = grauEscolaridade;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDependente() {
        return dependente;
    }

    public void setDependente(Boolean dependente) {
        this.dependente = dependente;
    }

    public Boolean getDeficiente() {
        return deficiente;
    }

    public void setDeficiente(Boolean deficiente) {
        this.deficiente = deficiente;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCertUniaoEuropeia() {
        return certUniaoEuropeia;
    }

    public void setCertUniaoEuropeia(String certUniaoEuropeia) {
        this.certUniaoEuropeia = certUniaoEuropeia;
    }
}