package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idinstituicao"))
})
@NamePattern("%s|descricao")
@Table(name = "instituicoes")
@Entity(name = "cmolhao_Instituicoes")
public class Instituicoes extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 3610667108364975924L;
    @Column(name = "cae", length = 45)
    protected String cae;
    @Column(name = "clasolhao")
    protected Boolean clasolhao;
    @Column(name = "contactopresidentedireccao", length = 250)
    protected String contactopresidentedireccao;
    @Column(name = "contactoresponsavelservico", length = 250)
    protected String contactoresponsavelservico;
    @Column(name = "coordenadasgps", length = 45)
    protected String coordenadasgps;
    @Column(name = "cpcj")
    protected Boolean cpcj;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_adesao")
    protected Date dataAdesao;
    @Column(name = "descricao", length = 250)
    protected String descricao;
    @Column(name = "email", length = 250)
    protected String email;
    @Column(name = "fax", length = 45)
    protected String fax;
    @Column(name = "moradacompleta", length = 250)
    protected String moradacompleta;
    @Column(name = "naturezajuridica", length = 45)
    protected String naturezajuridica;
    @Column(name = "niss", length = 11)
    protected String niss;
    @Column(name = "nrregistosegsocial", length = 45)
    protected String nrregistosegsocial;
    @Column(name = "outraredelocal")
    protected Boolean outraredelocal;
    @Column(name = "plataformatematica")
    protected Boolean plataformatematica;
    @Column(name = "presidentedireccao", length = 250)
    protected String presidentedireccao;
    @Column(name = "projectoscomunitarios")
    protected Boolean projectoscomunitarios;
    @Column(name = "quaisprojectoscomunitarios", length = 250)
    protected String quaisprojectoscomunitarios;
    @Column(name = "qualoutraredelocal", length = 250)
    protected String qualoutraredelocal;
    @Column(name = "responsavelservico", length = 250)
    protected String responsavelservico;
    @Column(name = "rsi")
    protected Boolean rsi;
    @Column(name = "telefone", length = 45)
    protected String telefone;
    @Column(name = "url", length = 250)
    protected String url;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idinstituicao")
    protected Set<Valencias> valencias;

    public Set<Valencias> getValencias() {
        return valencias;
    }

    public void setValencias(Set<Valencias> valencias) {
        this.valencias = valencias;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idInstituicao")
    protected Set<Apoios> apoios;

    public Set<Apoios> getApoios() {
        return apoios;
    }

    public void setApoios(Set<Apoios> apoios) {
        this.apoios = apoios;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idInstituicao")
    protected Set<Tecnico> tecnicos;

    public Set<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(Set<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idinstituicao")
    protected Set<ProjectosIntervencao> projectosIntervencaos;

    public Set<ProjectosIntervencao> getProjectosIntervencaos() {
        return projectosIntervencaos;
    }

    public void setProjectosIntervencaos(Set<ProjectosIntervencao> projectosIntervencaos) {
        this.projectosIntervencaos = projectosIntervencaos;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idInsituicao")
    protected Set<MembrosRedeTrabalho> membrosRedeTrabalhos;

    public Set<MembrosRedeTrabalho> getMembrosRedeTrabalhos() {
        return membrosRedeTrabalhos;
    }

    public void setMembrosRedeTrabalhos(Set<MembrosRedeTrabalho> membrosRedeTrabalhos) {
        this.membrosRedeTrabalhos = membrosRedeTrabalhos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getRsi() {
        return rsi;
    }

    public void setRsi(Boolean rsi) {
        this.rsi = rsi;
    }

    public String getResponsavelservico() {
        return responsavelservico;
    }

    public void setResponsavelservico(String responsavelservico) {
        this.responsavelservico = responsavelservico;
    }

    public String getQualoutraredelocal() {
        return qualoutraredelocal;
    }

    public void setQualoutraredelocal(String qualoutraredelocal) {
        this.qualoutraredelocal = qualoutraredelocal;
    }

    public String getQuaisprojectoscomunitarios() {
        return quaisprojectoscomunitarios;
    }

    public void setQuaisprojectoscomunitarios(String quaisprojectoscomunitarios) {
        this.quaisprojectoscomunitarios = quaisprojectoscomunitarios;
    }

    public Boolean getProjectoscomunitarios() {
        return projectoscomunitarios;
    }

    public void setProjectoscomunitarios(Boolean projectoscomunitarios) {
        this.projectoscomunitarios = projectoscomunitarios;
    }

    public String getPresidentedireccao() {
        return presidentedireccao;
    }

    public void setPresidentedireccao(String presidentedireccao) {
        this.presidentedireccao = presidentedireccao;
    }

    public Boolean getPlataformatematica() {
        return plataformatematica;
    }

    public void setPlataformatematica(Boolean plataformatematica) {
        this.plataformatematica = plataformatematica;
    }

    public Boolean getOutraredelocal() {
        return outraredelocal;
    }

    public void setOutraredelocal(Boolean outraredelocal) {
        this.outraredelocal = outraredelocal;
    }

    public String getNrregistosegsocial() {
        return nrregistosegsocial;
    }

    public void setNrregistosegsocial(String nrregistosegsocial) {
        this.nrregistosegsocial = nrregistosegsocial;
    }

    public String getNiss() {
        return niss;
    }

    public void setNiss(String niss) {
        this.niss = niss;
    }

    public String getNaturezajuridica() {
        return naturezajuridica;
    }

    public void setNaturezajuridica(String naturezajuridica) {
        this.naturezajuridica = naturezajuridica;
    }

    public String getMoradacompleta() {
        return moradacompleta;
    }

    public void setMoradacompleta(String moradacompleta) {
        this.moradacompleta = moradacompleta;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataAdesao() {
        return dataAdesao;
    }

    public void setDataAdesao(Date dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public Boolean getCpcj() {
        return cpcj;
    }

    public void setCpcj(Boolean cpcj) {
        this.cpcj = cpcj;
    }

    public String getCoordenadasgps() {
        return coordenadasgps;
    }

    public void setCoordenadasgps(String coordenadasgps) {
        this.coordenadasgps = coordenadasgps;
    }

    public String getContactoresponsavelservico() {
        return contactoresponsavelservico;
    }

    public void setContactoresponsavelservico(String contactoresponsavelservico) {
        this.contactoresponsavelservico = contactoresponsavelservico;
    }

    public String getContactopresidentedireccao() {
        return contactopresidentedireccao;
    }

    public void setContactopresidentedireccao(String contactopresidentedireccao) {
        this.contactopresidentedireccao = contactopresidentedireccao;
    }

    public Boolean getClasolhao() {
        return clasolhao;
    }

    public void setClasolhao(Boolean clasolhao) {
        this.clasolhao = clasolhao;
    }

    public String getCae() {
        return cae;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }
}