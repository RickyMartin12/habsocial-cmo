package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idajudastecnicas"))
})
@Table(name = "ajudas_tecnicas_nucleo_executivo")
@Entity(name = "cmolhao_AjudasTecnicasNucleoExecutivo")
public class AjudasTecnicasNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -1676207453470388181L;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datadisponivel")
    protected Date datadisponivel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valencia")
    protected ValenciaNucleoExecutivo idValencia;
    @Column(name = "localizacao", length = 250)
    protected String localizacao;
    @Column(name = "tipoajuda", length = 250)
    protected String tipoajuda;

    public String getTipoajuda() {
        return tipoajuda;
    }

    public void setTipoajuda(String tipoajuda) {
        this.tipoajuda = tipoajuda;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public ValenciaNucleoExecutivo getIdValencia() {
        return idValencia;
    }

    public void setIdValencia(ValenciaNucleoExecutivo idValencia) {
        this.idValencia = idValencia;
    }

    public Date getDatadisponivel() {
        return datadisponivel;
    }

    public void setDatadisponivel(Date datadisponivel) {
        this.datadisponivel = datadisponivel;
    }
}