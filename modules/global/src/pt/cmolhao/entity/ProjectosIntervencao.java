package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idprojectosintervencao"))
})
@NamePattern("%s|idinstituicao")
@Table(name = "projectos_intervencao")
@Entity(name = "cmolhao_ProjectosIntervencao")
public class ProjectosIntervencao extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 6953221178067886824L;


    @Column(name = "adultos")
    protected Boolean adultos;
    @Column(name = "comunidade")
    protected Boolean comunidade;
    @Column(name = "criancas")
    protected Boolean criancas;
    @Column(name = "deficientes")
    protected Boolean deficientes;
    @Column(name = "deficientesapoiodomiciliario")
    protected Boolean deficientesapoiodomiciliario;
    @Column(name = "deficientescao")
    protected Boolean deficientescao;
    @Column(name = "deficienteslarresidencial")
    protected Boolean deficienteslarresidencial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idinstituicao")
    protected pt.cmolhao.entity.Instituicoes idinstituicao;

    @Column(name = "idosos")
    protected Boolean idosos;
    @Column(name = "idososapoiodomiciliario")
    protected Boolean idososapoiodomiciliario;
    @Column(name = "idososcat")
    protected Boolean idososcat;
    @Column(name = "idososcentrodia")
    protected Boolean idososcentrodia;
    @Column(name = "idososlar")
    protected Boolean idososlar;
    @Column(name = "idosospequenolar")
    protected Boolean idosospequenolar;
    @Column(name = "jovens")
    protected Boolean jovens;
    @Column(name = "jovensatl")
    protected Boolean jovensatl;
    @Column(name = "jovenscat")
    protected Boolean jovenscat;
    @Column(name = "jovenscreche")
    protected Boolean jovenscreche;
    @Column(name = "jovensjardiminfancia")
    protected Boolean jovensjardiminfancia;
    @Column(name = "outrosgrupos", length = 250)
    protected String outrosgrupos;
    @Column(name = "pretendealargarservicos")
    protected Boolean pretendealargarservicos;
    @Column(name = "projectosemaprovacao")
    protected Boolean projectosemaprovacao;

    public Boolean getProjectosemaprovacao() {
        return projectosemaprovacao;
    }

    public void setProjectosemaprovacao(Boolean projectosemaprovacao) {
        this.projectosemaprovacao = projectosemaprovacao;
    }

    public Boolean getPretendealargarservicos() {
        return pretendealargarservicos;
    }

    public void setPretendealargarservicos(Boolean pretendealargarservicos) {
        this.pretendealargarservicos = pretendealargarservicos;
    }

    public String getOutrosgrupos() {
        return outrosgrupos;
    }

    public void setOutrosgrupos(String outrosgrupos) {
        this.outrosgrupos = outrosgrupos;
    }

    public Boolean getJovensjardiminfancia() {
        return jovensjardiminfancia;
    }

    public void setJovensjardiminfancia(Boolean jovensjardiminfancia) {
        this.jovensjardiminfancia = jovensjardiminfancia;
    }

    public Boolean getJovenscreche() {
        return jovenscreche;
    }

    public void setJovenscreche(Boolean jovenscreche) {
        this.jovenscreche = jovenscreche;
    }

    public Boolean getJovenscat() {
        return jovenscat;
    }

    public void setJovenscat(Boolean jovenscat) {
        this.jovenscat = jovenscat;
    }

    public Boolean getJovensatl() {
        return jovensatl;
    }

    public void setJovensatl(Boolean jovensatl) {
        this.jovensatl = jovensatl;
    }

    public Boolean getJovens() {
        return jovens;
    }

    public void setJovens(Boolean jovens) {
        this.jovens = jovens;
    }

    public Boolean getIdosospequenolar() {
        return idosospequenolar;
    }

    public void setIdosospequenolar(Boolean idosospequenolar) {
        this.idosospequenolar = idosospequenolar;
    }

    public Boolean getIdososlar() {
        return idososlar;
    }

    public void setIdososlar(Boolean idososlar) {
        this.idososlar = idososlar;
    }

    public Boolean getIdososcentrodia() {
        return idososcentrodia;
    }

    public void setIdososcentrodia(Boolean idososcentrodia) {
        this.idososcentrodia = idososcentrodia;
    }

    public Boolean getIdososcat() {
        return idososcat;
    }

    public void setIdososcat(Boolean idososcat) {
        this.idososcat = idososcat;
    }

    public Boolean getIdososapoiodomiciliario() {
        return idososapoiodomiciliario;
    }

    public void setIdososapoiodomiciliario(Boolean idososapoiodomiciliario) {
        this.idososapoiodomiciliario = idososapoiodomiciliario;
    }

    public Boolean getIdosos() {
        return idosos;
    }

    public void setIdosos(Boolean idosos) {
        this.idosos = idosos;
    }

    public pt.cmolhao.entity.Instituicoes getIdinstituicao() {
        return idinstituicao;
    }

    public void setIdinstituicao(pt.cmolhao.entity.Instituicoes idinstituicao) {
        this.idinstituicao = idinstituicao;
    }

    public Boolean getDeficienteslarresidencial() {
        return deficienteslarresidencial;
    }

    public void setDeficienteslarresidencial(Boolean deficienteslarresidencial) {
        this.deficienteslarresidencial = deficienteslarresidencial;
    }

    public Boolean getDeficientescao() {
        return deficientescao;
    }

    public void setDeficientescao(Boolean deficientescao) {
        this.deficientescao = deficientescao;
    }

    public Boolean getDeficientesapoiodomiciliario() {
        return deficientesapoiodomiciliario;
    }

    public void setDeficientesapoiodomiciliario(Boolean deficientesapoiodomiciliario) {
        this.deficientesapoiodomiciliario = deficientesapoiodomiciliario;
    }

    public Boolean getDeficientes() {
        return deficientes;
    }

    public void setDeficientes(Boolean deficientes) {
        this.deficientes = deficientes;
    }

    public Boolean getCriancas() {
        return criancas;
    }

    public void setCriancas(Boolean criancas) {
        this.criancas = criancas;
    }

    public Boolean getComunidade() {
        return comunidade;
    }

    public void setComunidade(Boolean comunidade) {
        this.comunidade = comunidade;
    }

    public Boolean getAdultos() {
        return adultos;
    }

    public void setAdultos(Boolean adultos) {
        this.adultos = adultos;
    }
}