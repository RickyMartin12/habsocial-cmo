package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idinstituicao"))
})
@NamePattern("%s|descricao")
@Table(name = "instituicoes")
@Entity(name = "cmolhao_Instituicoes")
public class Instituicoes extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2173434840167193055L;
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