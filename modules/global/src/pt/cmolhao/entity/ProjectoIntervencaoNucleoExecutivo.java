package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idprojectosintervencao"))
})
@NamePattern("%s|idinstituicao")
@Table(name = "projecto_intervencao_nucleo_executivo")
@Entity(name = "cmolhao_ProjectoIntervencaoNucleoExecutivo")
public class ProjectoIntervencaoNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 6612388904050159388L;
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
    @Lob
    @Column(name = "descricao_projecto")
    protected String descricaoProjecto;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idinstituicao")
    protected InstituicoesNucleoExecutivo idinstituicao;





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
    @Lob
    @Column(name = "nome_projecto")
    protected String nomeProjecto;
    @Column(name = "outrosgrupos", length = 250)
    protected String outrosgrupos;
    @Column(name = "pretendealargarservicos")
    protected Boolean pretendealargarservicos;
    @Column(name = "projectosemaprovacao")
    protected Boolean projectosemaprovacao;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idprojectosintervencao")
    protected Set<ProjectosAprovacaoNucleoExecutivo> projectosAprovacaoNucleoExecutivos;

    public Set<ProjectosAprovacaoNucleoExecutivo> getProjectosAprovacaoNucleoExecutivos() {
        return projectosAprovacaoNucleoExecutivos;
    }

    public void setProjectosAprovacaoNucleoExecutivos(Set<ProjectosAprovacaoNucleoExecutivo> projectosAprovacaoNucleoExecutivos) {
        this.projectosAprovacaoNucleoExecutivos = projectosAprovacaoNucleoExecutivos;
    }

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

    public String getNomeProjecto() {
        return nomeProjecto;
    }

    public void setNomeProjecto(String nomeProjecto) {
        this.nomeProjecto = nomeProjecto;
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

    public InstituicoesNucleoExecutivo getIdinstituicao() {
        return idinstituicao;
    }

    public void setIdinstituicao(InstituicoesNucleoExecutivo idinstituicao) {
        this.idinstituicao = idinstituicao;
    }

    public String getDescricaoProjecto() {
        return descricaoProjecto;
    }

    public void setDescricaoProjecto(String descricaoProjecto) {
        this.descricaoProjecto = descricaoProjecto;
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