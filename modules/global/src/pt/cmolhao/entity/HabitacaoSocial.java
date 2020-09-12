package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "hab_id"))
})
@NamePattern("%s|bloc")
@Table(name = "habitacao_social")
@Entity(name = "cmolhao_HabitacaoSocial")
public class HabitacaoSocial extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -1282627708413235453L;
    @Lob
    @Column(name = "andar")
    protected String andar;
    @Column(name = "arrend")
    protected Integer arrend;
    @Lob
    @Column(name = "bl")
    protected String bl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloc_id")
    protected BlocosHabitacaoSocial bloc;
    @Column(name = "cod")
    protected Integer cod;
    @Column(name = "cod_postal", length = 8)
    protected String codPostal;
    @Column(name = "codbr")
    protected Integer codbr;
    @Column(name = "coord")
    protected String coord;
    @Column(name = "eqsocial")
    protected Integer eqsocial;
    @Column(name = "ficha_completa")
    protected Boolean fichaCompleta;
    @Column(name = "freguesia")
    protected String freguesia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    protected Utente idUtente;
    @Column(name = "localidade")
    protected String localidade;
    @Column(name = "numpolicia")
    protected String numpolicia;
    @Lob
    @Column(name = "obsconf")
    protected String obsconf;
    @Column(name = "obsgerais", length = 1024)
    protected String obsgerais;
    @Column(name = "rendimento_anual")
    protected Integer rendimentoAnual;
    @Column(name = "rua")
    protected String rua;
    @Column(name = "sito_lugar")
    protected String sitoLugar;
    @Column(name = "t0")
    protected Boolean t0;
    @Column(name = "t1")
    protected Boolean t1;
    @Column(name = "t2")
    protected Boolean t2;
    @Column(name = "t3")
    protected Boolean t3;
    @Column(name = "t4")
    protected Boolean t4;
    @Column(name = "t5")
    protected Boolean t5;
    @Lob
    @Column(name = "tipo_arrendamento")
    protected String tipoArrendamento;
    @Column(name = "valor_patrimonio")
    protected Integer valorPatrimonio;
    @Column(name = "vend")
    protected Integer vend;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "habitacaoSocial")
    protected Set<Moradores> moradores;

    public Set<Moradores> getMoradores() {
        return moradores;
    }

    public void setMoradores(Set<Moradores> moradores) {
        this.moradores = moradores;
    }

    public Integer getVend() {
        return vend;
    }

    public void setVend(Integer vend) {
        this.vend = vend;
    }

    public Integer getValorPatrimonio() {
        return valorPatrimonio;
    }

    public void setValorPatrimonio(Integer valorPatrimonio) {
        this.valorPatrimonio = valorPatrimonio;
    }

    public String getTipoArrendamento() {
        return tipoArrendamento;
    }

    public void setTipoArrendamento(String tipoArrendamento) {
        this.tipoArrendamento = tipoArrendamento;
    }

    public Boolean getT5() {
        return t5;
    }

    public void setT5(Boolean t5) {
        this.t5 = t5;
    }

    public Boolean getT4() {
        return t4;
    }

    public void setT4(Boolean t4) {
        this.t4 = t4;
    }

    public Boolean getT3() {
        return t3;
    }

    public void setT3(Boolean t3) {
        this.t3 = t3;
    }

    public Boolean getT2() {
        return t2;
    }

    public void setT2(Boolean t2) {
        this.t2 = t2;
    }

    public Boolean getT1() {
        return t1;
    }

    public void setT1(Boolean t1) {
        this.t1 = t1;
    }

    public Boolean getT0() {
        return t0;
    }

    public void setT0(Boolean t0) {
        this.t0 = t0;
    }

    public String getSitoLugar() {
        return sitoLugar;
    }

    public void setSitoLugar(String sitoLugar) {
        this.sitoLugar = sitoLugar;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getRendimentoAnual() {
        return rendimentoAnual;
    }

    public void setRendimentoAnual(Integer rendimentoAnual) {
        this.rendimentoAnual = rendimentoAnual;
    }

    public String getObsgerais() {
        return obsgerais;
    }

    public void setObsgerais(String obsgerais) {
        this.obsgerais = obsgerais;
    }

    public String getObsconf() {
        return obsconf;
    }

    public void setObsconf(String obsconf) {
        this.obsconf = obsconf;
    }

    public String getNumpolicia() {
        return numpolicia;
    }

    public void setNumpolicia(String numpolicia) {
        this.numpolicia = numpolicia;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public String getFreguesia() {
        return freguesia;
    }

    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    public Boolean getFichaCompleta() {
        return fichaCompleta;
    }

    public void setFichaCompleta(Boolean fichaCompleta) {
        this.fichaCompleta = fichaCompleta;
    }

    public Integer getEqsocial() {
        return eqsocial;
    }

    public void setEqsocial(Integer eqsocial) {
        this.eqsocial = eqsocial;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public Integer getCodbr() {
        return codbr;
    }

    public void setCodbr(Integer codbr) {
        this.codbr = codbr;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public BlocosHabitacaoSocial getBloc() {
        return bloc;
    }

    public void setBloc(BlocosHabitacaoSocial bloc) {
        this.bloc = bloc;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public Integer getArrend() {
        return arrend;
    }

    public void setArrend(Integer arrend) {
        this.arrend = arrend;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }
}