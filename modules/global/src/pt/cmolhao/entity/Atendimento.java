package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@DesignSupport("{'imported':true}")
@NamePattern("%s, %s, %s|idUtente,idTecnico,idTipoAtendimento")

@Table(name = "atendimento")
@Entity(name = "cmolhao_Atendimento")
public class Atendimento extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -4437961964748870057L;
    @Column(name = "contactos_efetuados")
    protected String contactosEfetuados;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_atendimento")
    protected Date dataAtendimento;
    @JoinTable(name = "objetivos_atendimento",
            joinColumns = @JoinColumn(name = "id_atendimento"),
            inverseJoinColumns = @JoinColumn(name = "id_atendimento_objectivos"))
    @ManyToMany
    protected List<AtendimentoObjetivos> atendimentoObjetivos;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atendimento_encaminhamento")
    protected AtendimentoEncaminhamento idAtendimentoEncaminhamento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico")
    protected Tecnico idTecnico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_atendimento")
    protected TipoAtendimento idTipoAtendimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    protected Utente idUtente;
    @Column(name = "objetivo_outros")
    protected String objetivoOutros;
    @Column(name = "obs_conf")
    protected String obsConf;
    @Column(name = "obs_gerais")
    protected String obsGerais;
    @Column(name = "outros_encaminhamentos")
    protected String outrosEncaminhamentos;

    public List<AtendimentoObjetivos> getAtendimentoObjetivos() {
        return atendimentoObjetivos;
    }

    public void setAtendimentoObjetivos(List<AtendimentoObjetivos> atendimentoObjetivos) {
        this.atendimentoObjetivos = atendimentoObjetivos;
    }


    public String getOutrosEncaminhamentos() {
        return outrosEncaminhamentos;
    }

    public void setOutrosEncaminhamentos(String outrosEncaminhamentos) {
        this.outrosEncaminhamentos = outrosEncaminhamentos;
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

    public String getObjetivoOutros() {
        return objetivoOutros;
    }

    public void setObjetivoOutros(String objetivoOutros) {
        this.objetivoOutros = objetivoOutros;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public TipoAtendimento getIdTipoAtendimento() {
        return idTipoAtendimento;
    }

    public void setIdTipoAtendimento(TipoAtendimento idTipoAtendimento) {
        this.idTipoAtendimento = idTipoAtendimento;
    }

    public Tecnico getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Tecnico idTecnico) {
        this.idTecnico = idTecnico;
    }

    public AtendimentoEncaminhamento getIdAtendimentoEncaminhamento() {
        return idAtendimentoEncaminhamento;
    }

    public void setIdAtendimentoEncaminhamento(AtendimentoEncaminhamento idAtendimentoEncaminhamento) {
        this.idAtendimentoEncaminhamento = idAtendimentoEncaminhamento;
    }

    public Date getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Date dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getContactosEfetuados() {
        return contactosEfetuados;
    }

    public void setContactosEfetuados(String contactosEfetuados) {
        this.contactosEfetuados = contactosEfetuados;
    }
}