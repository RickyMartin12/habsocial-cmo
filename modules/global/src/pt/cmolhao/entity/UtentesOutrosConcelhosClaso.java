package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idutentesoutrosconcelhos"))
})
@Table(name = "utentes_outros_concelhos_claso")
@Entity(name = "cmolhao_UtentesOutrosConcelhosClaso")
public class UtentesOutrosConcelhosClaso extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 3129268322161727078L;
    @Column(name = "concelho", length = 45)
    protected String concelho;
    @Column(name = "freguesia", length = 45)
    protected String freguesia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valencia")
    protected ValenciasClaso idValencia;

    public ValenciasClaso getIdValencia() {
        return idValencia;
    }

    public void setIdValencia(ValenciasClaso idValencia) {
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