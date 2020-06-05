package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s - %s|tipoRelacionamento,tipoRelacionamentoInv")
@Table(name = "tipo_relacionamento_utentes")
@Entity(name = "cmolhao_TipoRelacionamentoUtentes")
public class TipoRelacionamentoUtentes extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2968528483686803938L;
    @Column(name = "tipo_relacionamento")
    protected String tipoRelacionamento;
    @Column(name = "tipo_relacionamento_inv")
    protected String tipoRelacionamentoInv;

    public String getTipoRelacionamentoInv() {
        return tipoRelacionamentoInv;
    }

    public void setTipoRelacionamentoInv(String tipoRelacionamentoInv) {
        this.tipoRelacionamentoInv = tipoRelacionamentoInv;
    }

    public String getTipoRelacionamento() {
        return tipoRelacionamento;
    }

    public void setTipoRelacionamento(String tipoRelacionamento) {
        this.tipoRelacionamento = tipoRelacionamento;
    }
}