package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "hab_id"))
})
@NamePattern("%s|bloc")
@Table(name = "habitacao_social")
@Entity(name = "cmolhao_HabitacaoSocial")
public class HabitacaoSocial extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -2965248998315385385L;
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
    protected Integer t0;
    @Column(name = "t1")
    protected Integer t1;
    @Column(name = "t2")
    protected Integer t2;
    @Column(name = "t3")
    protected Integer t3;
    @Column(name = "t4")
    protected Integer t4;
    @Column(name = "t5")
    protected Integer t5;
    @Lob
    @Column(name = "tipo_arrendamento")
    protected String tipoArrendamento;
    @Column(name = "valor_patrimonio")
    protected Integer valorPatrimonio;
    @Column(name = "vend")
    protected Integer vend;

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

    public Integer getT5() {
        return t5;
    }

    public void setT5(Integer t5) {
        this.t5 = t5;
    }

    public Integer getT4() {
        return t4;
    }

    public void setT4(Integer t4) {
        this.t4 = t4;
    }

    public Integer getT3() {
        return t3;
    }

    public void setT3(Integer t3) {
        this.t3 = t3;
    }

    public Integer getT2() {
        return t2;
    }

    public void setT2(Integer t2) {
        this.t2 = t2;
    }

    public Integer getT1() {
        return t1;
    }

    public void setT1(Integer t1) {
        this.t1 = t1;
    }

    public Integer getT0() {
        return t0;
    }

    public void setT0(Integer t0) {
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
}