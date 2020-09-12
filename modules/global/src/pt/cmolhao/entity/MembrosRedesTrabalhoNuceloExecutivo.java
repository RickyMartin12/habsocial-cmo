package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'dbView':true,'imported':true}")
@Table(name = "membros_redes_trabalho_nucelo_executivo")
@Entity(name = "cmolhao_MembrosRedesTrabalhoNuceloExecutivo")
public class MembrosRedesTrabalhoNuceloExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 4987136279940570070L;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_adesao")
    protected Date dataAdesao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_insituicao")
    protected Instituicoes idInsituicao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rede_trabalho")
    protected RedeTrabalho idRedeTrabalho;

    public RedeTrabalho getIdRedeTrabalho() {
        return idRedeTrabalho;
    }

    public void setIdRedeTrabalho(RedeTrabalho idRedeTrabalho) {
        this.idRedeTrabalho = idRedeTrabalho;
    }

    public Instituicoes getIdInsituicao() {
        return idInsituicao;
    }

    public void setIdInsituicao(Instituicoes idInsituicao) {
        this.idInsituicao = idInsituicao;
    }

    public Date getDataAdesao() {
        return dataAdesao;
    }

    public void setDataAdesao(Date dataAdesao) {
        this.dataAdesao = dataAdesao;
    }
}