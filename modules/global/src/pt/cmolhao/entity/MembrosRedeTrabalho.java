package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;

@DesignSupport("{'imported':true}")
@Table(name = "membros_rede_trabalho")
@Entity(name = "cmolhao_MembrosRedeTrabalho")
public class MembrosRedeTrabalho extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 5535534918427157055L;
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