package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'imported':true}")
@Table(name = "apoios")
@Entity(name = "cmolhao_Apoios")
public class Apoios extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -5091763254653855988L;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_atribuicao")
    protected Date dataAtribuicao;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim")
    protected Date dataFim;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_pedido")
    protected Date dataPedido;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_renovacao")
    protected Date dataRenovacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipamento")
    protected Equipamento idEquipamento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    protected Estados idEstado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao")
    protected Instituicoes idInstituicao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipoapoio")
    protected TipoAjuda idTipoapoio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    protected Utente idUtente;
    @Column(name = "num_processo")
    protected Integer numProcesso;
    @Column(name = "observacao_estado")
    protected String observacaoEstado;
    @Column(name = "observacoes_conf")
    protected String observacoesConf;
    @Column(name = "observacoes_gerais")
    protected String observacoesGerais;
    @Column(name = "valor_apoio")
    protected Integer valorApoio;

    public Integer getValorApoio() {
        return valorApoio;
    }

    public void setValorApoio(Integer valorApoio) {
        this.valorApoio = valorApoio;
    }

    public String getObservacoesGerais() {
        return observacoesGerais;
    }

    public void setObservacoesGerais(String observacoesGerais) {
        this.observacoesGerais = observacoesGerais;
    }

    public String getObservacoesConf() {
        return observacoesConf;
    }

    public void setObservacoesConf(String observacoesConf) {
        this.observacoesConf = observacoesConf;
    }

    public String getObservacaoEstado() {
        return observacaoEstado;
    }

    public void setObservacaoEstado(String observacaoEstado) {
        this.observacaoEstado = observacaoEstado;
    }

    public Integer getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(Integer numProcesso) {
        this.numProcesso = numProcesso;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public TipoAjuda getIdTipoapoio() {
        return idTipoapoio;
    }

    public void setIdTipoapoio(TipoAjuda idTipoapoio) {
        this.idTipoapoio = idTipoapoio;
    }

    public Instituicoes getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Instituicoes idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public Estados getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estados idEstado) {
        this.idEstado = idEstado;
    }

    public Equipamento getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Equipamento idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataAtribuicao() {
        return dataAtribuicao;
    }

    public void setDataAtribuicao(Date dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }
}