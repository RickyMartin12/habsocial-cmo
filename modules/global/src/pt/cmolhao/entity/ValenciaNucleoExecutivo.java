package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.*;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idvalencias"))
})
@Table(name = "valencia_nucleo_executivo")
@Entity(name = "cmolhao_ValenciaNucleoExecutivo")
@NamePattern("%s - %s|descricaotecnica,morada")
public class ValenciaNucleoExecutivo extends BaseIntegerIdEntity implements Versioned, SoftDelete, Updatable, Creatable {
    private static final long serialVersionUID = -8542945557038618223L;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_ts")
    protected Date createTs;
    @Column(name = "created_by", length = 50)
    protected String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delete_ts")
    protected Date deleteTs;
    @Column(name = "deleted_by", length = 50)
    protected String deletedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_ts")
    protected Date updateTs;
    @Column(name = "updated_by", length = 50)
    protected String updatedBy;
    @Version
    @Column(name = "version")
    protected Integer version;
    @Column(name = "acordocapacidade")
    protected Integer acordocapacidade;
    @Column(name = "acordocomparticipacao", precision = 12, scale = 2)
    protected BigDecimal acordocomparticipacao;
    @Temporal(TemporalType.DATE)
    @Column(name = "acordodatacelebracao")
    protected Date acordodatacelebracao;
    @Column(name = "acordolistaespera")
    protected Integer acordolistaespera;
    @Column(name = "acordoutentesextrafeminino")
    protected Integer acordoutentesextrafeminino;
    @Column(name = "acordoutentesextramasculino")
    protected Integer acordoutentesextramasculino;
    @Column(name = "acordoutentesfeminino")
    protected Integer acordoutentesfeminino;
    @Column(name = "acordoutentesmasculino")
    protected Integer acordoutentesmasculino;
    @Column(name = "acordovagasfeminino")
    protected Integer acordovagasfeminino;
    @Column(name = "acordovagasmasculino")
    protected Integer acordovagasmasculino;
    @Column(name = "actividadescultural")
    protected Boolean actividadescultural;
    @Column(name = "actividadesdesportiva")
    protected Boolean actividadesdesportiva;
    @Column(name = "actividadeslazer")
    protected Boolean actividadeslazer;
    @Column(name = "actividadesoutras", length = 1000)
    protected String actividadesoutras;
    @Column(name = "actividadesrecreativas")
    protected Boolean actividadesrecreativas;
    @Column(name = "actividadessocial")
    protected Boolean actividadessocial;
    @Column(name = "certauditoriaexternacurso")
    protected Boolean certauditoriaexternacurso;
    @Column(name = "certauditoriasinternascurso")
    protected Boolean certauditoriasinternascurso;
    @Temporal(TemporalType.DATE)
    @Column(name = "certdatainicio")
    protected Date certdatainicio;
    @Temporal(TemporalType.DATE)
    @Column(name = "certdataprevista")
    protected Date certdataprevista;
    @Column(name = "certificacaoqualidade")
    protected Boolean certificacaoqualidade;
    @Column(name = "contactodirectortecnico", length = 250)
    protected String contactodirectortecnico;
    @Column(name = "contratacaoexternajardinagem")
    protected Boolean contratacaoexternajardinagem;
    @Column(name = "contratacaoexternalimpeza")
    protected Boolean contratacaoexternalimpeza;
    @Column(name = "contratacaoexternaoutras", length = 250)
    protected String contratacaoexternaoutras;
    @Column(name = "contratacaoexternarefeicoes")
    protected Boolean contratacaoexternarefeicoes;
    @Column(name = "coordenadagps", length = 45)
    protected String coordenadagps;
    @Column(name = "descricaotecnica", length = 250)
    protected String descricaotecnica;
    @Column(name = "directortecnico", length = 250)
    protected String directortecnico;
    @Column(name = "emal", length = 250)
    protected String emal;
    @Column(name = "fax", length = 45)
    protected String fax;
    @Column(name = "formacaoprevia", length = 250)
    protected String formacaoprevia;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariofimsemanafimmanha")
    protected Date horariofimsemanafimmanha;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariofimsemanafimtarde")
    protected Date horariofimsemanafimtarde;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariofimsemanainiciomanha")
    protected Date horariofimsemanainiciomanha;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariofimsemanainiciotarde")
    protected Date horariofimsemanainiciotarde;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariosemanafimmanha")
    protected Date horariosemanafimmanha;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariosemanafimtarde")
    protected Date horariosemanafimtarde;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariosemanainiciomanha")
    protected Date horariosemanainiciomanha;
    @Temporal(TemporalType.TIME)
    @Column(name = "horariosemanainiciotarde")
    protected Date horariosemanainiciotarde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_res_social")
    protected RespostaSocial idResSocial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idinstituicao")
    protected InstituicoesNucleoExecutivo idinstituicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipovalencia")
    protected Tiposvalencia idtipovalencia;


    @Column(name = "morada", length = 1000)
    protected String morada;
    @Column(name = "nregsegsocial", length = 45)
    protected String nregsegsocial;
    @Column(name = "recursostecnicoscomplementares", length = 1000)
    protected String recursostecnicoscomplementares;
    @Column(name = "servicosespecializados", length = 1000)
    protected String servicosespecializados;
    @Column(name = "telefone", length = 45)
    protected String telefone;
    @Column(name = "tipoacordosegsocial", length = 45)
    protected String tipoacordosegsocial;
    @Column(name = "transporte")
    protected Boolean transporte;
    @Column(name = "transportecapacidade", length = 45)
    protected String transportecapacidade;
    @Column(name = "transporteproprio")
    protected Boolean transporteproprio;
    @Column(name = "url", length = 250)
    protected String url;
    @Column(name = "utentesfuzeta")
    protected Integer utentesfuzeta;
    @Column(name = "utentesmoncarapacho")
    protected Integer utentesmoncarapacho;
    @Column(name = "utentesolhao")
    protected Integer utentesolhao;
    @Column(name = "utentespechao")
    protected Integer utentespechao;
    @Column(name = "utentesquelfes")
    protected Integer utentesquelfes;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idValencia")
    protected Set<AjudasTecnicasNucleoExecutivo> ajudasTecnicasNucleoExecutivos;
    public Set<AjudasTecnicasNucleoExecutivo> getAjudasTecnicasNucleoExecutivos() {
        return ajudasTecnicasNucleoExecutivos;
    }
    public void setAjudasTecnicasNucleoExecutivos(Set<AjudasTecnicasNucleoExecutivo> ajudasTecnicasNucleoExecutivos) {
        this.ajudasTecnicasNucleoExecutivos = ajudasTecnicasNucleoExecutivos;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idvalencia")
    protected Set<FotosValenciasNucleoExecutivo> fotosValenciasNucleoExecutivos;
    public Set<FotosValenciasNucleoExecutivo> getFotosValenciasNucleoExecutivos() {
        return fotosValenciasNucleoExecutivos;
    }
    public void setFotosValenciasNucleoExecutivos(Set<FotosValenciasNucleoExecutivo> fotosValenciasNucleoExecutivos) {
        this.fotosValenciasNucleoExecutivos = fotosValenciasNucleoExecutivos;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idvalencia")
    protected Set<LocalizacaoNucleoExecutivo> localizacaoNucleoExecutivos;
    public Set<LocalizacaoNucleoExecutivo> getLocalizacaoNucleoExecutivos() {
        return localizacaoNucleoExecutivos;
    }
    public void setLocalizacaoNucleoExecutivos(Set<LocalizacaoNucleoExecutivo> localizacaoNucleoExecutivos) {
        this.localizacaoNucleoExecutivos = localizacaoNucleoExecutivos;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idValencia")
    protected Set<UtentesOutrosConcelhosNucleoExecutivo> utentesOutrosConcelhosNucleoExecutivos;
    public Set<UtentesOutrosConcelhosNucleoExecutivo> getUtentesOutrosConcelhosNucleoExecutivos() {
        return utentesOutrosConcelhosNucleoExecutivos;
    }
    public void setUtentesOutrosConcelhosNucleoExecutivos(Set<UtentesOutrosConcelhosNucleoExecutivo> utentesOutrosConcelhosNucleoExecutivos) {
        this.utentesOutrosConcelhosNucleoExecutivos = utentesOutrosConcelhosNucleoExecutivos;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idValencia")
    protected Set<PessoalAuxiliarNucleoExecutivo> pessoalAuxiliarNucleoExecutivos;
    public Set<PessoalAuxiliarNucleoExecutivo> getPessoalAuxiliarNucleoExecutivos() {
        return pessoalAuxiliarNucleoExecutivos;
    }
    public void setPessoalAuxiliarNucleoExecutivos(Set<PessoalAuxiliarNucleoExecutivo> pessoalAuxiliarNucleoExecutivos) {
        this.pessoalAuxiliarNucleoExecutivos = pessoalAuxiliarNucleoExecutivos;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idValencia")
    protected Set<PessoalTecnicoNucleoExecutivo> pessoalTecnicoNucleoExecutivos;
    public Set<PessoalTecnicoNucleoExecutivo> getPessoalTecnicoNucleoExecutivos() {
        return pessoalTecnicoNucleoExecutivos;
    }
    public void setPessoalTecnicoNucleoExecutivos(Set<PessoalTecnicoNucleoExecutivo> pessoalTecnicoNucleoExecutivos) {
        this.pessoalTecnicoNucleoExecutivos = pessoalTecnicoNucleoExecutivos;
    }





    @Override
    public Boolean isDeleted() {
        return deleteTs != null;
    }

    public Integer getUtentesquelfes() {
        return utentesquelfes;
    }

    public void setUtentesquelfes(Integer utentesquelfes) {
        this.utentesquelfes = utentesquelfes;
    }

    public Integer getUtentespechao() {
        return utentespechao;
    }

    public void setUtentespechao(Integer utentespechao) {
        this.utentespechao = utentespechao;
    }

    public Integer getUtentesolhao() {
        return utentesolhao;
    }

    public void setUtentesolhao(Integer utentesolhao) {
        this.utentesolhao = utentesolhao;
    }

    public Integer getUtentesmoncarapacho() {
        return utentesmoncarapacho;
    }

    public void setUtentesmoncarapacho(Integer utentesmoncarapacho) {
        this.utentesmoncarapacho = utentesmoncarapacho;
    }

    public Integer getUtentesfuzeta() {
        return utentesfuzeta;
    }

    public void setUtentesfuzeta(Integer utentesfuzeta) {
        this.utentesfuzeta = utentesfuzeta;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getTransporteproprio() {
        return transporteproprio;
    }

    public void setTransporteproprio(Boolean transporteproprio) {
        this.transporteproprio = transporteproprio;
    }

    public String getTransportecapacidade() {
        return transportecapacidade;
    }

    public void setTransportecapacidade(String transportecapacidade) {
        this.transportecapacidade = transportecapacidade;
    }

    public Boolean getTransporte() {
        return transporte;
    }

    public void setTransporte(Boolean transporte) {
        this.transporte = transporte;
    }

    public String getTipoacordosegsocial() {
        return tipoacordosegsocial;
    }

    public void setTipoacordosegsocial(String tipoacordosegsocial) {
        this.tipoacordosegsocial = tipoacordosegsocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getServicosespecializados() {
        return servicosespecializados;
    }

    public void setServicosespecializados(String servicosespecializados) {
        this.servicosespecializados = servicosespecializados;
    }

    public String getRecursostecnicoscomplementares() {
        return recursostecnicoscomplementares;
    }

    public void setRecursostecnicoscomplementares(String recursostecnicoscomplementares) {
        this.recursostecnicoscomplementares = recursostecnicoscomplementares;
    }

    public String getNregsegsocial() {
        return nregsegsocial;
    }

    public void setNregsegsocial(String nregsegsocial) {
        this.nregsegsocial = nregsegsocial;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }



    public Tiposvalencia getIdtipovalencia() {
        return idtipovalencia;
    }

    public void setIdtipovalencia(Tiposvalencia idtipovalencia) {
        this.idtipovalencia = idtipovalencia;
    }

    public InstituicoesNucleoExecutivo getIdinstituicao() {
        return idinstituicao;
    }

    public void setIdinstituicao(InstituicoesNucleoExecutivo idinstituicao) {
        this.idinstituicao = idinstituicao;
    }

    public RespostaSocial getIdResSocial() {
        return idResSocial;
    }

    public void setIdResSocial(RespostaSocial idResSocial) {
        this.idResSocial = idResSocial;
    }



    public Date getHorariosemanainiciotarde() {
        return horariosemanainiciotarde;
    }

    public void setHorariosemanainiciotarde(Date horariosemanainiciotarde) {
        this.horariosemanainiciotarde = horariosemanainiciotarde;
    }

    public Date getHorariosemanainiciomanha() {
        return horariosemanainiciomanha;
    }

    public void setHorariosemanainiciomanha(Date horariosemanainiciomanha) {
        this.horariosemanainiciomanha = horariosemanainiciomanha;
    }

    public Date getHorariosemanafimtarde() {
        return horariosemanafimtarde;
    }

    public void setHorariosemanafimtarde(Date horariosemanafimtarde) {
        this.horariosemanafimtarde = horariosemanafimtarde;
    }

    public Date getHorariosemanafimmanha() {
        return horariosemanafimmanha;
    }

    public void setHorariosemanafimmanha(Date horariosemanafimmanha) {
        this.horariosemanafimmanha = horariosemanafimmanha;
    }

    public Date getHorariofimsemanainiciotarde() {
        return horariofimsemanainiciotarde;
    }

    public void setHorariofimsemanainiciotarde(Date horariofimsemanainiciotarde) {
        this.horariofimsemanainiciotarde = horariofimsemanainiciotarde;
    }

    public Date getHorariofimsemanainiciomanha() {
        return horariofimsemanainiciomanha;
    }

    public void setHorariofimsemanainiciomanha(Date horariofimsemanainiciomanha) {
        this.horariofimsemanainiciomanha = horariofimsemanainiciomanha;
    }

    public Date getHorariofimsemanafimtarde() {
        return horariofimsemanafimtarde;
    }

    public void setHorariofimsemanafimtarde(Date horariofimsemanafimtarde) {
        this.horariofimsemanafimtarde = horariofimsemanafimtarde;
    }

    public Date getHorariofimsemanafimmanha() {
        return horariofimsemanafimmanha;
    }

    public void setHorariofimsemanafimmanha(Date horariofimsemanafimmanha) {
        this.horariofimsemanafimmanha = horariofimsemanafimmanha;
    }

    public String getFormacaoprevia() {
        return formacaoprevia;
    }

    public void setFormacaoprevia(String formacaoprevia) {
        this.formacaoprevia = formacaoprevia;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmal() {
        return emal;
    }

    public void setEmal(String emal) {
        this.emal = emal;
    }

    public String getDirectortecnico() {
        return directortecnico;
    }

    public void setDirectortecnico(String directortecnico) {
        this.directortecnico = directortecnico;
    }

    public String getDescricaotecnica() {
        return descricaotecnica;
    }

    public void setDescricaotecnica(String descricaotecnica) {
        this.descricaotecnica = descricaotecnica;
    }

    public String getCoordenadagps() {
        return coordenadagps;
    }

    public void setCoordenadagps(String coordenadagps) {
        this.coordenadagps = coordenadagps;
    }

    public Boolean getContratacaoexternarefeicoes() {
        return contratacaoexternarefeicoes;
    }

    public void setContratacaoexternarefeicoes(Boolean contratacaoexternarefeicoes) {
        this.contratacaoexternarefeicoes = contratacaoexternarefeicoes;
    }

    public String getContratacaoexternaoutras() {
        return contratacaoexternaoutras;
    }

    public void setContratacaoexternaoutras(String contratacaoexternaoutras) {
        this.contratacaoexternaoutras = contratacaoexternaoutras;
    }

    public Boolean getContratacaoexternalimpeza() {
        return contratacaoexternalimpeza;
    }

    public void setContratacaoexternalimpeza(Boolean contratacaoexternalimpeza) {
        this.contratacaoexternalimpeza = contratacaoexternalimpeza;
    }

    public Boolean getContratacaoexternajardinagem() {
        return contratacaoexternajardinagem;
    }

    public void setContratacaoexternajardinagem(Boolean contratacaoexternajardinagem) {
        this.contratacaoexternajardinagem = contratacaoexternajardinagem;
    }

    public String getContactodirectortecnico() {
        return contactodirectortecnico;
    }

    public void setContactodirectortecnico(String contactodirectortecnico) {
        this.contactodirectortecnico = contactodirectortecnico;
    }

    public Boolean getCertificacaoqualidade() {
        return certificacaoqualidade;
    }

    public void setCertificacaoqualidade(Boolean certificacaoqualidade) {
        this.certificacaoqualidade = certificacaoqualidade;
    }

    public Date getCertdataprevista() {
        return certdataprevista;
    }

    public void setCertdataprevista(Date certdataprevista) {
        this.certdataprevista = certdataprevista;
    }

    public Date getCertdatainicio() {
        return certdatainicio;
    }

    public void setCertdatainicio(Date certdatainicio) {
        this.certdatainicio = certdatainicio;
    }

    public Boolean getCertauditoriasinternascurso() {
        return certauditoriasinternascurso;
    }

    public void setCertauditoriasinternascurso(Boolean certauditoriasinternascurso) {
        this.certauditoriasinternascurso = certauditoriasinternascurso;
    }

    public Boolean getCertauditoriaexternacurso() {
        return certauditoriaexternacurso;
    }

    public void setCertauditoriaexternacurso(Boolean certauditoriaexternacurso) {
        this.certauditoriaexternacurso = certauditoriaexternacurso;
    }

    public Boolean getActividadessocial() {
        return actividadessocial;
    }

    public void setActividadessocial(Boolean actividadessocial) {
        this.actividadessocial = actividadessocial;
    }

    public Boolean getActividadesrecreativas() {
        return actividadesrecreativas;
    }

    public void setActividadesrecreativas(Boolean actividadesrecreativas) {
        this.actividadesrecreativas = actividadesrecreativas;
    }

    public String getActividadesoutras() {
        return actividadesoutras;
    }

    public void setActividadesoutras(String actividadesoutras) {
        this.actividadesoutras = actividadesoutras;
    }

    public Boolean getActividadeslazer() {
        return actividadeslazer;
    }

    public void setActividadeslazer(Boolean actividadeslazer) {
        this.actividadeslazer = actividadeslazer;
    }

    public Boolean getActividadesdesportiva() {
        return actividadesdesportiva;
    }

    public void setActividadesdesportiva(Boolean actividadesdesportiva) {
        this.actividadesdesportiva = actividadesdesportiva;
    }

    public Boolean getActividadescultural() {
        return actividadescultural;
    }

    public void setActividadescultural(Boolean actividadescultural) {
        this.actividadescultural = actividadescultural;
    }

    public Integer getAcordovagasmasculino() {
        return acordovagasmasculino;
    }

    public void setAcordovagasmasculino(Integer acordovagasmasculino) {
        this.acordovagasmasculino = acordovagasmasculino;
    }

    public Integer getAcordovagasfeminino() {
        return acordovagasfeminino;
    }

    public void setAcordovagasfeminino(Integer acordovagasfeminino) {
        this.acordovagasfeminino = acordovagasfeminino;
    }

    public Integer getAcordoutentesmasculino() {
        return acordoutentesmasculino;
    }

    public void setAcordoutentesmasculino(Integer acordoutentesmasculino) {
        this.acordoutentesmasculino = acordoutentesmasculino;
    }

    public Integer getAcordoutentesfeminino() {
        return acordoutentesfeminino;
    }

    public void setAcordoutentesfeminino(Integer acordoutentesfeminino) {
        this.acordoutentesfeminino = acordoutentesfeminino;
    }

    public Integer getAcordoutentesextramasculino() {
        return acordoutentesextramasculino;
    }

    public void setAcordoutentesextramasculino(Integer acordoutentesextramasculino) {
        this.acordoutentesextramasculino = acordoutentesextramasculino;
    }

    public Integer getAcordoutentesextrafeminino() {
        return acordoutentesextrafeminino;
    }

    public void setAcordoutentesextrafeminino(Integer acordoutentesextrafeminino) {
        this.acordoutentesextrafeminino = acordoutentesextrafeminino;
    }

    public Integer getAcordolistaespera() {
        return acordolistaespera;
    }

    public void setAcordolistaespera(Integer acordolistaespera) {
        this.acordolistaespera = acordolistaespera;
    }

    public Date getAcordodatacelebracao() {
        return acordodatacelebracao;
    }

    public void setAcordodatacelebracao(Date acordodatacelebracao) {
        this.acordodatacelebracao = acordodatacelebracao;
    }

    public BigDecimal getAcordocomparticipacao() {
        return acordocomparticipacao;
    }

    public void setAcordocomparticipacao(BigDecimal acordocomparticipacao) {
        this.acordocomparticipacao = acordocomparticipacao;
    }

    public Integer getAcordocapacidade() {
        return acordocapacidade;
    }

    public void setAcordocapacidade(Integer acordocapacidade) {
        this.acordocapacidade = acordocapacidade;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdateTs() {
        return updateTs;
    }

    @Override
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    @Override
    public String getDeletedBy() {
        return deletedBy;
    }

    @Override
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Override
    public Date getDeleteTs() {
        return deleteTs;
    }

    @Override
    public void setDeleteTs(Date deleteTs) {
        this.deleteTs = deleteTs;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreateTs() {
        return createTs;
    }

    @Override
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}