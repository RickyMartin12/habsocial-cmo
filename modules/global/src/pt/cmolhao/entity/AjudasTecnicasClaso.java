package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idajudastecnicas"))
})
@Table(name = "ajudas_tecnicas_claso")
@Entity(name = "cmolhao_AjudasTecnicasClaso")
public class AjudasTecnicasClaso extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 6354330029182540043L;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datadisponivel")
    protected Date datadisponivel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valencia")
    protected ValenciasClaso idValencia;
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

    public ValenciasClaso getIdValencia() {
        return idValencia;
    }

    public void setIdValencia(ValenciasClaso idValencia) {
        this.idValencia = idValencia;
    }

    public Date getDatadisponivel() {
        return datadisponivel;
    }

    public void setDatadisponivel(Date datadisponivel) {
        this.datadisponivel = datadisponivel;
    }
}