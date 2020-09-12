package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idutentesoutrosconcelhos"))
})
@Table(name = "utentes_outros_concelhos_nucleo_executivo")
@Entity(name = "cmolhao_UtentesOutrosConcelhosNucleoExecutivo")
public class UtentesOutrosConcelhosNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2844711627209104589L;
    @Column(name = "concelho", length = 45)
    protected String concelho;
    @Column(name = "freguesia", length = 45)
    protected String freguesia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valencia")
    protected ValenciaNucleoExecutivo idValencia;

    public ValenciaNucleoExecutivo getIdValencia() {
        return idValencia;
    }

    public void setIdValencia(ValenciaNucleoExecutivo idValencia) {
        this.idValencia = idValencia;
    }

    public String getFreguesia() {
        return freguesia;
    }

    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    public String getConcelho() {
        return concelho;
    }

    public void setConcelho(String concelho) {
        this.concelho = concelho;
    }
}