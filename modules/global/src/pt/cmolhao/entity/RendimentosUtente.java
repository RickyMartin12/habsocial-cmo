package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@Table(name = "rendimentos_utente")
@Entity(name = "cmolhao_RendimentosUtente")
public class RendimentosUtente extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 7209311559649517181L;
    @Column(name = "ano", length = 4)
    protected String ano;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_rendimento")
    protected TipoRendimento idTipoRendimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    protected Utente idUtente;
    @Column(name = "valor_rendimento")
    protected Integer valorRendimento;

    public Integer getValorRendimento() {
        return valorRendimento;
    }

    public void setValorRendimento(Integer valorRendimento) {
        this.valorRendimento = valorRendimento;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public TipoRendimento getIdTipoRendimento() {
        return idTipoRendimento;
    }

    public void setIdTipoRendimento(TipoRendimento idTipoRendimento) {
        this.idTipoRendimento = idTipoRendimento;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}